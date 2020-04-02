import request from '@/utils/request'
const qs = require("qs")
//退出
export function logout(params) {
  return request({
    url: '/auth/logout',
    method: 'get'
  })
}

//修改密码
export function updatePwd(params) {
  return request({
    url: '/auth/updatePwd',
    method: 'post',
    data: qs.stringify(params)
  })
}
//更新用户信息
export function updateUserInfo(params) {
  return request({
    url: '/header/updateUserInfo',
    method: 'post',
    data: qs.stringify(params)
  })
}

//用户消息分页信息
export function userMsgList(params) {
  return request({
    url: '/header/userMsgList',
    method: 'get',
    params: params
  })
}


//获取未读消息数
export function unreadMsgCount(params) {
  return request({
    url: '/header/unreadMsgCount',
    method: 'get'
  })
}
//阅读消息接口
export function readUserMsg(params) {
  return request({
    url: '/header/readUserMsg',
    method: 'post',
    data: qs.stringify(params)
  })
}

//消息中心获取用户未读信息
export function unreadMsgCountNew(params) {
  return request({
    url: '/header/unreadMsgCount',
    method: 'get',
    params: params
  })
}
