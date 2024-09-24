# publicOpinionPlatform



## 舆情分析系统

根据用户的各种表数据，实现舆情分析的相关功能，其主要功能包括

+ 社交网络中基本信息的展示
+ 社交网络中用户基本信息的查询
+ 社交网络中用户发布贴文信息的查询
+ 社交账号中额外特征的Echarts展示
+ 社交事件排行榜
+ 包含首页、用户页、事件页、敌意分析页、工具类（目前仅工具类用到部分后端、其余部分用假数据替代）





## 项目的文件夹功能介绍

该项目为前后端分离项目，前端采用Vue2 + ElementUI + Echarts 实现， 后端采用Springboot + Mybatis 框架， 数据库存储是Mysql8.0

+  frontend: 前端项目代码
+ backend: 后端项目代码
+ database: 示例数据库文件
+ python_api: 为项目调用的一些python_api接口  实现部分机器学习、深度学习功能



## 数据库表设计

各个数据库表的内容：待补充







## 前端

打开fontend文件夹，先下载所有依赖。

`npm run install`

进一步，我们需要运行前端代码，展示页面，前端端口号为：9528

`npm run dev`

demo中核心文件夹： router（管理所有路由），view（存放所有视图）、asserts（存放所有静态文件）、Layerout&Components（存放公共导航栏以及基本样式）



## 后端

在Idea中打开backend文件夹，打开POM文件，用Maven导入所有依赖

找到主程序入口，运行（注意修改本地Maven仓库地址、JDK版本以及Mysql数据库名称，用户名和账号）

直接点击运行即可









