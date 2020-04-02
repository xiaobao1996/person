import { defaultRouterMap } from '@/router/routerConfig'
import { constantRouterMap } from '@/router/index'
import {hasPermission} from '@/utils/utils'
const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: [],
    permissionGotten : false,
    permissions : []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    },
    SET_PERMISSIONGOTTEN: (state, status) => {
      state.permissionGotten = status;
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions;
    },
    SET_VISITHOMECOUNT: (state, nowCount) => {
      state.visitHomeCount = nowCount;
    },
  },
  actions: {
    SET_VISITHOMECOUNT({commit}, nowCount) {
      return new Promise(resolve => {
        commit('SET_VISITHOMECOUNT', nowCount)
        resolve()
      });
    },
    PermissionGotten({commit}, status) {
      return new Promise(resolve => {
        commit('SET_PERMISSIONGOTTEN', status)
        resolve()
      });
    },
    GenerateRoutes({ commit }, permissionList) {
      return new Promise(resolve => {

        let accessedRouters;
        commit('SET_PERMISSIONS', permissionList)
        if (permissionList && permissionList.length && permissionList.length > 0) {
          let routerList = [];
          let availableRouter = defaultRouterMap;
          for (let i = 0; i < availableRouter.length; i ++) {
            if (hasPermission(permissionList, availableRouter[i])) {
              routerList.push(availableRouter[i]);
            }
          }
          // let newRouter = Object.assign({}, defaultRouterMap[2]);
          // newRouter.children = routerList;
          accessedRouters = routerList
        } else {
          accessedRouters = []
        }
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission
