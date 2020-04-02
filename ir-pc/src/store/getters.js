const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  userInfoGotten: state => state.user.userInfoGotten,
  userInfo : statue=> statue.user.userInfo,
  permissions: state => state.permission.permissions,
  addRouters: state => state.permission.addRouters,
  permission_routers: state => state.permission.routers,
  permissionGotten: state => state.permission.permissionGotten,
}
export default getters
