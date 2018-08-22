# generate_code_manager

#### 项目介绍

### 我为什么想开源一个代码生成器系统？


现在的项目基本上属于前后端分离的 所以我们需要提供接口文档给前端。
没有API文档工具之前，大家都是手写API文档的，在什么地方书写的都有，我们公司是写word文档的，每个公司都有每个公司的玩法，无所谓好坏。
书写API文档的工具有很多，但是能称之为“框架”的，估计也只有swagger了。
【对swagger不了解的可以参考下 https://blog.csdn.net/i6448038/article/details/77622977 网上看到的】
但是使用swagger框架比较麻烦的就是注解写的比较多 例如下图：

![实体类](https://images.gitee.com/uploads/images/2018/0822/112702_23817e62_535567.png "实体类")
![controller](https://images.gitee.com/uploads/images/2018/0822/112803_214ff158_535567.png "controller")


自己手写注解的话工作量有点大，我的内心是有点拒绝的 :grin: ，然后跟我们总监聊了下这个事，说实体类这块可以考虑代码生成器减轻重复性工作，姜还是老的辣哈。



最后就看mybatis-plus的代码生成器的源码，发现代码生成用的velocity引擎，并且使用的话是new AutoGenerator()
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/135750_f4b0bf73_535567.png "屏幕截图.png")
所以我只要继承AutoGenerator再重写getVelocityEngine方法即可
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/135912_d5b88986_535567.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0822/140017_596ddb67_535567.png "屏幕截图.png")

最后就形成了我们公司现在用的代码生成器 能生成单表的增删改查，并且实体类和controller都加上swagger注解

### 但是开发途中也发现了很多可以改进的地方
 
例如：包括但不限于

有的字段需要加正则校验（手机号、邮箱、日期）

有的字段需要判断唯一性 

有的字段需要必填

。。。


这些都是我们手工需要增加的代码！！！

虽说工作量不大但是挺繁琐的 所以就萌生了开源一个代码生成器，既造福了自己也方便大家


generate_code_manager：
这是一个可配置的代码生成器（只支持mysql）

通过查询表字段和页面上的设置和自己实现的模板  动态生成对应的文件

这个generate_code_manager系统目前经过本人的 https://gitee.com/1945605733/authManager【一个 权限管理的微服务】实战
大家可以放心使用不用担心有太多bug，有问题可以再群里问我，看到消息有时间我会及时回复的


### 生成的java文件 代码有些凌乱需要手动的 

alt+shift+s+f（格式化代码） & ctrl+shift+o(把多引入的包去掉)


### 功能有：


项目管理

数据库连接管理

模板管理

配置管理

代码生成



#### 软件架构
后台：jfinal+freemarker

前台：vue+elementUi


为什么这个代码生成器 我前后端分离而不是整合成一个项目了？

现在基本上系统都是前后端分离了，加上我也想搞搞vue~


#### 安装教程
前台：
因为前台采用的是vue 所以需要先安装node.js
https://nodejs.org/en/  (node.js官网地址)

1. 安装完成后  cmd 进入![输入图片说明](https://images.gitee.com/uploads/images/2018/0821/101326_ea2c4bc8_535567.png "屏幕截图.png") 这个 generate_code_manager_vue文件夹下面  执行npm install （下载vue依赖的组件）

2. 在执行 npm run dev (启动vue项目)


后台：
1.eclipse导入generate_code_manager_java

2.tomcat端口设置成8083【如果不用这个端口 可以改 generate_code_manager\generate_code_manager_vue\src\main.js 下面的axios.defaults.baseURL 这个配置即可】



数据库位置：

有什么问题可以加群<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=97f1b0f6f636df40cceadad80a177d0439746b6d8f1ecf8ff1d428639ae61ba3"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="generate_code_manager群" title="generate_code_manager群">点我</a>


#### 使用说明

1. xxxx
2. xxxx
3. xxxx



