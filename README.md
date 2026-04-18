# SpringBoot+Uniapp 前后端分离项目

# 📖 项目介绍
  这是一个基于 SpringBoot + Uniapp 的前后端分离实战项目，核心实现 用户注册、JWT登录鉴权、文章列表查询 功能，解决了SpringBoot循环依赖、JUnit版本冲突、Uniapp请求头携带Token等常见踩坑点，架构清晰、可扩展性强，适合入门学习和二次开发。

✅ 后端：SpringBoot + MyBatis + JWT + MD5加密  
✅ 前端：Uniapp（Vue3 + Setup语法）  
✅ 核心：无循环依赖、统一响应格式、JWT鉴权、前后端联调无报错

# 🚀 技术栈

后端技术栈
- 核心框架：SpringBoot 4.0.x

- 数据访问：MyBatis

- 鉴权方式：JWT（JSON Web Token）

- 加密方式：MD5

- 测试框架：JUnit 5（SpringBoot自带，无版本冲突）

- 数据库：MySQL 8.0+

前端技术栈

- 框架：Uniapp（Vue3 + Setup语法）

- 请求工具：uni.request

- 本地存储：uni.setStorageSync/uni.getStorageSync

- UI：Uniapp原生组件（可无缝对接uView等UI库）

# 📌 核心功能

1. 用户模块
        
  - 用户注册：用户名校验、密码MD5加密入库

  - 用户登录：账号密码校验、JWT Token生成与返回

  - 鉴权拦截：接口请求需携带Token（Authorization请求头）

2. 文章模块

  - 文章列表查询：携带Token请求，返回列表数据并渲染

3. 通用功能


  - 统一响应格式（Result工具类）：成功/失败统一返回，前端易处理

  - 异常处理：请求异常、Token无效/缺失友好提示

# ⚙️ 快速开始

1. 环境准备

  - Java 17+

  - MySQL 8.0+

  - Maven 3.6+

  - HBuilderX（运行Uniapp前端）

  - Postman（接口测试，可选）

2. 后端部署

  1. 克隆项目到本地
          git clone https://github.com/wendao2004/news.git

  2. 打开后端项目（SpringBoot），修改 application.yml 中的数据库配置
          spring:
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/对应数据表名字?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
      username: root  # 你的数据库用户名
      password: 123456  # 你的数据库密码

  3. 刷新Maven依赖，确保无缺失（核心依赖已配置，无需额外添加）

  4. 启动SpringBoot主启动类，后端服务默认运行在 http://localhost:8080

3. 前端部署

  1. 用HBuilderX打开前端项目（uniapp目录）

  2. 修改 pages/article/article.vue 中的接口地址（确保与后端地址一致）
          // 无需修改，默认已配置为 http://localhost:8080
  url: 'http://localhost:8080/articles/list'

  3. 选择运行方式（模拟器/真机/浏览器），启动前端项目

  4. 接口测试

  登录接口（获取Token）

  POST http://localhost:8080/user/login
  请求体（JSON）:
  {
    "username": "admin",
    "password": "123456"
  }

  响应成功（返回Token）:
  {
    "code": 200,
    "message": "操作成功",
    "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }

  文章列表接口（需携带Token）

  GET http://localhost:8080/articles/list
  请求头:
  Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...（登录返回的Token）

  响应成功:
  {
    "code": 200,
    "message": "操作成功",
    "data": [
    {
      "id": 1,
      "title": "测试文章1",
      "content": "这是一篇测试文章..."
    }
    ]
  }

📂 项目结构

后端结构（SpringBoot）

com.message.news
├── controller          # 接口层（用户/文章接口）
│   └── UserController.java、ArticleController.java
├── service             # 业务逻辑层
│   ├── impl            # 实现类
│   └── UserService.java、ArticleService.java
├── mapper              # 数据访问层（MyBatis）
│   └── UserMapper.java、ArticleMapper.java
├── pojo                # 实体类
│   ├── User.java、Article.java、Result.java
├── login               # JWT登录扩展（策略模式）
│   ├── strategy        # 登录策略（密码登录）
│   └── handler         # 登录后处理器（Token生成）
├── utils               # 工具类
│   ├── JwtUtil.java、Md5Util.java
└── NewsApplication.java # 主启动类

前端结构（Uniapp）

uniapp-project
├── pages
│   ├── login           # 登录页面
|   ├── register        # 注册页面
│   └── article         # 文章列表页面（article.vue）
|    
├── static              # 静态资源
├── App.vue             # 根组件
└── main.js             # 入口文件

❌ 常见问题（避坑指南）

1. 后端启动报错：循环依赖解决方案：LoginStrategy 注入 UserMapper 而非 UserService，彻底打破循环依赖（代码已修复）

2. JUnit测试无输出/报错：版本冲突解决方案：删除手动添加的JUnit依赖，只保留 SpringBoot 自带的 spring-boot-starter-test（代码已修复）

3. 前端请求异常：TypeError: (intermediate value) is not iterable解决方案：uni.request 不支持 [error, res] 数组解构，改用 const res = await uni.request(...)（代码已修复）

4. 后端报错：Required request header 'Authorization' is not present解决方案：前端请求时，将Token放在 header 中，而非 data 中（代码已修复）

5. 前端拿不到Token/Token为null解决方案：后端登录接口成功后返回Token，前端接收后存储到本地，请求时从本地获取（代码已修复）

📈 扩展方向

- 后端：添加全局异常处理器、接口权限拦截器、Token刷新机制

- 前端：添加登录页面、个人中心、文章详情页，对接uView UI库优化样式

- 功能扩展：文章新增/修改/删除、用户密码修改、分页查询等

🙏 致谢

感谢 SpringBoot、Uniapp 官方文档支持，感谢所有帮助过项目开发的小伙伴！

📞 联系方式

GitHub：(https://github.com/wendao2004)

