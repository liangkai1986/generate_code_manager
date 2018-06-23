<template>
  <el-container>
    <el-header><el-button  @click="addJdbcInfo()">增加jdbc記錄</el-button></el-header>
    <el-main>列表
          <el-table :data="tableDataList" height="500" border style="width: 100%"  v-loading="loading">
            <el-table-column prop="jdbc_config_id" label="id" width="50"  header-align="center"></el-table-column>
            <el-table-column prop="name" label="名称" width="100"  header-align="center"></el-table-column>
            <el-table-column prop="user" label="账号" width="100"  header-align="center"></el-table-column>
            <el-table-column prop="password" label="密码" width="180"  header-align="center"></el-table-column>
            <el-table-column prop="jdbcUrl" label="链接" width="450"  header-align="center"></el-table-column>
            <el-table-column prop="jdbcUrl" label="操作" width="100"  header-align="center">
              <template slot-scope="scope">
                <el-button @click="delJdbcConfig(scope.row.jdbc_config_id)" type="text" size="small">删除</el-button>
                <el-button @click="editJdbcInfo(scope.row.jdbc_config_id)" type="text" size="small">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
    </el-main>

    <el-dialog title="编辑" :visible.sync="addTableVisible">
        <el-form ref="form" :model="tableData" label-width="80px">
          <el-form-item label="id">
              <el-input v-model="tableData.jdbc_config_id" :disabled="true"></el-input>
          </el-form-item>
           <el-form-item label="名称">
              <el-input v-model="tableData.name"></el-input>
          </el-form-item>
           <el-form-item label="账号">
              <el-input v-model="tableData.user"></el-input>
          </el-form-item>
           <el-form-item label="密码">
              <el-input v-model="tableData.password"></el-input>
          </el-form-item>
           <el-form-item label="链接">
              <el-input v-model="tableData.jdbcUrl"></el-input>
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
    // `this` 指向 vm 实例
  },
  data() {
    return {
      msg: "aa",
      addTableVisible: false,
      tableDataList: [],
      tableData: {},
      loading:true,
      loadingTable:true

    };
  },
  methods: {
    getList: function() {
      var thisVar = this;
      this.loading=true;
      this.$http
        .post("/jdbcConfig/list")
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
    delJdbcConfig: function(id) {
      var thisVar = this;
      const qs = require("qs");
      this.$http
        .post("/jdbcConfig/del", qs.stringify({ id: id }))
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
    getJdbcInfo: function(id) {
      const qs = require("qs");
      var thisVar = this;
      this.$http
        .post("/jdbcConfig/get", qs.stringify({ id: id }))
        .then(function(response) {
          thisVar.tableData = response.data.data;
          console.log(thisVar.tableData);
        })
        .catch(function(error) {
          console.log(error);
           thisVar.$message.error('网络错误');
        });
    },
    editJdbcInfo: function(id) {
      this.addTableVisible = true;
      this.getJdbcInfo(id);
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
        name: this.tableData.name,
        user: this.tableData.user,
        password: this.tableData.password,
        jdbcUrl: this.tableData.jdbcUrl
      };

      this.$http
        .post("/jdbcConfig/saveUpdate", qs.stringify(data))
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