import request from '../utils/request'
import type { ResponseData } from '../types/response'

// 请求参数类型
export interface JobItemsParams {
  job: string
}

// 检查项返回结构
export interface CheckInfoItems {
  desc: string
  url: string
}

export interface carCheckListParams {
  job: string
  vin: string
}

// 根据岗位查询检查项接口
export function getCarCheckItems(params: JobItemsParams): Promise<ResponseData<CheckInfoItems[]>> {
  return request({
    url: '/checkItem/items',
    method: 'GET',
    // get请求参数放params
    params
  })
}

  // 根据岗位查询检查项接口
export function getCarByBarCode(params: carCheckListParams): Promise<ResponseData<CheckInfoItems[]>> {
  return request({
    url: '/carInfo/getCarByBarCode',
    method: 'GET',
    // get请求参数放params
    params
  })
}