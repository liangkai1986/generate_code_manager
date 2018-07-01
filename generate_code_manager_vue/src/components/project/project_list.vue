<template>
  <el-container>
    <el-header><el-button  @click="addJdbcInfo()">增加项目</el-button><el-button  @click="go_href('jdbcConfigList')">数据源管理</el-button></el-header>
    <el-main>项目列表
      <hr>
          <el-row :gutter="12" v-loading.fullscreen.lock="loading">
            <el-col :span="8" v-for="tableDataTmp in tableDataList" :key="tableDataTmp.id" >
              <el-card    shadow="always" :body-style="{ height: '180px' }">
                  <el-row>
                  <el-tooltip  class="item" effect="dark" content="這是項目名稱" placement="top-start">
                      <el-tag>{{tableDataTmp.name}}</el-tag>
                  </el-tooltip>
                  </el-row>
                  <br>
                  <el-row>
                  <el-tooltip class="item" effect="dark" content="這是 数据源 的名称" placement="top-start">
                      <el-tag>{{tableDataTmp.jdbcName}}</el-tag>
                  </el-tooltip>
                  </el-row>
                  <br>
                  <el-row>
                    <el-tooltip class="item" effect="dark" content="删除项目" placement="top-start">
                      <el-button @click="delFun(tableDataTmp.project_id)" type="danger" icon="el-icon-delete" circle>删除</el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="修改项目信息" placement="top-start">
                      <el-button @click="editFun(tableDataTmp.project_id)" type="primary" icon="el-icon-edit" circle>编辑</el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="生成代码的操作" placement="top-start">
                      <router-link  to="/tableInfoList?id=1" target="_blank"><el-button type="success" round>代码生成操作</el-button></router-link>
                    </el-tooltip>
                  </el-row>
              </el-card>
            </el-col>
          </el-row>
    </el-main>

    <el-dialog title="编辑" :visible.sync="addTableVisible">
        <el-form ref="form" :model="tableData" label-width="80px">
          <el-form-item label="id">
              <el-input v-model="tableData.project_id" :disabled="true"></el-input>
          </el-form-item>
           <el-form-item label="名称">
              <el-input v-model="tableData.name"></el-input>
          </el-form-item>
          <el-form-item label="数据源">
            <el-radio-group    v-model="tableData.jdbc_config_id" size="medium" >
              <el-radio v-for="jdbcDataTmp in JdbcDataList" :key="jdbcDataTmp.id" :label="jdbcDataTmp.jdbc_config_id"  border >{{jdbcDataTmp.name}}</el-radio>

            </el-radio-group>
          </el-form-item>
              <el-button type="primary" @click="saveUpdateFun">立即创建</el-button>
        </el-form>
    </el-dialog>
  </el-container>



  
</template>

<script>
export default {
  name: "HelloWorld",
  created: function() {

     
    this.getList();
    this.getJdbcConfigList();
    // `this` 指向 vm 实例
  },
  data() {
    return {
      addTableVisible: false,
      tableDataList: [],
      JdbcDataList: [],
      tableData: {},
      loading:true,
      loadingTable:true
    };
  },
  methods: {
    go_href(href){
      window.open(href, '_blank');
    },
    getList: function() {
      var thisVar = this;
      this.loading=true;
      this.$http
        .post("/project/list")
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
    getJdbcConfigList: function() {
      var thisVar = this;
      this.$http
        .post("/jdbcConfig/list")
        .then(function(response) {
          thisVar.loading=false;
          if (response.data.ok) {
            thisVar.JdbcDataList = response.data.data;
          } else {
             thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
           console.log(error);
           thisVar.$message.error('网络错误');
        });
    },
    delFun: function(id) {
      var thisVar = this;
      const qs = require("qs");
      this.$http
        .post("/project/del", qs.stringify({ id: id }))
        .then(function(response) {
          if(response.data.ok){
           thisVar.$message.success('刪除成功');
           thisVar.getList();
          }else{
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          thisVar.$message.error('网络错误');
        });
    },
    getData: function(id) {
      const qs = require("qs");
      var thisVar = this;
      this.$http
        .post("/project/get", qs.stringify({ id: id }))
        .then(function(response) {
          thisVar.tableData = response.data.data;
          console.log(thisVar.tableData);
        })
        .catch(function(error) {
          console.log(error);
           thisVar.$message.error('网络错误');
        });
    },
    editFun: function(id) {
      this.addTableVisible = true;
      this.tableData={};
      this.getData(id);
    },
    addJdbcInfo: function() {
      this.addTableVisible = true;
      this.tableData={};
    },
    saveUpdateFun: function() {
      const qs = require("qs");
      var thisVar = this;
      let data = {
        jdbc_config_id: this.tableData.jdbc_config_id,
        project_id: this.tableData.project_id,
        name: this.tableData.name
      };

      this.$http
        .post("/project/saveUpdate", qs.stringify(data))
        .then(function(response) {
          if(response.data.ok){
           thisVar.$message.success('添加或者修改成功');
           thisVar.addTableVisible = false;
           thisVar.getList();
          }else{
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
           thisVar.$message.error('网络错误');
        });
    }
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