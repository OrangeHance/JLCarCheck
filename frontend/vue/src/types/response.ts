// 定义后端返回数据类型
export interface ResponseData<T = any> {
  code: number
  msg: string
  data: T
}