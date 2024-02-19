import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// 用于响应事件的动作
const actions={
  ac_user(context,value){
    console.log(context,value)
    context.commit("mu_user",value)
  }
}
//用于操作数据
const mutations={
  mu_user(state,value){
    state.userInfo=value
  }
}
//用于存储数据
const state={
  userInfo:{},
}


// 创建并暴露store
export default new Vuex.Store({
  actions,
  mutations,
  state
})
