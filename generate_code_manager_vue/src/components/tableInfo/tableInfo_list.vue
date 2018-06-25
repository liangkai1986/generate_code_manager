<template>
  <el-container>
    <el-header><el-button  @click="addJdbcInfo()">增加项目</el-button><el-button  @click="go_href('jdbcConfigList')">数据源管理</el-button></el-header>
    <el-main>项目列表
      <hr>
          <el-row :gutter="12" v-loading.fullscreen.lock="loading">
            <el-col :span="6" v-for="tableDataTmp in tableDataList" :key="tableDataTmp" >
              <el-card    shadow="always" :body-style="{ height: '180px' }">
                  <el-row>
                  <el-tooltip  class="item" effect="dark" content="這是数据库表名称" placement="top-start">
                      <el-tag>{{tableDataTmp.TABLE_NAME}}</el-tag>
                  </el-tooltip>
                  </el-row>
                  <br>
                  <el-row>
                  <el-tooltip class="item" effect="dark" content="這是数据库表备注" placement="top-start">
                      <el-tag>{{tableDataTmp.TABLE_COMMENT}}</el-tag>
                  </el-tooltip>
                  </el-row>
                  <br>
                  <el-row>

                    <el-tooltip class="item" effect="dark" content="生成代码的操作" placement="top-start">
                      <el-button type="success" round>代码生成操作</el-button>
                    </el-tooltip>
                  </el-row>
              </el-card>
            </el-col>
          </el-row>
    </el-main>


  </el-container>



  
</template>

<script>
export default {
  name: "tableInfo",
  created: function() {
    this.projectId = this.$route.query.id;

    this.getList();
    this.getJdbcConfigList();
    // `this` 指向 vm 实例
  },
  data() {
    return {
      projectId:null,
      tableName:"",
      addTableVisible: false,
      tableDataList: [],
      JdbcDataList: [],
      tableData: {},
      loading:true,
      loadingTable:true
    };
  },
  methods: {
    getList: function() {
      var thisVar = this;
      this.loading=true;
      const qs = require("qs");
      var thisVar = this;
      let data = {projectId:thisVar.projectId,
              tableName:thisVar.tableName};

      this.$http
        .post("/tableInfo/listTable",qs.stringify(data))
        .then(function(response) {
          thisVar.loading=false;
          if (response.data.ok) {
            thisVar.tableDataList = response.data.data;
          } else {
             thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
           thisVar.loading=false;
           console.log(error);
           thisVar.$message.error('网络错误');
        });
    },
    
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  background-color: #b3c0d1;
  color: #333;

  line-height: 60px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}
</style>