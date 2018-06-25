import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import jdbcConfigList from '@/components/jdbc_config/jdbc_config_list'
import projectList from '@/components/project/project_list'
import tableInfoList from '@/components/tableInfo/tableInfo_list'

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: "/jdbcConfigList",
      name: 'jdbcConfigList',
      component: jdbcConfigList
    },
    {
      path: "/projectList",
      name: 'projectList',
      component: projectList
    },
    {
      path: "/tableInfoList",
      name: 'tableInfoList',
      component: tableInfoList
    }
  ]

})
