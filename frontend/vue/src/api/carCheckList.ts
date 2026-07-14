import request from '../utils/request'
import type { ResponseData } from '../types/response'

// 入库查询接口参数
export interface WareHouseInSearch {
  goodsName: string
  goodsCode: string
  inDate: Number
  pageSize: Number
  pageNum: Number
  minQuantity: Number
  maxQuantity: Number
}
// 入库更新接口参数
export interface WareHouseUpdate {
  id: Number
  goodsName: string
  goodsCode: string
  inDate: Number
  spec: String
  quantity: String
  remark: String
}
// 入库保存接口参数
export interface WareHouseSave {
  goodsName: string
  goodsCode: string
  inDate: Number
  spec: String
  quantity: String
  remark: String
}

// 出库保存接口参数
export interface WareHouseOut {
  goodsCode: string
  outQuantity: Number
  outDate: String
  remark: String
}

// 入库接口返回
export interface WareHouseInResult {
              id: Number
      goods_name: String
      goods_code: String
            spec: String
        quantity: String
          inDate: Number
          remark: String
      createTime: Number
      updateTime: Number
        pageSize: Number
         pageNum: Number
}

// 库存查询请求
export function wareHouseSearch(data: WareHouseInSearch): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/wareHouseSearch',
    method: 'post',
    data
  })
}

//  库存管理更新数据请求
export function wareHouseUpdate(data: WareHouseUpdate): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/wareHouseUpdate',
    method: 'post',
    data
  })
}

//  库存管理保存数据请求
export function wareHouseSave(data: WareHouseSave): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/wareHouseSave',
    method: 'post',
    data
  })
}

//  获取商品下拉选项接口
export function getGoodsOptions(data: WareHouseSave): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/getGoodsOptions',
    method: 'post',
    data
  })
}

//  出库操作
export function wareHouseOut(data: WareHouseOut): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/wareHouseOut',
    method: 'post',
    data
  })
}
  //入库记录查询接口
export function wareHouseInSearch(data: WareHouseInSearch): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/wareHouseInSearch',
    method: 'post',
    data
  })
}
    //出库记录查询接口
export function wareHouseOutSearch(data: WareHouseOut): Promise<ResponseData<WareHouseInResult>> {
  return request({
    url: '/WareHouseIn/wareHouseOutSearch',
    method: 'post',
    data
  })
}