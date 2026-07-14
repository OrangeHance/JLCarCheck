import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import store from './store'
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 中文语言包
import zhCn from 'element-plus/es/locale/lang/zh-cn'
// 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 挂载 + 中文
app.use(ElementPlus, {
  locale: zhCn
})

app.use(store)
app.use(router)
store.dispatch("initUser");
app.mount('#app')