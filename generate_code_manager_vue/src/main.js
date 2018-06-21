// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);


import router from './router'

Vue.config.productionTip = false


import axios from 'axios'
axios.defaults.baseURL = 'http://127.0.0.1:8083/generate_code_manager_java';

Vue.prototype.$http = axios

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router, axios,
  components: { App },
  template: '<App/>'
})
