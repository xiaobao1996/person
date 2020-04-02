import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
//自定义主题
import '@/styles/element-style.scss'
import '@/styles/index.scss' // global css

import axios from 'axios'
Vue.prototype.$axios = axios
import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon


import '@/permission' // permission control 暂时注释
Vue.use(ElementUI)
// sessionStorage.setItem("schoolId", "d3746e6564374233bffd66e3612c9924")
Vue.config.productionTip = false
Date.prototype.format = function(format) {
  let o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(),    //day
    "h+" : this.getHours(),   //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
    "S" : this.getMilliseconds() //millisecond
  }
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(let k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
      RegExp.$1.length==1 ? o[k] :
        ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
}
import moment from 'moment'
// 定义全局的过滤器
Vue.filter('dateFormat', function (dataStr, pattern = "YYYY/MM/DD HH:mm") {
  return moment(dataStr).format(pattern)
})
Vue.filter('timeFormat', function (dataStr, pattern = "YYYY-M-D HH:mm") {
  if (dataStr === null || dataStr === '') {
    return
  }
  return moment(dataStr).format(pattern)
})
Vue.filter('pointTimeFormat', function (dataStr, pattern = "YYYY.M.D") {
  if (dataStr === null || dataStr === '') {
    return null
  }
  return moment(dataStr).format(pattern)
})
Vue.filter('timeFormatHm', function (dataStr, pattern = "HH:mm") {
  return moment(dataStr).format(pattern)
})

Vue.filter('weekFormat', function (dataStr, pattern = "d") {
 let week =  moment(dataStr).format(pattern);
  let res = '';
  switch (week) {
    case '0':
      res =   "周日";

      break;
    case '1':
      res =   "周一";
      break;
    case '2':
      res =   "周二";
      break;
    case '3':
      res =   "周三";
      break;
    case '4':
      res =   "周四";
      break;
    case '5':
      res =   "周五";
      break;
    case '6':
      res =   "周六";
      break;
    default:
      res =   "未知";
  }
  return res;
})


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
