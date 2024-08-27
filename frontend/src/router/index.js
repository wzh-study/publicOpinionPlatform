import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * 动态路由： 菜单的内容从后台获取（数据是动态的）
 * 静态路由（constantRoutes）： 菜单的内容在前端配置，配置是写死的（数据是静态的）
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  

//   {
//     path: '/form',
//     component: Layout,
//     children: [
//       {
//         path: 'index',
//         name: 'Form',
//         component: () => import('@/views/form/index'),
//         meta: { title: '时间轴', icon: 'form' }
//       }
//     ]
//   },
  {
    path: '/userpage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'userpage',
        component: () => import('@/views/userpage/index.vue'),
        meta: { title: '用户页', icon: 'form' }
      }
    ]
  },
  {
    path: '/eventPage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'eventPage',
        component: () => import('@/views/eventPage/index.vue'),
        meta: { title: '事件页', icon: 'form' }
      }
    ]
  },

//   {
//     path: '/recognitionRiskEstimation',
//     component: Layout,
//     children: [
//       {
//         path: 'index',
//         name: 'recognitionRiskEstimation',
//         component: () => import('@/views/recognitionRiskEstimation/index.vue'),
//         meta: { title: '认知风险评估', icon: 'link' }
//       }
//     ]
//   },

  {
    path: '/qunzu',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'qunzu',
        component: () => import('@/views/QunZu/index.vue'),
        // component: () => import('@/views/QunZu/demo.vue'),

        meta: { title: '群组与敌意分析页', icon: 'link' }
      }
    ]
  },
  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: '工具类', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: '账号基本信息', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: '账号贴文信息', icon: 'tree' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
