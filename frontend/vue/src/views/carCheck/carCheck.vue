<template>
  <div class="quality-check-page">
    <!-- 顶部条码与提交按钮区域 -->
    <div class="top-bar">
      <el-input
        v-model="barCode"
        class="bar-input"
        placeholder="请输入条码"
        @keyup.enter="searchCarInfo"
      />
      <el-button type="primary" @click="findCarInfoAndcCheck">查询车辆</el-button>
      <el-button type="warning" size="large" @click="handleSubmit">提交</el-button>
    </div>

    <!-- 条码下方 车辆信息卡片 -->
    <div class="car-card" v-if="carInfo">
      <h3>车辆详情</h3>
      <div class="car-row">
        <span class="label">生产流水号：</span>
        <span>{{ carInfo.productSeqNum }}</span>
      </div>
      <div class="car-row">
        <span class="label">VIN码：</span>
        <span>{{ carInfo.productNum }}</span>
      </div>
    
      <div class="car-row">
        <span class="label">配置：</span>
        <span>{{ carInfo.config }}</span>
      </div>
    </div>

    <!-- 工单标题描述 -->
    <div class="title-area">
      <h2>{{ workInfo.title }}</h2>
      <p class="desc">{{ workInfo.desc }}</p>
    </div>

    <!-- 质检项列表 - 超出自动滚动 -->
    <div class="check-list-wrap" v-loading="loading">
      <div class="check-list">
        <div class="check-item" v-for="(item, index) in checkList" :key="index">
          <!-- 检查项标题 -->
          <div class="item-label">{{ item.desc }}</div>
          <div hidden>{{ item.id }}</div>
          <div class="item-content">
            <!-- 500*200图片容器 -->
            <div class="item-img-box">
              <img :src="item.url" alt="检测图" />
            </div>
            <!-- OK / NOK 单选Radio组，互斥选择 -->
            <div class="btn-group">
              <el-radio-group v-model="item.result">
                <el-radio-button value="0" type="danger" size="large">NOK</el-radio-button>
                <el-radio-button value="1" type="success" size="large">OK</el-radio-button>
              </el-radio-group>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { getCarCheckItems,getCarByBarCode,submitQualityCheck,findCarInfo} from '../../api/carCheckList'
import { useRoute } from 'vue-router'

const route = useRoute()
const jobCode = route.meta.job as string

// 车辆信息类型
interface CarInfo {
  productSeqNum: string
  vin: string
  productNum: string
  config: string
}

// 质检项类型定义
interface CheckItem {
  id: number
  desc: string
  url?: string
  result: null | '0' | '1' // radio输出字符串0/1，兼容校验逻辑
}

// 工单信息类型
interface WorkInfo {
  title: string
  desc: string
}

// 加载状态
const loading = ref<boolean>(false)
// 条码：默认可输入，初始为空
const barCode = ref<string>('')
// 车辆信息
const carInfo = ref<CarInfo | null>(null)

// 工单基础信息
const workInfo = reactive<WorkInfo>({
  title: '',
  desc: ''
})

// 质检列表数据
const checkList = reactive<CheckItem[]>([])

const getDynamicImg = async (fileName: string) => {
  const res = await import(`@/assets/image/${fileName}`)
  return res.default
}

const findCarInfoAndcCheck = async () => {
  try {
     const res = await findCarInfo({ job: jobCode, vin: barCode.value })
    if (res.code === 200) {
      carInfo.value = res.data.carInfo
      if(res.data.checkList.length === 0){
        checkList.forEach((item, index) => {
            item.result = ''
      })
      }
      checkList.forEach((item, index) => {
        res.data.checkList.forEach((checkItem) => {
          if (checkItem.id === item.id) {
            item.result = String(checkItem.result)
          }
        })
      })
    console.log(checkList);
    } else {
      ElMessage.info('未匹配到该条码车辆信息')
      carInfo.value = null
    }
  } catch (err) {
    ElMessage.error('查询车辆数据失败')
    console.error(err)
    carInfo.value = null
  }
}
// 根据条码查询车辆
const searchCarInfo = async () => {
  try {
     const res = await getCarByBarCode({ job: jobCode, vin: barCode.value })
    if (res.code === 200) {
      carInfo.value = res.data
    } else {
      ElMessage.info('未匹配到该条码车辆信息')
      carInfo.value = null
    }
  } catch (err) {
    ElMessage.error('查询车辆数据失败')
    console.error(err)
    carInfo.value = null
  }
}

// 页面加载自动查询检查项
const getCheckData = async () => {
  loading.value = true
  try {
    const res = await getCarCheckItems({ job: jobCode })
    // 接口成功判断
    if (res.code === 200) {
      const data = res.data // data 是 CheckInfoItems[] 数组
      // 格式化数据，补齐result默认null
      const formatList = data.map(d => ({
        id: d.id,     
        desc: d.desc,
        url: d.url,
        result: null
      }))
      checkList.splice(0, checkList.length, ...formatList)
    } else {
      ElMessage.warning(res.msg || '获取检查项失败')
    }
  } catch (err) {
    ElMessage.error('加载质检数据失败，请刷新重试')
    console.error('接口请求错误：', err)
  } finally {
    loading.value = false
  }
}

// 切换左侧菜单重新加载页面数据、清空车辆信息
watch(
  () => route.meta.job,
  (newVal, oldVal) => {
    if (newVal !== oldVal) {
      getCheckData()
      barCode.value = ''
      carInfo.value = null
    }
  }
)

// 页面挂载时执行接口查询
onMounted(() => {
  getCheckData()
  searchCarInfo()
})

// 提交校验
const handleSubmit = async () => {
  // 1. 校验是否查到车辆
  if (!carInfo.value) {
    ElMessage.warning('请输入条码并查询车辆后再提交')
    return
  }
  // 2. 校验所有检查项都已选择OK/NOK
  const unSelect = checkList.some(item => item.result === null)
  if (unSelect) {
    ElMessage.warning('存在未判定的检查项，请全部选择OK/NOK后再提交')
    return
  }

  try {
    // 组装后端需要的完整提交参数
    const submitParams = {
      barCode: barCode.value,
      jobCode: route.meta.job as string, // 路由meta里的岗位 A/B/C...
      carInfo: carInfo.value,
      checkList: checkList.map(item => ({
        id: item.id,
        desc: item.desc,
        url: item.url,
        result: Number(item.result)
      }))
    }

    // 调用提交接口
    const res = await submitQualityCheck(submitParams)
    if (res.code === 200) {
      ElMessage.success('提交保存成功！')
      // 提交成功后清空页面数据，可选
      barCode.value = ''
      carInfo.value = null
      checkList.splice(0, checkList.length)
      getCheckData()
      searchCarInfo()
    } else {
      ElMessage.error(res.msg || '提交失败，请重试')
    }
  } catch (err) {
    ElMessage.error('提交失败，请重试')
    console.error('提交异常：', err)
  }
}

</script>

<style scoped>
/* 页面根容器，占满可视区域，超出滚动 */
.quality-check-page {
  height: 100vh;
  padding: 20px;
  max-width: 750px;
  margin: 0 auto;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}

.top-bar {
  display: flex;
  gap: 16px;
  align-items: center;
  margin-bottom: 16px;
  flex-shrink: 0;
}
.bar-input {
  flex: 1;
}

/* 车辆信息卡片样式 */
.car-card {
  background-color: #f7f9fc;
  border-radius: 8px;
  padding: 16px 20px;
  margin-bottom: 20px;
}
.car-card h3 {
  margin: 0 0 12px;
  font-size: 16px;
  color: #222;
}
.car-row {
  display: flex;
  margin: 8px 0;
  font-size: 15px;
}
.car-row .label {
  width: 130px;
  color: #606266;
}

.title-area {
  margin-bottom: 24px;
  flex-shrink: 0;
}
.title-area h2 {
  font-size: 22px;
  margin: 0 0 8px;
  font-weight: 500;
}
.desc {
  color: #666;
  font-size: 16px;
  margin: 0;
}

/* 列表外层容器，高度自适应，内容溢出出现滚动条 */
.check-list-wrap {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
}

.check-list {
  display: flex;
  flex-direction: column;
  gap: 28px;
  padding-right: 8px;
}
.check-item {
  width: 100%;
}
.item-label {
  font-size: 17px;
  margin-bottom: 10px;
  margin-left: -220px;
}
.item-content {
  display: flex;
  gap: 20px;
  align-items: center;
}

/* 500*200图片外框 */
.item-img-box {
  width: 500px;
  height: 200px;
  border: 1px solid #eee;
  border-radius: 6px;
  overflow: hidden;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f8f8;
}
.item-img-box img {
  max-width: 90%;
  max-height: 90%;
  /* 替换 cover，完整显示图片不裁剪 */
  object-fit: contain;
}


.btn-group {
  display: flex;
  flex-direction: column;
  gap: 14px;
  flex-shrink: 0;
}
/* 放大单选按钮，适配触控 */
.btn-group .el-radio-button__inner {
  width: 80px;
  height: 80px;
  font-size: 20px;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50% !important;
}
/* 选中放大高亮 */
.btn-group .el-radio-button.is-active .el-radio-button__inner {
  transform: scale(1.08);
  box-shadow: 0 0 0 3px rgba(0,0,0,0.15);
}
</style>
