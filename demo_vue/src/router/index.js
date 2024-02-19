import Vue from 'vue'
import VueRouter from 'vue-router'
import BlogDetail from '../views/BlogDetail'
import BlogEdit from '../views/BlogEdit'
import Blogs from '../views/Blogs'
import Login from '../views/Login'

Vue.use(VueRouter)

const routes = [
  //  index
  {
    path: '/',
    name: 'index',
    redirect: {name: "blogs"}
  },
  //  详情
  {
    path: '/blog/:blogId',
    name: 'blogDetail',
    component: BlogDetail,
    meta: {
      requireToken: true
    }
  },
  //  编辑or添加
  {
    path: '/blog/add',
    name: 'blogEdit',
    component: BlogEdit,
    meta: {
      requireToken: true
    }
  },
  //  全部博客页面
  {
    path: '/blogs',
    name: 'blogs',
    component: Blogs,
    meta: {
      requireToken: true
    }
  },

  //登录
  {
    path: '/login',
    name: 'login',
    component: Login
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
