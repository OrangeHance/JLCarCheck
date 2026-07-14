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

// 车辆信息子类型
export interface CarInfoDTO {
  id: number
  productSeqNum: string
  productNum: string
  stationCode: string
  materialCode: string
  model: string
  series: string
  config: string
  selection: string
  engineType: string
}

// 工单信息
export interface WorkInfoDTO {
  title: string
  desc: string
}

// 检查项
export interface CheckItemDTO {
  id: number
  desc: string
  url: string
  result: number | null // 0 NOK 1 OK
}

// 提交整体参数
export interface QualityCheckSubmitDTO {
  barCode: string
  jobCode: string // 岗位编码，后端接收
  carInfo: CarInfoDTO
  workInfo: WorkInfoDTO
  checkList: CheckItemDTO[]
}

// 提交质检保存接口
export function submitQualityCheck(data: QualityCheckSubmitDTO): Promise<ResponseData<string>> {
  return request({
    url: '/checkHis/submit',
    method: 'POST',
    data
  })
}
