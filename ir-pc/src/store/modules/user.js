import { getLoginUserInfo } from '@/api/login'
import { Message } from 'element-ui'

const user = {
  state: {
    // token: getToken(),
    // name: '',
    // avatar: '',
    // roles: []
    userInfo : {},
    userInfoGotten : false
  },

  mutations: {
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
    },
    SET_USERINFOGOTTEN: (state, userInfoGotten) => {
      state.userInfoGotten = userInfoGotten
    },

    // SET_NAME: (state, name) => {
    //   state.name = name
    // },
    // SET_AVATAR: (state, avatar) => {
    //   state.avatar = avatar
    // },
    // SET_ROLES: (state, roles) => {
    //   state.roles = roles
    // }
  },

  actions: {
    // 获取用户信息
    GetInfo({commit}) {
      return new Promise((resolve, reject) => {
        getLoginUserInfo().then(res => {
          if (res.code === 200) {
            commit('SET_USERINFO', res.result)
            commit('SET_USERINFOGOTTEN', true)
            resolve();
          } else {
            Message.error(res.message);
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
  }
}

export default user
