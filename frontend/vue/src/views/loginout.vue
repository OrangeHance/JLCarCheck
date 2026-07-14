<template>
  <div class="stock-in-page">
    <!-- 查询区域：同一行 -->
    <div class="query-form">
      <div class="query-row">
        <!-- 入库日期 -->
        <div class="query-item">
          <label class="query-label">入库日期</label>
          <el-date-picker
            v-model="query.inDate"
            placeholder="请输入入库日期"
            style="width: 200px"
            value-format="YYYY-MM-DD"
          />
        </div>

        <!-- 查询 + 重置 按钮 -->
        <div class="btn-group">
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
      >
        <el-table-column label="ID" prop="id" />
        <el-table-column label="商品编码" prop="goodsCode" />
        <el-table-column label="规格" prop="spec" />
        <el-table-column label="入库数量" prop="quantity" />
        <el-table-column label="入库日期" prop="inDate" />
        <el-table-column label="备注" prop="remark" />
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
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
import { wareHouseInSearch } from '../../api/wareHouse'

// 分页
const total = ref(0);
const pageNum = ref(1);
const pageSize = ref(10);

// 查询条件
const query = reactive({
  inDate: "",
  pageNum: 1,
  pageSize: 10
});

// 列表
const list = ref([]);

const getList = async () => {
  query.pageNum = pageNum.value;
  query.pageSize = pageSize.value;

  const res = await wareHouseInSearch(query);
  if (res.code === 200) {
    list.value = res.data.list;
    total.value = res.data.total || 0;
  } else {
    ElMessage.error('查询出错，请联系管理员！');
  }
};

// 页大小改变
const handleSizeChange = (val) => {
  pageSize.value = val;
  getList();
};

// 页码改变
const handleCurrentChange = (val) =>{
  pageNum.value = val;
  getList();
};

// 重置
const resetQuery = () => {
  query.inDate = "";
  pageNum.value = 1;
  pageSize.value = 10;
  getList();
};

onMounted(() => getList());
</script>

<style scoped>
.stock-in-page {
  width: 100%;
  height: 100%;
}

/* 查询表单行：条件 + 按钮 同一行 */
.query-row {
  display: flex;
  align-items: center;
  gap: 20px; /* 行内间距 */
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

/* 按钮组 */
.btn-group {
  display: flex;
  gap: 10px;
  margin-left: 10px;
}

/* 表格 */
.table-wrapper {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
}

/* 分页居中 */
.pagination {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}
</style>