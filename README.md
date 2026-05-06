# SpringBoot + Uniapp 前后端分离项目

## 📖 项目简介

这是一个基于 **SpringBoot + Uniapp** 的前后端分离实战项目，实现了完整的用户认证体系和文章管理功能。项目解决了 SpringBoot 循环依赖、JUnit 版本冲突、Uniapp 请求头携带 Token 等常见问题，架构清晰、可扩展性强，适合作为学习和二次开发的基础模板。

### ✅ 技术特性

| 分类 | 技术栈 | 版本 |
| :--- | :--- | :--- |
| 后端框架 | SpringBoot | 4.0.x |
| 数据访问 | MyBatis | 3.0+ |
| 身份认证 | JWT | - |
| 密码加密 | MD5 | - |
| 测试框架 | JUnit 5 | SpringBoot 自带 |
| 数据库 | MySQL | 8.0+ |
| 前端框架 | Uniapp | Vue3 + Setup |

### ✨ 核心功能

- **用户模块**
  - 用户注册（用户名唯一性校验、密码 MD5 加密）
  - 用户登录（账号密码校验、JWT Token 生成）
  - 鉴权拦截（接口请求需携带 Authorization 请求头）

- **文章模块**
  - 文章列表查询（需携带 Token 访问）

- **通用功能**
  - 统一响应格式（Result 工具类）
  - 异常处理（请求异常、Token 无效/缺失友好提示）

---

## 🚀 快速开始

### 环境要求

- Java 17+
- MySQL 8.0+
- Maven 3.6+
- HBuilderX（运行 Uniapp 前端）
- Node.js（Uniapp 依赖管理）

### 后端部署

1. **克隆项目**
   ```bash
   git clone https://github.com/wendao2004/news.git
   cd news
   ```

2. **配置数据库**
   修改 `src/main/resources/application.yml` 中的数据库连接信息：
   ```yaml
   spring:
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/news_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
       username: root
       password: your_password
   ```

3. **启动服务**
   - 使用 IDE 运行 `NewsApplication.java`
   - 或使用 Maven 命令：
     ```bash
     mvn spring-boot:run
     ```
   - 服务默认运行在 `http://localhost:8888`

### 前端部署

1. **打开项目**
   使用 HBuilderX 打开 `SpringBoot-TestDemo` 目录

2. **配置接口地址**
   前端默认已配置为 `http://localhost:8888`，如需修改请编辑相关页面文件

3. **运行项目**
   在 HBuilderX 中选择运行方式（模拟器/真机/浏览器）

---

## 🔌 接口测试

### 登录接口

**POST** `http://localhost:8888/user/login`

请求体：
```json
{
  "username": "admin",
  "password": "123456"
}
```

成功响应：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 文章列表接口

**GET** `http://localhost:8888/articles/list`

请求头：
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

成功响应：
```json
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
```

---

## 📂 项目结构

### 后端结构

```
src/main/java/com/message/news/
├── controller          # REST API 控制层
│   ├── UserController.java
│   ├── ArticleController.java
│   └── CategoryController.java
├── service             # 业务逻辑层
│   ├── impl            # 服务实现类
│   ├── UserService.java
│   ├── ArticleService.java
│   └── CategoryService.java
├── mapper              # MyBatis 数据访问层
│   ├── UserMapper.java
│   ├── ArticleMapper.java
│   └── CategoryMapper.java
├── pojo                # 实体类与响应对象
│   ├── User.java
│   ├── Article.java
│   ├── Category.java
│   └── Result.java
├── login               # 登录策略模块（策略模式）
│   ├── strategy        # 登录策略接口与实现
│   └── handler         # 登录后处理器
├── interceptors        # 请求拦截器
│   └── LoginInterceptor.java
├── config              # 配置类
│   ├── CorsConfig.java
│   └── WebConfig.java
├── utils               # 工具类
│   ├── JwtUtil.java
│   ├── Md5Util.java
│   └── ThreadLocalUtil.java
└── NewsApplication.java # 启动类
```

### 前端结构

```
SpringBoot-TestDemo/
├── pages/
│   ├── login/          # 登录页面
│   ├── register/       # 注册页面
│   ├── article/        # 文章列表页面
│   ├── ariticle/       # 文章详情页面
│   └── me/             # 个人中心页面
├── common/
│   ├── tabbar/         # TabBar 组件
│   └── utils/          # 工具函数
├── static/             # 静态资源
├── App.vue             # 根组件
├── main.js             # 入口文件
└── pages.json          # 页面配置
```

---

## ⚠️ 常见问题

| 问题 | 解决方案 |
| :--- | :--- |
| 循环依赖报错 | `LoginStrategy` 注入 `UserMapper` 而非 `UserService` |
| JUnit 测试无输出 | 删除手动添加的 JUnit 依赖，使用 SpringBoot 自带的 `spring-boot-starter-test` |
| `TypeError: (intermediate value) is not iterable` | `uni.request` 不支持数组解构，改用 `const res = await uni.request(...)` |
| `Required request header 'Authorization' is not present` | 前端请求时将 Token 放在 `header` 中，而非 `data` 中 |
| Token 为 null | 后端登录接口成功后返回 Token，前端接收后存储到本地 |

---

## 📈 扩展方向

- **后端**：添加全局异常处理器、接口权限拦截器、Token 刷新机制
- **前端**：完善登录页面、个人中心、文章详情页，对接 uView UI 库
- **功能**：文章 CRUD、用户密码修改、分页查询、分类管理

---

## 📞 联系方式

- GitHub：[https://github.com/wendao2004](https://github.com/wendao2004)

---

**License**: MIT