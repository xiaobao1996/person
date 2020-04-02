import axios from 'axios'
import {Message } from 'element-ui'
import { getNewUrl } from './index'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 20000 // 请求超时时间
})

// request interceptor
service.interceptors.request.use(
  config => {
    //不能缺少，需要学校信息
    config.params = {
      ...config.params,
      schoolToken: sessionStorage.getItem("schoolId"),
      // platform : "cf"
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    let res = response.data;
    // 未认证 或者 ticket过期
    if (res.code === 402 || res.code === 4002) {
      //没有ticket 跳转到sso server登陆页面
      location.replace(res.result + "?service=" + encodeURIComponent(getNewUrl()));
      return;
  }

    return res;

  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
