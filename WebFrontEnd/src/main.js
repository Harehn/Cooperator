// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import VueCookies from 'vue-cookies';
import BootstrapVue from "bootstrap-vue";
import App from './App';
import router from './router';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.use(VueCookies);
Vue.use(BootstrapVue);
Vue.config.productionTip = false;

VueCookies.config('7d');
VueCookies.set('theme','default');
VueCookies.set('hover-time','1s');

/* eslint-disable no-new */
new Vue({
  el: '#app',

  template: '<App/>',
  components: {
     App
   }
})
