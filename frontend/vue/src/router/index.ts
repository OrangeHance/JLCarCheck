import { createRouter, createWebHistory} from 'vue-router'
import store from "../store";


// 路由规则
const routes = [
   {
     path: '/',
     name: 'home',
     component: () => import('../views/home.vue'),
     children: [
      {
        path: "/aJobMenu",
        component: () => import("../views/carCheck/carCheck.vue"),
      },
    ],
   },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login.vue')
  },
  

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isLogin = store.getters.isLogin;
  if (to.path === "/login" && !isLogin) {
    next();
  } else if (!isLogin) {
    next("/login");
  } else if(to.path === "/login" && isLogin) {
    next("/");
  }else {
    next();
  }
});

export default router