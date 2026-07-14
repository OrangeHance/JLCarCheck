<template>
  <div class="login-wrap">
    <div class="login-box">
      <h2 style="text-align:center;margin-bottom:30px;">系统登录</h2>
      <el-form :model="form" label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密　码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" style="width:100%;">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { loginApi } from '../api/login'
import { useStore } from 'vuex'
import { md5 } from 'js-md5'


const router = useRouter()
const store = useStore()
const form = ref({
  username: '',
  password: ''
})
//登录方法执行
const login = async () => {
  if (!form.value.username) return ElMessage.warning('请输入用户名')
  if (!form.value.password) return ElMessage.warning('请输入密码')
  // 密码加密
  const data = {
    username: form.value.username,
    password: md5(form.value.password)
  }
  // 使用 axios 请求后端
  const res = await loginApi(data)
  if (res.code === 200) {
      store.dispatch('loginSuccess', {
         username: form.value.username,
         token: res.data.token || ''
       })
    ElMessage.success('登录成功')
    router.push('/')
  } else {
    ElMessage.error('账号或密码错误')
  }
}
</script>

<style scoped>
.login-wrap {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  background: #f5f7fa;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.login-box {
  width: 90%;
  max-width: 360px;
  padding: 40px 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0,0,0,0.05);
}
</style>