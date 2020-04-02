import Layout from '@/layout'
export const defaultRouterMap = [
  // {
  //   path: '/message',
  //   component: Layout,
  //   name: 'message',
  //   children: [
  //     {
  //       path: 'list',
  //       name: '消息提醒',
  //       component: () => import('@/views/message/list'),
  //       meta: {title: '消息提醒', icon: 'message'},
  //     }
  //   ],
  // },
  // {
  //   path: '/adviceFeedback',
  //   component: Layout,
  //   name: 'adviceFeedback',
  //   permission : ["cf:common:list", "cf:manage:reply"],
  //   logical : 'or',
  //   children: [
  //     {
  //       path: 'list',
  //       name: '反馈',
  //       component: () => import('@/views/adviceFeedback/list'),
  //       meta: { title: '咨询反馈', icon: 'feedback',},
  //     },
  //     {
  //       path: 'reply',
  //       name: '回复',
  //       hidden: true,
  //       component: () => import('@/views/adviceFeedback/reply'),
  //       meta: {title: '回复', parentPath :  "/adviceFeedback/list", fallbackPath : "/adviceFeedback/list", fallbackConfirm : true},
  //     },
  //     {
  //       path: 'detail',
  //       name: '详情',
  //       hidden: true,
  //       component: () => import('@/views/adviceFeedback/detail'),
  //       meta: {title: '详情',parentPath :  "/adviceFeedback/list", fallbackPath : "/adviceFeedback/list", fallbackConfirm : true},
  //     }
  //   ],
  // },
  {
    path: '/report',
    component: Layout,
    name: 'report',
    meta: {title: '体温上报记录'},
    children: [
      {
        path: 'studentList',
        name: '学生体温上报记录',
        component: () => import('@/views/report/studentReportList'),
        meta: {title: '学生体温上报记录', icon: 'studentReport',}
      },
      {
        path: 'teacherList',
        name: '教职工体温上报记录',
        component: () => import('@/views/report/teacherReportList'),
        meta: {title: '教职工体温上报记录', icon: 'teacherReport',},
      }
    ]
  },
  {
    path: '/health',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '人员健康监测',
        component: () => import('@/views/healthMonitoring/index'),
        meta: {title: '人员健康监测', icon: 'health'},
      },

    ],
  },
  {
    path: '/healthReport',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '健康监测报告',
        component: () => import('@/views/monitoringReport/index'),
        meta: {title: '健康监测报告', icon: 'report'},
      },

    ],
  },

  {
    path: '/config',
    component: Layout,
    meta: {title: '系统设置'},
    children: [
      {
        path: 'index',
        name: {title: '系统设置', icon: 'config'},
        component: () => import('@/views/config/index'),
        meta: {title: '系统设置', icon: 'config'},
      },
      {
        path: 'student',
        name: 'studentConfig',
        hidden : true,
        component: () => import('@/views/config/student'),
        meta: {title: '学生体温异常通知人员设置', icon: 'student', parentPath : "/config/index",},
      },
      {
        path: 'staff',
        name: ' staffConfig',
        hidden : true,
        component: () => import('@/views/config/staff'),
        meta: {title: ' 教职工体温异常通知人员设置', icon: 'student', parentPath : "/config/index",},
      },
      {
        path: 'import',
        name: ' import',
        component: () => import('@/views/config/dataImport'),
        meta: {title: ' 体温枪数据导入', icon: 'dataImport', parentPath : "/config/dataImport",},
      },
    ],
  },
]
