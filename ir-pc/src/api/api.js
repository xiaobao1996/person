import request from '@/utils/request'
const qs = require("qs")
/*************    班级    ***************/
//获取学校班级选项信息
export function getClassOptionList() {
  return request({
    url: '/busi/gradeoption/list',
    method: 'get'
  })
}

//根据年级查询班级信息
export function getClassesByGrade(params) {
  return request({
    url: '/busi/class/getClassesByGrade',
    method: 'get',
    params : params
  })
}

//获取班级信息
export function classList(params) {
  return request({
    url: '/busi/class/list',
    method: 'get',
    params : params
  })
}

//新增或更新班级信息
export function saveOrUpdateClass(params) {
  return request({
    url: '/busi/class/saveOrUpdate',
    method: 'post',
    data : qs.stringify(params)
  })
}

//停用/启用班级信息
export function changeClassStatus(params) {
  return request({
    url: '/busi/class/changeStatus',
    method: 'post',
    data : qs.stringify(params)
  })
}

//新增或更新班级信息
export function deleteClass(params) {
  return request({
    url: '/busi/class/delete',
    method: 'post',
    data : qs.stringify(params)
  })
}

/****************    学生    ***************/
// //获取学生列表
// export function getStudentList(params) {
//   return request({
//     url: '/busi/studentV4/list',
//     method: 'get',
//     params : params
//   })
// }

//新增或更新学生信息
export function saveOrUpdateStudent(params) {
  return request({
    url: '/busi/studentV4/saveOrUpdate',
    method: 'post',
    data : qs.stringify(params)
  })
}

//更新学生学籍信息
export function updateBusiStudent(params) {
  return request({
    url: '/busi/studentV4/updateBusiStudent',
    method: 'post',
    data : qs.stringify(params)
  })
}

//学生分配班级
export function supplyClass(params) {
  return request({
    url: '/busi/studentV4/supplyClass',
    method: 'post',
    data : qs.stringify(params)
  })
}

//获取学生详情
export function studentDetail(params) {
  return request({
    url: '/busi/studentV4/' + params.id,
    method: 'get',
    params : params
  })
}

//删除学生
export function deleteStudent(params) {
  return request({
    url: '/busi/studentV4/delete',
    method: 'post',
    data : qs.stringify(params)
  })
}


//获取所有班级信息
export function allClassList(params) {
  return request({
    url: '/busi/class/allList',
    method: 'get',
    params : params
  })
}

/************     学生家长    ***********/
//获取家长列表
export function parentList(params) {
  return request({
    url: '/busi/studentV4/parentList',
    method: 'get',
    params : params
  })
}

//新增或更新家长信息
export function saveOrUpdateParent(params) {
  return request({
    url: '/busi/studentV4/saveOrUpdateParent',
    method: 'post',
    data : qs.stringify(params)
  })
}

//删除家长信息
export function deleteParent(params) {
  return request({
    url: '/busi/studentV4/deleteParent',
    method: 'post',
    data : qs.stringify(params)
  })
}



/****************    教师  ***************/
//获取教师列表
export function getTeacherList(params) {
  return request({
    url: '/busi/teacherV4/list',
    method: 'get',
    params : params
  })
}
//获取教师详情
export function teacherDetail(params) {
  return request({
    url: '/busi/teacherV4/' + params.id,
    method: 'get',
    params : params
  })
}

//新增或更新学教师信息
export function saveOrUpdateTeacher(params) {
  return request({
    url: '/busi/teacherV4/saveOrUpdate',
    method: 'post',
    data : qs.stringify(params)
  })
}

//删除教师信息
export function deleteTeacher(params) {
  return request({
    url: '/busi/teacherV4/delete',
    method: 'post',
    data : qs.stringify(params)
  })
}


//更新教师职务信息
export function updateTeacherJob(params) {
  return request({
    url: '/busi/teacherV4/updateJob',
    method: 'post',
    data : qs.stringify(params)
  })
}

//教师分配部门
export function supplyOffice(params) {
  return request({
    url: '/busi/teacherV4/supplyOffice',
    method: 'post',
    data : qs.stringify(params)
  })
}

//获取部门列表
export function officeList(params) {
  return request({
    url: '/busi/teacherV4/officeList',
    method: 'get',
    params : params
  })
}

/******  教职工   ******/
//教职工列表
export function staffList(params) {
  return request({
    url: '/busi/class/staffList',
    method: 'get',
    params : params
  })
}

//设置班主任
export function updateHeadTeacher(params) {
  return request({
    url: '/busi/class/updateHeadTeacher',
    method: 'post',
    data : qs.stringify(params)
  })
}

//获取词典数据
export function dictList(params) {
  return request({
    url: '/busi/dict/list',
    method: 'get',
    params : params
  })
}


/*********  场所管理  *******/
//获取场所列表
export function placeList(params) {
  return request({
    url: '/busi/place/list',
    method: 'get',
    params : params
  })
}

//新增或更新场所
export function saveOrUpdatePlace(params) {
  return request({
    url: '/busi/place/saveOrUpdate',
    method: 'post',
    data : qs.stringify(params)
  })
}

//删除场所
export function deletePlace(params) {
  return request({
    url: '/busi/place/delete',
    method: 'post',
    data : qs.stringify(params)
  })
}


/****  科目管理  ****/
//获取场所列表
export function subjectList(params) {
  return request({
    url: '/busi/subject/list',
    method: 'get',
    params : params
  })
}

//新增或更新场所
export function saveOrUpdateSubject(params) {
  return request({
    url: '/busi/subject/saveOrUpdate',
    method: 'post',
    data : qs.stringify(params)
  })
}

//删除场所
export function deleteSubject(params) {
  return request({
    url: '/busi/subject/delete',
    method: 'post',
    data : qs.stringify(params)
  })
}

//校验科目参数
export function checkSubject(params) {
  return request({
    url: '/busi/subject/check',
    method: 'get',
    params : params
  })
}


/****  课节管理  ****/
//获取课节列表
export function lessonList(params) {
  return request({
    url: '/busi/lesson/list',
    method: 'get',
    params : params
  })
}

//设置课节
export function saveLesson(params) {
  return request({
    url: '/busi/lesson/save',
    method: 'post',
    data : qs.stringify(params)
  })
}

//删除课节
export function deleteLesson(params) {
  return request({
    url: '/busi/lesson/delete',
    method: 'post',
    data : qs.stringify(params)
  })
}

// 咨询列表
export function adviceList(params) {
  return request({
    url: '/consultation/lists',
    method: 'post',
    data : qs.stringify(params)
  })
}

// 咨询主题类型
export function adviceThemeType(params) {
  return request({
    url: '/consultation/type',
    method: 'post',
    data : qs.stringify(params)
  })
}
//反馈详情
export function adviceDetail(params) {
  return request({
    url: '/consultation/selectAdvice',
    method: 'post',
    data : qs.stringify(params)
  })
}

//保存信息
export function saveAdviceFeedback(params) {
  return request({
    url: '/consultation/saveFeedback',
    method: 'post',
    data : qs.stringify(params)
  })
}

// 用户消息中心相关
export function userMsgList(params) {
  return request({
    url: '/header/feedback/userMsgList',
    method: 'post',
    data : qs.stringify(params)
  })
}

//阅读信息
export function readMsg(params) {
  return request({
    url: '/header/feedback/readUserMsg',
    method: 'post',
    data : qs.stringify(params)
  })
}


// 获取学生记录信息
export  function getStudentInfoReport(params) {
  return request({
    url: '/busi/irTemperatureReport/list',
    method: 'get',
    params : params
  })
}

// 获取班级列表
export  function getClassList(params) {
  return request({
    url: '/busi/irTemperatureReport/findClass',
    method: 'get',
    params : params
  })
}

//获取学生检测记录
export  function getStudentHealthMonitor(params) {
  return request({
    url: '/busi/healthMonitor/studentList',
    method: 'get',
    params : params
  })
}

//获取教职工信息
export  function getStaffHealthMonitor(params) {
  return request({
    url: '/busi/healthMonitor/staffList',
    method: 'get',
    params : params
  })
}

// 添加测温记录
export function saveMonitorHistory(params) {
  return request({
    url: '/busi/healthMonitor/saveMonitorHistory',
    method: 'post',
    data : qs.stringify(params)
  })
}
//  获取身份列表(学生/教职工)
export  function getIdentifyList(params) {
  return request({
    url: '/busi/reportConfig/identifyList',
    method: 'get',
    params : params
  })
}

export  function getStudentList(params) {
  return request({
    url: '/busi/user/studentList',
    method: 'get',
    params : params
  })
}

export  function getStaffList(params) {
  return request({
    url: '/busi/user/staffList',
    method: 'get',
    params : params
  })
}
//获取学生家长
export  function getParents(params) {
  return request({
    url: '/busi/user/parents',
    method: 'get',
    params : params
  })
}

//获取测温记录
export  function getMonitorHistory(params) {
  return request({
    url: '/busi/healthMonitor/monitorHistory',
    method: 'get',
    params : params
  })
}


//获取场所集合
export  function getPlaceList(params) {
  return request({
    url: '/busi/healthMonitor/placeList',
    method: 'get',
    params : params
  })
}
//获取学生异常信息
export  function getStudentCalc(params) {
  return request({
    url: '/busi/healthMonitor/studentCalc',
    method: 'get',
    params : params
  })
}

//获取教职工异常信息
export  function getStaffCalc(params) {
  return request({
    url: '/busi/healthMonitor/staffCalc',
    method: 'get',
    params : params
  })
}



//获取学生健康监测信息
export  function getMonitorStudentCalc(params) {
  return request({
    url: '/busi/monitorCalc/student',
    method: 'get',
    params : params
  })
}


//获取教职工健康监测信息
export  function getMonitorStaffCalc(params) {
  return request({
    url: '/busi/monitorCalc/staff',
    method: 'get',
    params : params
  })
}



//获取配置详情
export  function getConfigDetail(params) {
  return request({
    url: '/busi/config/detail',
    method: 'get',
    params : params
  })
}

// 保存配置
export function saveConfig(params) {
  return request({
    url: '/busi/config/save',
    method: 'post',
    data : qs.stringify(params)
  })
}

//获取通知配置
export  function getReportConfig(params) {
  return request({
    url: '/busi/reportConfig/detail',
    method: 'get',
    params : params
  })
}
//获取获取身份列表
export  function getReportIdentifyList(params) {
  return request({
    url: '/busi/reportConfig/identifyList',
    method: 'get',
    params : params
  })
}

// 添加测温记录
export function saveReportConfig(params) {
  return request({
    url: '/busi/reportConfig/save',
    method: 'post',
    data : qs.stringify(params)
  })
}

//获取通知人数
export function getReportCountDetail(params) {
  return request({
    url: '/busi/reportConfig/countDetail',
    method: 'get',
    params : params
  })
}
//通知相关人员
export function notifyConcat(params) {
  return request({
    url: '/busi/healthMonitor/notify',
    method: 'get',
    params : params
  })
}

///findUserInfo获取用户详情
export function findUserInfo(params) {
  return request({
    url: '/busi/irTemperatureReport/findUserInfo',
    method: 'get',
    params : params
  })
}

//获取体温枪数据
export function getGunList(params) {
  return request({
    url: '/busi/gun/list',
    method: 'get',
    params : params
  })
}











