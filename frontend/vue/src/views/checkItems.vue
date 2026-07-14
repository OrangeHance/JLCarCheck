<template>
  <div class="stock-in-page">
    <!-- 查询区域 -->
    <div class="query-form">
      <div class="query-row">
        <div class="query-item">
          <label class="query-label">商品名称</label>
          <el-input
            v-model="query.goodsName"
            placeholder="请输入商品名称"
            style="width: 200px"
          />
        </div>

        <div class="query-item">
          <label class="query-label">商品编码</label>
          <el-input
            v-model="query.goodsCode"
            placeholder="请输入商品编码"
            style="width: 200px"
          />
        </div>

        <div class="query-item">
          <label class="query-label">库存数量</label>
          <el-input
            v-model="query.minQuantity"
            placeholder="最小数量"
            style="width: 100px"
            oninput="value=value.replace(/[^\d]/g,'')"
          />
          <span style="margin: 0 5px">-</span>
          <el-input
            v-model="query.maxQuantity"
            placeholder="最大数量"
            style="width: 100px"
            oninput="value=value.replace(/[^\d]/g,'')"
          />
        </div>
      </div>

      <!-- 按钮行：左侧总数+入库按钮  右侧查询重置 -->
      <div class="query-btn-row">
        <div class="left-info">
          <el-button type="success" @click="openAddDialog">+ 入库</el-button>
          <el-button type="warning" @click="openOutDialog">− 出库</el-button>
          <WareHouseDialog ref="dialogFormRef" @confirm="handleSubmitData" />
          <WareHouseInDialog ref="indialogFormRef" @confirm="inhandleSubmitData" />
          <WareHouseOutDialog ref="outdialogFormRef" @confirm="outhandleSubmitData" />

        </div>
        <div class="right-btns">
          <el-button type="primary" @click="getList">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </div>
      </div>
    </div>

    <!-- 表格区域 -->
    <div class="table-wrapper">
      <el-table
        :data="list"
        border
        stripe
        max-height="500"
        :row-class-name="tableRowClassName"
        v-loading="loading"
      >
        <el-table-column label="ID" prop="id" />
        <el-table-column label="商品名称" prop="goodsName" />
        <el-table-column label="商品编码" prop="goodsCode" />
        <el-table-column label="规格" prop="spec" />
        <el-table-column label="当前库存" prop="quantity" />
        <el-table-column label="入库日期" prop="inDate" />
        <el-table-column label="最后更新时间" prop="updateTime" />
        <el-table-column label="备注" prop="remark" />
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button 
              type="primary" 
              size="small" 
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页居中 -->
      <div class="pagination">
        <el-pagination
      :current-page="currentPage4"
      :page-size="pageSize4"
      :page-sizes="[10, 20, 30, 40]"
      :size="size"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from 'element-plus'
// import { wareHouseUpdate, wareHouseSearch, wareHouseSave,wareHouseOut } from '../../api/wareHouse'
import { ElMessageBox } from 'element-plus'

// 获取子组件实例
const dialogFormRef = ref(null)
const indialogFormRef = ref(null)
const outdialogFormRef = ref(null)
const loading = ref(true)


// 分页总数、页码、页大小
const total = ref(0);
const pageNum = ref(1);
const pageSize = ref(10);
// 查询条件
const query = reactive({
  goodsName: "",
  goodsCode: "",
  inDate: "",
  minQuantity: "",
  maxQuantity: "",
  pageSize: pageSize,
  pageNum: pageNum

});
// 表格行样式：数量 < 5 标记红色
const tableRowClassName = ({ row }) => {
  if (row.quantity <= 5) {
    return 'red-bg-row'
  }
  return ''
}
// 触发打开弹窗
const handleEdit = (row) => {
  dialogFormRef.value.openDialog(row)
}
// 入库触发打开弹窗
const openAddDialog = (row) => {
  indialogFormRef.value.openDialog(row)
}
// 出库触发打开弹窗
const   openOutDialog = (row) => {
  outdialogFormRef.value.openDialog(row)
}
// 处理修改提交数据
// const handleSubmitData = async (formData) => {
//   console.log('修改的表单数据：', formData)
//     loading.value = true;
//   const res = await wareHouseUpdate(formData)
//   if (res.code === 200) {
//     getList()
//     showMessage({ message: '修改成功', type: 'success' })
//     loading.value = false;
//     dialogFormRef.value.closeDialog() // 关闭编辑弹窗
//   } else {
//     ElMessageBox.confirm(res.message, '修改失败', {
//       confirmButtonText: '重试',
//       showCancelButton: false, // 隐藏取消按钮
//       type: 'error',
//     })
//     .then(() => {
//     })
//     // showMessage({ message: '修改失败，请联系管理员！', type: 'error' })
//     loading.value = false;
//   }
// }
// 处理入库提交数据
// const inhandleSubmitData  = async (formData) => {
//   console.log('入库的表单数据：', formData)
//   loading.value = true;
//   const res = await wareHouseSave(formData)
//   if (res.code === 200) {
//     indialogFormRef.value.closeDialog() // 关闭入库弹窗
//     getList()
//     showMessage({ message: '入库成功', type: 'success' })
//     loading.value = false;
//   } else {
//     ElMessageBox.confirm(res.message, '入库失败', {
//       confirmButtonText: '重试',
//       showCancelButton: false, // 隐藏取消按钮

//       type: 'error',
//     })
//     .then(() => {
//       // indialogFormRef.value.closeDialog() // 关闭入库弹窗
//     })
//     loading.value = false;
//   }
// }
// 处理出库提交数据
// const outhandleSubmitData  = async (formData) => {
//   console.log('出库的表单数据：', formData)
//   loading.value = true;
//   const res = await wareHouseOut(formData)
//   if (res.code === 200) {
//     getList()
//     showMessage({ message: '出库成功', type: 'success' })
//     loading.value = false;
//     outdialogFormRef.value.closeDialog() // 关闭出库弹窗
//   } else {
//     ElMessageBox.confirm(res.message, '出库失败', {
//       confirmButtonText: '重试',
//       showCancelButton: false, // 隐藏取消按钮
//       type: 'error',
//     })
//     .then(() => {
//       // outdialogFormRef.value.closeDialog() // 关闭出库弹窗
//     })
//     // showMessage({ message: res.message, type: 'error' })
//     loading.value = false;
//   }
// }
// 列表数据定义
const list = ref([]);
// const getList = async () => {
//   loading.value = true;
// const res = await wareHouseSearch(query)
// if (res.code === 200) {
//       list.value = res.data.list;
//       total.value = list.value.length > 0 ? res.data.list[0].total : 0;
//       loading.value = false;
//   } else {
//     ElMessage.error('查询出错，请联系管理员！')
//     loading.value = false;
//   }

// };
const handleSizeChange = (val) => {
  pageSize.value = val;
//   getList();  
}
const handleCurrentChange = (val) => {
  pageNum.value = val;
//   getList();
}
const resetQuery = () => {
  query.goodsName = "";
  query.goodsCode = "";
  query.inDate = "";
  query.minQuantity = "";
  query.maxQuantity = "";
  query.pageNum = 1;
  query.pageSize = 10;
  getList();
};

onMounted(() => getList());
</script>

<style scoped>
.stock-in-page {
  width: 100%;
  height: 100%;
}

/* 查询表单 */
.query-form {
  margin-bottom: 15px;
}
.query-row {
  display: flex;
  gap: 40px;
  align-items: center;
}
.query-item {
  display: flex;
  align-items: center;
}
.query-label {
  font-size: 14px;
  margin-right: 8px;
  white-space: nowrap;
}

/* 按钮行：左右布局 */
.query-btn-row {
  margin-top: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 左侧：总数 + 入库按钮 间隔10px */
.left-info {
  display: flex;
  align-items: center;
  gap: 10px;
}
.total-text {
  font-size: 14px;
  color: #666;
}

/* 右侧查询重置按钮 */
.right-btns {
  display: flex;
  gap: 10px;
}

/* 表格容器 */
.table-wrapper {
  display: flex;
  flex-direction: column;
  width: 100%;
}

/* 分页居中 */
.pagination {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}
:deep(.el-table tr.red-bg-row) {
  background-color: #fef0f0 !important;
  --el-table-row-hover-bg-color: #fde2e2 !important;
}
:deep(.el-table tr.red-bg-row td) {
  background-color: #fef0f0 !important;
  color: #F53F3F !important;
}
</style>