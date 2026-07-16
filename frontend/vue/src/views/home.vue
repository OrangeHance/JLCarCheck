<template>
  <div class="home-full">
    <!-- 左侧导航（永远固定不动） -->
    <div class="sidebar">
      <div class="logo">JL车辆检测管理系统</div>
      
      <el-menu
      :default-active="$route.path"
        :active-index="$route.path"
        background-color="#2f4050"
        text-color="#fff"
        active-text-color="#1890ff"
        router>
          <el-menu-item
            v-for="menu in menuList"
            :key="menu.menuUrl"
            :index="menu.menuUrl"
          >
            {{ menu.menuName }}
          </el-menu-item>
        </el-menu>
    </div>

    <!-- 右侧内容区（只有这里切换页面） -->
    <div class="main-box">
      <div class="content">
        <el-card v-if="$route.path === '/'" style="max-width: 480px">
          <template #header>
            <div class="card-header">
              <span>{{ userInfo.userName }}</span>
            </div>
          </template>
           <p>{{ userInfo.email }}</p>
           <p>{{ userInfo.phone }}</p>
           <p>{{ userInfo.status }}</p>
           <p>{{ userInfo.createTime }}</p>
          <template #footer>当前登录人</template>
          <el-button type="danger" round @click="logout">登出</el-button>
        </el-card>
        <router-view :key="$route.fullPath"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { loginUser } from '../api/login'
import { menuList } from '../api/menu';
export default {
  name: "Home",
  watch: {
    // 监听路由变化，强制更新菜单高亮
    $route(to) {
      this.activePath = to.path;
    },
  },
  data() {
    return {
      activePath: window.location.pathname,
      userInfo:{
          userName: '',
          email: '',
          phone: '',
          status: '',
          createTime: ''
      },
      menuList: []
    };
  },
  mounted() {
    this.fetchUserInfo();
    this.menu();
  },
  methods: {
    menu() {
      menuList().then(res => {
        if (res.code === 200) {
          this.menuList = res.data;
        } else {
          this.$message.error('获取菜单列表失败');
        }
      }).catch(() => {
        this.$message.error('获取菜单列表失败');
      });
    },
    logout() {
      this.$store.dispatch('logout');
      this.$router.push("/login");
    },
    async fetchUserInfo() {
      const userInfo = JSON.parse(localStorage.getItem("userInfo"));
      if (userInfo.token) {
        try {
          const res = await loginUser("Bearer " + userInfo.token);
          if (res.code === 200) {
            this.userInfo = res.data;
            if(this.userInfo.status === 0){
              this.userInfo.status = '正常';
            } else if(this.userInfo.status === 1){
              this.userInfo.status = '禁用';
            } else {
              this.userInfo.status = '未知';

            }
          } else {
            this.$message.error('获取用户信息失败');
          }
        } catch (error) {
          this.$message.error('获取用户信息失败');
        }
      }
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.home-full {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  overflow: hidden;
}
.sidebar {
  width: 220px;
  height: 100%;
  background: #2f4050;
  flex-shrink: 0;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  border-bottom: 1px solid #45569e;
}
.main-box {
  flex: 1;
  height: 100%;
  background: #f5f7fa;
  padding: 20px;
  overflow: hidden;
}
.content {
  width: 100%;
  height: 100%;
  background: #fff;
  border-radius: 8px;
  padding: 20px;
}
</style>