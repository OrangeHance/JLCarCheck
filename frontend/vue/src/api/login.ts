import request from '../utils/request'
import type { ResponseData } from '../types/response'

// 登录接口参数
export interface LoginParams {
  username: string
  password: string
}

// 登录接口返回
export interface LoginResult {
  token: string
  username: string
}

// 登录请求
export function loginApi(data: LoginParams): Promise<ResponseData<LoginResult>> {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
  }
  // 获取当前登录人信息
export function loginUser(token: string): Promise<ResponseData<LoginResult>> {
  return request({
    url: '/user/info',
    method: 'get',
    headers: {
      'Authorization': token
    }
  })
}