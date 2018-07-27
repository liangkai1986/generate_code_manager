<template>
  <el-container>
    <el-header><el-input
    placeholder="筛选数据库表列表"
    prefix-icon="el-icon-search"
    v-model="tableName" @input="getList" style="width:200px">
  </el-input><el-button  @click="showListTemplate()">模板管理</el-button><el-button  @click="showConfigVisible()">配置管理</el-button></el-header>
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
        <hr/>
        <el-tabs type="border-card" tab-position="top">
        
        <el-tab-pane  v-for="item in templateList" :key="item.id"  :label="item.name" stretch ="true" >
          <el-button  @click="generateCodeJsonFun(item)">查看【{{item.name}}】的数据（方便调试模板）</el-button>
          <el-button  @click="generateCodeFun(item)">生成【{{item.name}}】</el-button>
          <el-card class="box-card"  style="margin-top:10px;">
          <div slot="header" class="clearfix">
            字段配置
          </div>

            <el-collapse  >

              <el-collapse-item v-for="columnsItem in item.columnsList" :key="columnsItem.id" >
                <template slot="title">
                  字段名：【{{columnsItem.columnInfo.columnName}}】     字段备注：【{{columnsItem.columnInfo.columnComment}}】
                  
                </template>
                
                <div>
                <el-row :gutter="12">
                  <el-col :span="8"  v-for="configItem in columnsItem.configList" :key="configItem.id">
                    <el-card shadow="hover" >
                          <span> {{configItem.config_name}}:</span>
                          <span v-if="configItem.input_type == 'radio'">
                            <el-radio-group  v-model="configItem.checkVal">
                              <el-radio-button  v-for="configKeyValItem in configItem.configKeyValList" :key="configKeyValItem.id"  :label="configKeyValItem.id">{{configKeyValItem.name}}</el-radio-button>
                            </el-radio-group>
                          </span>
                          <span v-else>
                          <el-checkbox-group  v-model="configItem.checkValList">
                             <el-checkbox-button  v-for="configKeyValItem in configItem.configKeyValList" :key="configKeyValItem.id"  :label="configKeyValItem.id">{{configKeyValItem.name}}</el-checkbox-button>
                          </el-checkbox-group>
                          </span>
                    </el-card>
                  </el-col>
                </el-row>
                </div>

              </el-collapse-item>
              <hr>
            </el-collapse>

        </el-card>




        </el-tab-pane>
       
      </el-tabs>
     
    </el-dialog>


    <el-dialog title="模板管理" :visible.sync="templateVisible" :fullscreen="true">
         <el-button  @click="saveUpdateTempaleFun()">添加模板</el-button>
         <hr/>
         <el-tabs type="border-card" tab-position="top">
          <el-tab-pane  v-for="item in templateList" :key="item.id"  :label="item.name" stretch ="true" >
            <el-button   @click="delTempaleFun(item)" type="danger">删除【 {{item.name}}】模板</el-button>
            <el-button   @click="saveUpdateTempaleFun(item)" type="primary">保存【 {{item.name}}】模板</el-button>
            <br/>
            <br/>
            <el-input placeholder="模板名称" v-model="item.name"  style="width:200px"></el-input>
            <el-input placeholder="模板路径" v-model="item.path"  style="width:500px"></el-input>
            <br/>
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>设置这个模板对应的字段配置</span>
              </div>
              <el-checkbox v-model="item.configIdList" v-for="itemConfig in configList" :key="itemConfig.config_id" :value="itemConfig.config_id"  style="margin-top:10px;" :label="itemConfig.config_id"  border>{{itemConfig.config_name}}</el-checkbox>
              
            </el-card>
            <br/>
            <textarea rows="50" cols="150" v-model="item.content"></textarea>

          </el-tab-pane>
       
         </el-tabs>

     
    </el-dialog>


    <el-dialog title="配置管理" :visible.sync="configVisible" :fullscreen="true" >
         <el-button  @click="showConfigSaveUpdateFun(null)">添加配置</el-button>
         <hr/>
            <el-main  >数据源列表
            <hr>



          <el-table :data="configList" style="width: 100%">
            <el-table-column type="expand" width="100"  header-align="center"  >
              <template slot-scope="props" >
                    <div style="text-align: left;">
                    <el-button  @click="showConfigKeyValSaveUpdateFun(null,props.row.config_id)" >添加配置值</el-button>
                    <br>
                    </div><br>
                    <el-table :data="props.row.keyValList" height="380" border style="width: 99%">
                      <el-table-column prop="id" label="配置值-id" width="180"  header-align="center"></el-table-column>
                      <el-table-column prop="name" label="配置值-名称" width="180"  header-align="center"></el-table-column>
                      <el-table-column prop="val" label="配置值-val" width="180"  header-align="center"></el-table-column>
                      <el-table-column prop="val1" label="配置值-va11" width="180"  header-align="center"></el-table-column>
                      <el-table-column prop="val2" label="配置值-val2" width="180"  header-align="center"></el-table-column>
                      <el-table-column prop="listImportPkg" label="引入的pkg(#分割)" width="180"  header-align="center"></el-table-column>
                      
                      <el-table-column prop="jdbcUrl" label="操作" width="220"  header-align="center">
                        <template slot-scope="scope">
                          <el-button @click="delConfigKeyVal(scope.row.id)" type="text" size="small">删除</el-button>
                          <el-button @click="showConfigKeyValSaveUpdateFun(scope.row,scope.row.config_id)" type="text" size="small">编辑</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
              </template>
            </el-table-column>

            <el-table-column width="160"  label="配置ID" prop="config_id"  header-align="center" ></el-table-column>
            <el-table-column width="260" label="配置名称" prop="config_name"  header-align="center" ></el-table-column>
            <el-table-column width="330" label="类型【多选:check  单选:radio】" prop="input_type"  header-align="center" ></el-table-column>
            <el-table-column width="220" label="唯一编码" prop="code"  header-align="center"  ></el-table-column>

            <el-table-column  width="380" label="操作"   header-align="center" >
              <template slot-scope="scope">
                <el-button @click="delConfig(scope.row.config_id)" type="text" size="small">删除</el-button>
                <el-button @click="showConfigSaveUpdateFun(scope.row)" type="text" size="small">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
    </el-main>

     
    </el-dialog>


          <el-dialog title="添加/编辑【配置】" :visible.sync="addOrEditConfigFlag">
              <el-form ref="form" :model="configData" label-width="80px">
                <el-form-item label="配置ID">
                    <el-input v-model="configData.config_id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="配置名称">
                    <el-input v-model="configData.config_name"></el-input>
                </el-form-item>
                <el-form-item label="类型">
                    <div style="margin-top: 20px">
                      <el-radio v-model="configData.input_type" label="radio" border size="medium">单选</el-radio>
                      <el-radio v-model="configData.input_type" label="check" border size="medium">多选</el-radio>
                    </div>
                </el-form-item>
                <el-form-item label="唯一编码">
                    <el-input v-model="configData.code"></el-input>
                </el-form-item>
                <el-button type="primary" @click="configSaveUpdateFun">立即【创建/修改】</el-button>
              </el-form>
          </el-dialog>

          <el-dialog title="添加/编辑【配置值】" :visible.sync="addOrEditConfigKeyValFlag">
              <el-form ref="form" :model="configKeyValData" label-width="120px">
                <el-form-item label="配置值ID">
                    <el-input v-model="configKeyValData.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="配置值-名称">
                    <el-input v-model="configKeyValData.name"></el-input>
                </el-form-item>
                <el-form-item label="配置值-val">
                    <el-input v-model="configKeyValData.val"></el-input>
                </el-form-item>
                <el-form-item label="配置值-val1">
                    <el-input v-model="configKeyValData.val1"></el-input>
                </el-form-item>
                <el-form-item label="配置值-val2">
                    <el-input v-model="configKeyValData.val2"></el-input>
                </el-form-item>
                <el-form-item label="引入的pkg(#分割)">
                    <el-input v-model="configKeyValData.listImportPkg"></el-input>
                </el-form-item>
                <el-button type="primary" @click="configKeyValSaveUpdateFun">立即【创建/修改】</el-button>
              </el-form>
          </el-dialog>


          <el-dialog title="模板数据的json字符串" :visible.sync="dataJsonStrVisible" :fullscreen="true">
            
            <textarea name="" id="" cols="220" rows="50" v-model="dataJsonStr"></textarea>
          </el-dialog>
  </el-container>



  
</template>

<script>
export default {
  name: "tableInfo",
  created: function() {
    this.projectId = this.$route.query.id;
    this.getList();
    this.getConfigList();
    // `this` 指向 vm 实例
  },
  data() {
    return {
      addOrEditConfigFlag: false,
      addOrEditConfigKeyValFlag: false,
      //tmpData: null,
      //tmpDataList: [],

      projectId: null,
      tableName: "",
      generateCodeVisible: false,
      tableDataList: [],
      templateList: [],
      tableData: {},
      loading: true,
      startGenerateCodeTableName: null,
      templateVisible: false,
      //config
      configVisible: false,
      configList: [],
      configData: {},
      configKeyValData: {},
      configIdData: "",
      dataJsonStr: null,
      dataJsonStrVisible: false
    };
  },
  methods: {
    // addOrEditConfigFlag
    showConfigSaveUpdateFun: function(configDataTmp) {
      this.configData = {};
      if (configDataTmp != null) {
        this.configData = configDataTmp;
      }
      this.addOrEditConfigFlag = true;
    },
    showConfigKeyValSaveUpdateFun: function(
      configKeyValDataTmp,
      configIdDataTmp
    ) {
      console.log(configKeyValDataTmp);
      console.log("------");
      console.log(configIdDataTmp);
      this.configKeyValData = {};
      if (configKeyValDataTmp != null) {
        this.configKeyValData = configKeyValDataTmp;
      }
      this.configIdData = configIdDataTmp;
      this.addOrEditConfigKeyValFlag = true;
    },
    configSaveUpdateFun: function() {
      const qs = require("qs");
      var thisVar = this;

      let data = {
        configId: this.configData.config_id,
        configName: this.configData.config_name,
        inputType: this.configData.input_type,
        code: this.configData.code,
        projectId: this.projectId
      };

      this.$http
        .post("/config/saveUpdate", qs.stringify(data))
        .then(function(response) {
          if (response.data.ok) {
            thisVar.$message.success("添加或者修改成功");
            thisVar.addOrEditConfigFlag = false;
            thisVar.getConfigList();
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
    configKeyValSaveUpdateFun: function() {
      const qs = require("qs");
      var thisVar = this;
      let data = {
        configId: this.configIdData,
        id: this.configKeyValData.id,
        name: this.configKeyValData.name,
        val: this.configKeyValData.val,
        val1: this.configKeyValData.val1,
        val2: this.configKeyValData.val2,
        listImportPkg:this.configKeyValData.listImportPkg
      };

      this.$http
        .post("/config/configKeyValSaveUpdate", qs.stringify(data))
        .then(function(response) {
          if (response.data.ok) {
            thisVar.$message.success("添加或者修改成功");
            thisVar.addOrEditConfigKeyValFlag = false;
            thisVar.getConfigList();
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
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
        templateId: templateInfo.template_id,
        columnsListStr: JSON.stringify(templateInfo.columnsList)
      };
      let templateName = templateInfo.name;
      console.log(templateInfo.columnsList);

      console.log(JSON.stringify(templateInfo.columnsList));
      this.$http
        .post("/template/generateCode", qs.stringify(data))
        .then(function(response) {
          thisVar.loading = false;
          if (response.data.ok) {
            thisVar.$message.success("创建【" + templateName + "】成功");
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
    generateCodeJsonFun: function(templateInfo) {
      var thisVar = this;
      const qs = require("qs");

      let data = {
        projectId: thisVar.projectId,
        tableName: thisVar.startGenerateCodeTableName,
        templateId: templateInfo.template_id,
        columnsListStr: JSON.stringify(templateInfo.columnsList)
      };
      let templateName = templateInfo.name;

      thisVar.dataJsonStr = null;
      this.$http
        .post("/template/generateCodeJson", qs.stringify(data))
        .then(function(response) {
          thisVar.loading = false;
          if (response.data.ok) {
            thisVar.dataJsonStrVisible = true;
            thisVar.dataJsonStr = response.data.data;
            //thisVar.dataJsonStr = thisVar.formatJson(response.data.data)
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
        projectId: thisVar.projectId,
        tableName: thisVar.startGenerateCodeTableName
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
    getConfigList: function() {
      var thisVar = this;
      this.loading = true;
      const qs = require("qs");
      var thisVar = this;
      let data = {
        projectId: thisVar.projectId
      };
      thisVar.configList = null;
      this.$http
        .post("/config/list", qs.stringify(data))
        .then(function(response) {
          thisVar.loading = false;
          if (response.data.ok) {
            thisVar.configList = response.data.data;
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
      this.getConfigList();
      this.getTemplateListFun();
    },
    showConfigVisible: function() {
      this.configVisible = true;
      this.getConfigList();
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

          path: data.path,
          configIdList: data.configIdList.join()
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
    },
    delTempaleFun: function(data) {
      const qs = require("qs");
      var thisVar = this;

      let dataTmp = {
        project_id: thisVar.projectId
      };
      if (data != null) {
        dataTmp = {
          templateId: data.template_id
        };
      }
      this.$http
        .post("/template/del", qs.stringify(dataTmp))
        .then(function(response) {
          if (response.data.ok) {
            thisVar.$message.success("删除成功");
            thisVar.getTemplateListFun();
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
    delConfig: function(idTmp) {
      const qs = require("qs");
      var thisVar = this;

      let dataTmp = {
        id: idTmp
      };
      this.$http
        .post("/config/delConfig", qs.stringify(dataTmp))
        .then(function(response) {
          if (response.data.ok) {
            thisVar.$message.success("删除成功");
            thisVar.getConfigList();
          } else {
            thisVar.$message.error(response.data.msg);
          }
        })
        .catch(function(error) {
          console.log(error);
          thisVar.$message.error("网络错误");
        });
    },
    delConfigKeyVal: function(idTmp) {
      const qs = require("qs");
      var thisVar = this;

      let dataTmp = {
        id: idTmp
      };
      this.$http
        .post("/config/delConfigKeyVal", qs.stringify(dataTmp))
        .then(function(response) {
          if (response.data.ok) {
            thisVar.$message.success("删除成功");
            thisVar.getConfigList();
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