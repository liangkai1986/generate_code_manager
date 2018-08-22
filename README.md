# generate_code_manager

#### 项目介绍

为什么开源这个系统？

公司有个新项目要做，这个项目属于前后端分离的 所以我们需要提供接口文档给前端。
没有API文档工具之前，大家都是手写API文档的，在什么地方书写的都有，我们公司是写word文档的，每个公司都有每个公司的玩法，无所谓好坏。
书写API文档的工具有很多，但是能称之为“框架”的，估计也只有swagger了，但是使用swagger框架比较麻烦的就是注解写的比较多
【对swagger不了解的可以参考下 https://blog.csdn.net/i6448038/article/details/77622977 网上看到的】
例如下图：

![实体类](https://images.gitee.com/uploads/images/2018/0822/112702_23817e62_535567.png "实体类")
![controller](https://images.gitee.com/uploads/images/2018/0822/112803_214ff158_535567.png "controller")

自己手写注解的话工作量有点大，我的内心是拒绝的 :grin: ，然后跟我们总监聊了下这个事，说实体类这块可以考虑代码生成器减轻重复性工作，姜还是老的辣哈，最后就看mybatis-plus的代码生成器的代码 代码生成用的velocity引擎。

1、把对应的模板拷到自己的项目下面（实体类的模板加上swagger框架的注解）
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/135624_be3f8d14_535567.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/140225_328806a1_535567.png "屏幕截图.png")

2、因为mybatis-plus代码生成器是new AutoGenerator()
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/135750_f4b0bf73_535567.png "屏幕截图.png")
3、所以我只要继承AutoGenerator再重写getVelocityEngine方法即可
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/135912_d5b88986_535567.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/140017_596ddb67_535567.png "屏幕截图.png")



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
后台：jfinal+freemarker
前台：vue+elementUi

为什么这个代码生成器 我前后端分离而不是整合成一个项目了？
因为现在基本上都是前后端分离了，加上我也想了解学习下vue感受下它的魅力。


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



