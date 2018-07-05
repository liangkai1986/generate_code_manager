<template>
  <el-container>
    <el-header><el-input
    placeholder="筛选数据库表列表"
    prefix-icon="el-icon-search"
    v-model="tableName" @input="getList" style="width:200px">
  </el-input><el-button  @click="showListTemplate()">模板管理</el-button></el-header>
    <el-main>项目列表
      <hr>
          <el-row :gutter="12" v-loading.fullscreen.lock="loading">
            <el-col :span="6" v-for="tableDataTmp in tableDataList" :key="tableDataTmp.id" >
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
                      <el-button type="success"   @click="startGenerateCodeFun(tableDataTmp.TABLE_NAME)" round>代码生成操作</el-button>
                    </el-tooltip>
                  </el-row>
              </el-card>
            </el-col>
          </el-row>
    </el-main>

    <el-dialog title="代码生成操作" :visible.sync="generateCodeVisible" :fullscreen="true">
        <el-button  @click="listGenerateCodeFun()">开始生成</el-button>
        <el-tabs type="border-card" tab-position="top">
        
        <el-tab-pane  v-for="item in templateList" :key="item.id"  :label="item.name" stretch ="true" >
          {{item.name}}

        </el-tab-pane>
       
      </el-tabs>
     
    </el-dialog>


    <el-dialog title="模板管理" :visible.sync="templateVisible" :fullscreen="true">
         <el-button  @click="saveUpdateTempaleFun()">添加模板</el-button>
         <el-tabs type="border-card" tab-position="top">
          <el-tab-pane  v-for="item in templateList" :key="item.id"  :label="item.name" stretch ="true" >
            <el-button   @click="saveUpdateTempaleFun(item)">保存【 {{item.name}}】模板</el-button>
            <hr/>
            <el-input placeholder="模板名称" v-model="item.name"  style="width:200px"></el-input>
            <el-input placeholder="模板路径" v-model="item.path"  style="width:500px"></el-input>
             <hr/>
            <textarea rows="50" cols="150" v-model="item.content"></textarea>

          </el-tab-pane>
       
         </el-tabs>

     
    </el-dialog>

  </el-container>



  
</template>

<script>
export default {
  name: "tableInfo",
  created: function() {
    this.projectId = this.$route.query.id;
    this.getList();

    // `this` 指向 vm 实例
  },
  data() {
    return {
      projectId: null,
      tableName: "",
      generateCodeVisible: false,
      tableDataList: [],
      templateList: [],
      tableData: {},
      loading: true,
      startGenerateCodeTableName: null,
      templateVisible: false
    };
  },
  methods: {
    startGenerateCodeFun: function(tableName) {
      this.startGenerateCodeTableName = null;
      this.startGenerateCodeTableName = tableName;
      console.log(tableName);
      this.getTemplateListFun();
      this.generateCodeVisible = true;
    },
    listGenerateCodeFun: function() {
      for (var i = 0; i < this.templateList.length; ++i) {
        let data = this.templateList[i];
        console.log(data);
        this.generateCodeFun(data);
        //this.generateCodeFun(data);
      }

    },
    generateCodeFun: function(templateInfo) {
      var thisVar = this;
      const qs = require("qs");

      let data = {
        projectId: thisVar.projectId,
        tableName: thisVar.startGenerateCodeTableName,
        templateId: templateInfo.template_id
      };

      this.$http
        .post("/template/generateCode", qs.stringify(data))
        .then(function(response) {
          thisVar.loading = false;
          if (response.data.ok) {
            thisVar.$message.success("成功");
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          thisVar.loading = false;
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
    copyObj: function(data) {
      var obj = null;
      obj = JSON.parse(JSON.stringify(data)); //this.templateData是父组件传递的对象
      return obj;
    },
    getTemplateListFun: function() {
      var thisVar = this;
      this.loading = true;
      const qs = require("qs");
      var thisVar = this;
      thisVar.templateList = null;
      let data = {
        projectId: thisVar.projectId
      };

      this.$http
        .post("/template/list", qs.stringify(data))
        .then(function(response) {
          thisVar.loading = false;
          if (response.data.ok) {
            thisVar.templateList = response.data.data;
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          thisVar.loading = false;
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
    getList: function() {
      var thisVar = this;
      this.loading = true;
      const qs = require("qs");
      var thisVar = this;
      let data = {
        projectId: thisVar.projectId,
        tableName: thisVar.tableName
      };
      this.$http
        .post("/tableInfo/listTable", qs.stringify(data))
        .then(function(response) {
          thisVar.loading = false;
          if (response.data.ok) {
            thisVar.tableDataList = response.data.data;
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          thisVar.loading = false;
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
    showListTemplate: function() {
      this.templateVisible = true;
      this.getTemplateListFun();
    },
    saveUpdateTempaleFun: function(data) {
      const qs = require("qs");
      var thisVar = this;

      let dataTmp = {
        project_id: thisVar.projectId
      };

      if (data != null) {
        dataTmp = {
          template_id: data.template_id,
          project_id: thisVar.projectId,
          name: data.name,
          content: data.content,
          path: data.path
        };
      }

      this.$http
        .post("/template/saveUpdate", qs.stringify(dataTmp))
        .then(function(response) {
          if (response.data.ok) {
            thisVar.$message.success("添加或者修改成功");
            thisVar.addTableVisible = false;
            thisVar.getTemplateListFun();
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          thisVar.$message.error("网络错误");
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