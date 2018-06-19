<template>
  <el-container>
    <el-header>增加jdbc{{msg}}</el-header>
    <el-main>列表
          <el-table :data="tableData" height="500" border style="width: 100%">
            <el-table-column prop="jdbc_config_id" label="日期" width="120"  header-align="center"></el-table-column>
            <el-table-column prop="date" label="日期" width="120"  header-align="center"></el-table-column>
            <el-table-column prop="date" label="日期" width="120"  header-align="center"></el-table-column>
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
      tableData: [{ jdbc_config_id: "b" }]
    };
  },
  methods: {
    getList: function() {
      alert("dd"+this.msg);
      this.$http
        .post("/jdbcConfig/list")
        .then(function(response) {
          console.log(this.msg);
          this.msg="ddddddddd";
          console.log(response.data[0]);
          console.log(response.data[0].jdbc_config_id);
          VM.tableData.push({
            "jdbc_config_id": response.data[0].jdbc_config_id
          });
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