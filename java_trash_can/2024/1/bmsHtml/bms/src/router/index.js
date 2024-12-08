import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/user'
  },
  {
    path: '/user',
    name: 'user',
    component: () => import('../views/bms/UserView.vue')
  },
  {
    path: '/book',
    name: 'book',
    component: () => import('../views/bms/BookView.vue')
  },
  {
    path: '/user/add',
    component: () => import('../views/bms/UserAddView.vue'),
    meta: {
      title: "添加/修改员工",
      hidden: true
    }
  }
]

const router = new VueRouter({
  routes
})

export default router
