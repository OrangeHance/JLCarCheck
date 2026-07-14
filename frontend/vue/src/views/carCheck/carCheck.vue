<template>
  <div class="quality-check-page">
    <!-- 顶部条码与提交按钮区域 -->
    <div class="top-bar">
      <el-input
        v-model="barCode"
        class="bar-input"
        placeholder="请输入条码"
      />
      <el-button type="warning" size="large" @click="handleSubmit">提交</el-button>
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
          <div class="item-label">{{ item.label }}</div>
          <div class="item-content">
            <!-- 图片（无图片则隐藏） -->
            <div v-if="item.imgUrl" class="item-img">
              <img :src="item.imgUrl" alt="质检图片" />
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

// 质检项类型定义
interface CheckItem {
  label: string
  imgUrl?: string
  result: null | '0' | '1' // radio输出字符串0/1，兼容校验逻辑
}

// 工单信息类型
interface WorkInfo {
  title: string
  desc: string
}

// 接口返回整体数据类型
interface ApiResponse {
  barCode: string
  workInfo: WorkInfo
  checkList: CheckItem[]
}

// 加载状态
const loading = ref<boolean>(false)
// 条码：默认可输入，初始为空
const barCode = ref<string>('')

// 工单基础信息
const workInfo = reactive<WorkInfo>({
  title: '',
  desc: ''
})

// 质检列表数据
const checkList = reactive<CheckItem[]>([])

// 页面加载自动查询接口
const getCheckData = async () => {
  loading.value = true
  try {
    // ========== 替换为你真实后端接口地址 ==========
    const res = await axios.get<ApiResponse>('/api/quality/getCheckInfo', {
      params: {
        // 可传页面参数，如工单ID、条码等
      }
    })
    const data = res.data
    // 赋值页面数据
    barCode.value = data.barCode
    workInfo.title = data.workInfo.title
    workInfo.desc = data.workInfo.desc
    checkList.splice(0, checkList.length, ...data.checkList)
  } catch (err) {
    ElMessage.error('加载质检数据失败，请刷新重试')
    console.error('接口请求错误：', err)
  } finally {
    loading.value = false
  }
}

// 页面挂载时执行接口查询
onMounted(() => {
  getCheckData()
})

// 提交校验
const handleSubmit = () => {
  // 校验所有项是否全部选择
  const unSelect = checkList.some(item => item.result === null)
  if (unSelect) {
    ElMessage.warning('存在未判定的检查项，请全部选择OK/NOK后再提交')
    return
  }
  // 组装提交参数，转数字兼容后端
  const submitData = {
    barCode: barCode.value,
    workInfo,
    checkList: checkList.map(item => ({
      ...item,
      result: Number(item.result)
    }))
  }
  console.log('提交数据：', submitData)
  ElMessage.success('提交成功！')
  // 提交接口示例
  // await axios.post('/api/quality/submit', submitData)
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
  margin-bottom: 20px;
  flex-shrink: 0;
}
.bar-input {
  flex: 1;
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
}
.item-content {
  display: flex;
  gap: 20px;
  align-items: center;
}
.item-img {
  width: 60%;
}
.item-img img {
  width: 100%;
  display: block;
  border-radius: 4px;
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
