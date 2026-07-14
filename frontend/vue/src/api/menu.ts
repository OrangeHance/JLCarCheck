import request from '../utils/request'
import type { ResponseData } from '../types/response'

// 登录接口参数
export interface menuList {
  id: Number
  menuName: String
  menuUrl: String
  createTime: Number
}


  // 获取当前登录人信息
export function menuList(): Promise<ResponseData<menuList>> {
  return request({
    url: '/menu/menuList',
    method: 'get',
  })
}