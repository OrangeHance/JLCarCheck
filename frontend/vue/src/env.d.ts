/// <reference types="vite/client" />

// 解决 vue 文件识别
declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

// 解决 import.meta.env 报错（关键！）
interface ImportMeta {
  readonly env: Record<string, string>
}

// 解决 vuex 报错
declare module 'vuex';
declare module 'vue-router';