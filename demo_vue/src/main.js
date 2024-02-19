import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

//axios
import instance from "./axios/index";

import "./permission"
import "./global/global.css"

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(ElementUI);
Vue.use(mavonEditor)
Vue.config.productionTip = false
Vue.prototype.request=instance


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
