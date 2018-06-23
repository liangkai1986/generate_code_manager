<template>
  <el-container>
    <el-header>增加jdbc{{msg}}</el-header>
    <el-main>列表
          <el-table :data="tableData" height="500" border style="width: 80%">
            <el-table-column prop="jdbc_config_id" label="id" width="50"  header-align="center"></el-table-column>
            <el-table-column prop="user" label="账号" width="100"  header-align="center"></el-table-column>
            <el-table-column prop="password" label="密码" width="230"  header-align="center"></el-table-column>
            <el-table-column prop="jdbcUrl" label="链接" width="520"  header-align="center"></el-table-column>
            <el-table-column prop="jdbcUrl" label="操作" width="150"  header-align="center">
              <template slot-scope="scope">
                <el-button @click="handleClick(scope.row)" type="text" size="small">删除{{scope.row.jdbc_config_id}}</el-button>
                <el-button type="text" size="small">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
    </el-main>
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
      tableData: []
    };
  },
  methods: {
    getList: function() {
      var thisVar = this;
      this.$http
        .post("/jdbcConfig/list")
        .then(function(response) {
   
          if (response.data.ok) {
            thisVar.tableData = response.data.data;
          } else {
            alert(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          alert("网络错误");
        });
    },
    delJdbcConfig: function(id) {
      this.$http
        .post("/jdbcConfig/del")
        .then(function(response) {
          console.log(response.data.data);
          thisVar.tableData = response.data.data;
        })
        .catch(function(error) {
          console.log(error);
          alert("网络错误");
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