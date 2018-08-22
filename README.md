# generate_code_manager

#### 项目介绍
起因：
公司有个新项目要做，这个项目属于前后端分离的 所以我们需要提供接口文档给前端。
没有API文档工具之前，大家都是手写API文档的，在什么地方书写的都有，我们公司是写word文档的，每个公司都有每个公司的玩法，无所谓好坏。
书写API文档的工具有很多，但是能称之为“框架”的，估计也只有swagger了，但是使用swagger框架有点不好的就是注解写的比较多有点费劲
例如下图：
![实体类](https://images.gitee.com/uploads/images/2018/0822/112702_23817e62_535567.png "屏幕截图.png")
![controller](https://images.gitee.com/uploads/images/2018/0822/112803_214ff158_535567.png "屏幕截图.png")

【对swagger不了解的可以参考下 https://blog.csdn.net/i6448038/article/details/77622977 网上看到的】


之前代码生成器是用mybatis-plus的

这是一个可配置的代码生成器（只支持mysql）
通过查询表字段和页面上的设置和自己实现的模板  动态生成对应的文件

生成的java文件 代码有些凌乱需要手动的 alt+shift+s+f（格式化代码） & ctrl+shift+o(把多引入的包去掉)

功能有：
项目管理
数据库连接管理
模板管理
配置管理
代码生成


#### 软件架构
vue+jfinal+freemarker


#### 安装教程
前台：
因为前台采用的是vue 所以需要先安装node.js
https://nodejs.org/en/  (node.js官网地址)
1. 安装完成后  cmd 进入![输入图片说明](https://images.gitee.com/uploads/images/2018/0821/101326_ea2c4bc8_535567.png "屏幕截图.png") 这个 generate_code_manager_vue文件夹下面  执行npm install （下载vue依赖的组件）
2. 在执行 npm run dev (启动vue项目)
后台：
1.eclipse导入generate_code_manager_java
2.tomcat端口设置成8083【如果不用这个端口 可以改 generate_code_manager\generate_code_manager_vue\src\main.js 下面的axios.defaults.baseURL 这个配置即可】

#### 使用说明

1. xxxx
2. xxxx
3. xxxx



