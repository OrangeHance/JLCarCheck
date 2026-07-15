import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  base: './',
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },

   server: {
    port: 9060,

     proxy: {
      '/api': {
        target:  process.env.VITE_API_URL || 'http://localhost:8080', // 后端地址
        changeOrigin: true,              // 允许跨域
        rewrite: (path) => path.replace(/^\/api/, '') // 把 /api 去掉
      }
    }
  }
})
