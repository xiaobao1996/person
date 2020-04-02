import request from '@/utils/request'
import {getNewUrl} from '@/utils/index'

//获取登陆用户信息
export function getLoginUserInfo(params) {
  return request({
    url: '/auth/loginUserInfo',
    method: 'get'
  })
}


//判断登陆状态
export function checkLoginStatus(ticket) {
  let url = '/auth/checkLoginStatus';
  if (ticket && ticket.trim() !== '') {
    url += "?ticket=" + ticket + "&service=" + encodeURIComponent(getNewUrl());
  }
  return request({
    url: url,
    method: 'post'
  })
}

//登出接口
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}
//获取用户 可以操作的学校列表
export function userSchoolList() {
  return request({
    url: '/admin/user/userSchoolList',
    method: 'post'
  })
}
//获取用户的权限列表
export function userPermissionList() {
  return request({
    url: '/auth/userPermissionList',
    method: 'post'
  })
}
