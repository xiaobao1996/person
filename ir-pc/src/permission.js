import router from './router'
import store from './store'
import {MessageBox} from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import getPageTitle from '@/utils/get-page-title'

import {defaultRouterMap} from '@/router/routerConfig'
import {getTicket, getNewUrl, getSchoolId} from '@/utils/index'
import {checkLoginStatus, userPermissionList} from '@/api/login'


NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach(async(to, from, next) => {

  // set page title
  document.title = getPageTitle(to.meta.title)

  let toPage = true;

  //获取学校信息
  let schoolId = getSchoolId();
  if (schoolId && schoolId.trim() !== '' ) {
    sessionStorage.setItem("schoolId", schoolId);
  } else {
    let curSchoolId = sessionStorage.getItem("schoolId");
    if (!curSchoolId || curSchoolId.trim() === "") {
      toPage = false;
      MessageBox.alert('缺少学校信息，请关闭此页面，从首页重新打开', '错误', {
        confirmButtonText: '确定',
      });
    }
  }

  if (toPage) {


    let ticket = getTicket();

    checkLoginStatus(ticket).then((res) => {
      if (res) {
        if (res.code === 200) {
          if (ticket && ticket.trim() !== '') {
            location.replace(getNewUrl());
          } else {
            if (!store.getters.userInfoGotten) {
              store.dispatch('GetInfo').then(() => {

              });
            }

            if (!store.getters.permissionGotten) { // 判断当前用户是否已拉取完permission信息
              userPermissionList().then((res) => {
                if (res.code === 200) {
                  // start progress bar
                  NProgress.start()
                  store.dispatch('GenerateRoutes', res.result).then(() => { // 根据roles权限生成可访问的路由表
                    store.dispatch('PermissionGotten', true);
                    router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                    next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
                  });
                } else {
                  MessageBox.alert(res.message, '错误', {
                    confirmButtonText: '确定',
                  });
                }
              });

            } else {
              // start progress bar
              NProgress.start()
              next()
            }
          }
        } else {
          MessageBox.alert(res.message, '错误', {
            confirmButtonText: '确定',
          });
        }
      }
    })
  }


})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
