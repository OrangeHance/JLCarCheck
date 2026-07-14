# WMS MVP 项目文档

## 项目介绍

本项目是一个**仓储管理系统（WMS）的MVP版本**，
核心目标是实现商品的入库、出库管理功能，提供基础的仓库数据管理能力。系统采用前后端分离架构，支持用户登录认证、菜单导航、商品入库登记、商品出库登记等核心业务功能。

---

## 技术选型说明

### 前端技术栈

| 技术 | 版本 | 选型理由 |
| :--- | :--- | :--- |
| Vue | 3.5.x | 渐进式JavaScript框架，响应式数据绑定，组件化开发，生态成熟 |
| TypeScript | 6.0.x | 强类型语言，提供更好的代码提示和类型安全，降低维护成本 |
| Vite | 8.0.x | 新一代前端构建工具，启动速度快，热更新效率高 |
| Element Plus | 2.14.x | 基于Vue 3的企业级UI组件库，提供丰富的组件，开箱即用 |
| Vue Router | 4.6.x | Vue官方路由管理器，支持嵌套路由、导航守卫 |
| Vuex | 4.1.x | Vue官方状态管理库，集中管理应用状态 |
| Axios | 1.16.x | 轻量级HTTP客户端，支持Promise API |

### 后端技术栈

| 技术 | 版本 | 选型理由 |
| :--- | :--- | :--- |
| Spring Boot | 3.2.0 | 社区成熟，生态完善，便于快速构建RESTful服务 |
| Java | 17 | LTS版本，性能稳定，支持最新语言特性 |
| MyBatis | 3.5.x | 优秀的ORM框架，SQL与代码解耦，灵活可控 |
| MyBatis Plus | 3.5.5 | 增强MyBatis功能，提供便捷的CRUD操作 |
| JWT | 0.12.3 | 无状态认证方案，支持分布式部署 |
| BCrypt | 0.4 | 安全的密码加密算法，防止彩虹表攻击 |

### 数据库

| 技术 | 版本 | 选型理由 |
| :--- | :--- | :--- |
| MySQL | 8.0+ | 开源关系型数据库，性能稳定，社区活跃，适合中小型项目 |

---

## 环境准备

### 后端环境要求

- **JDK**: 17 或更高版本
- **Maven**: 3.6+
- **MySQL**: 8.0+

### 前端环境要求

- **Node.js**: 18+
- **npm**: 9+

---

## 启动步骤

### 1. 数据库准备

1. 登录MySQL数据库，创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS `sys` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `sys`;
```

2. 导入数据库脚本：
```bash
# 方式一：使用mysql命令行
mysql -u root -p sys < f:\MVP\fullstack-interview\database\DDL.txt

# 方式二：使用数据库管理工具（如Navicat、DBeaver）
# 依次执行 database 目录下的以下文件：
# - DDL.txt（创建表结构）
# - user.sql（导入用户数据）
# - menu.sql（导入菜单数据）
```

### 2. 后端服务启动

1. 进入后端目录：
```bash
cd f:\MVP\fullstack-interview\backend
```

2. 修改数据库配置（可选）：
编辑 `src/main/resources/application.yml`，确认数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sys?allowPublicKeyRetrieval=true&useSSL=false
    username: root
    password: 123456
```

3. 使用Maven运行：
```bash
mvn spring-boot:run
```

4. 验证启动成功：
访问 `http://localhost:8080/user/info` 应返回登录相关错误（未携带Token），表示服务启动正常。

### 3. 前端服务启动

1. 进入前端目录：
```bash
cd f:\MVP\fullstack-interview\frontend\vue
```

2. 安装依赖：
```bash
npm install
```

3. 启动开发服务器：
```bash
npm run dev
```

4. 访问应用：
打开浏览器访问 `http://localhost:9060`，即可进入登录页面。

---

## 测试账号

| 用户名 | 密码     | 状态 |
| :--- |:-------| :--- |
| admin | 123456 | 正常 |
| admin123 | 123456 | 正常 |

---

## 功能说明

### 1. 用户登录模块

**功能描述**：用户认证登录，获取JWT Token

**操作流程**：
1. 访问首页，进入登录页面
2. 输入用户名和密码
3. 点击登录按钮
4. 登录成功后跳转到主页面

### 2. 菜单管理模块

**功能描述**：获取系统菜单列表，用于左侧导航

### 3. 商品入库模块

**功能描述**：管理商品入库信息

**操作流程**：
1. 点击左侧菜单"商品入库"
2. 点击"新增"按钮
3. 填写商品编码、规格、数量、入库日期等信息
4. 点击"确定"保存

### 4. 商品出库模块

**功能描述**：管理商品出库信息

**操作流程**：
1. 点击左侧菜单"商品出库"
2. 点击"新增"按钮
3. 填写商品编码、出库数量、出库日期等信息
4. 点击"确定"保存

### 5. 仓库管理模块

**功能描述**：查看仓库库存信息，支持入库和出库操作

---

## 问题与解决

### 问题1：跨域请求被阻止

**问题描述**：前端访问后端API时出现CORS跨域错误

**解决思路**：在后端配置CorsConfig，允许前端域名的跨域请求，配置允许的请求方法、请求头和凭证。

### 问题2：JWT Token认证失败

**问题描述**：前端携带Token访问接口时认证失败

**解决思路**：检查JWT的签名密钥是否一致，确保Token在请求头中正确携带（格式：`Bearer {token}`），检查拦截器配置是否正确排除登录接口。

### 问题3：密码加密不一致

**问题描述**：用户登录时密码比对失败

**解决思路**：使用BCrypt算法进行密码加密和比对，确保注册和登录时使用相同的加密强度。BCrypt自带salt机制，每次加密结果不同但比对时能正确匹配。

### 问题4：日期格式转换异常

**问题描述**：前端传递的日期格式与后端期望格式不一致

**解决思路**：统一日期格式为`yyyy-MM-dd`，后端使用`@DateTimeFormat`注解进行格式化，前端使用Element Plus的日期选择器确保格式正确。

---

## 项目结构

```
fullstack-interview/
├── backend/                    # 后端服务
│   ├── src/main/java/com/mvp/
│   │   ├── controller/         # 控制层
│   │   ├── service/            # 服务层
│   │   ├── mapper/             # 数据访问层
│   │   ├── entity/             # 实体类
│   │   ├── dto/                # 数据传输对象
│   │   ├── config/             # 配置类
│   │   ├── interceptor/        # 拦截器
│   │   └── util/               # 工具类
│   └── src/main/resources/
│       ├── mapper/             # MyBatis映射文件
│       └── application.yml     # 应用配置
├── frontend/vue/               # 前端应用
│   ├── src/
│   │   ├── views/              # 页面组件
│   │   ├── components/         # 公共组件
│   │   ├── api/                # API接口
│   │   ├── router/             # 路由配置
│   │   ├── store/              # 状态管理
│   │   └── utils/              # 工具函数
│   └── package.json            # 依赖配置
└── database/                   # 数据库脚本
    ├── DDL.txt                 # 表结构定义
    ├── user.sql                # 用户数据
    ├── menu.sql                # 菜单数据
    └── warehouse*.sql          # 仓库相关数据
```
使用AI范围：后端springBoot搭建框架，前端首页生成
单元测试

测试类	测试数量	状态
MenuServiceImplTest	2	 通过
UserServiceImplTest	7	 通过
WareHouseInServiceImplTest	6	 通过
总计：15 个测试，0 失败，0 错误，0 跳过

测试覆盖的功能点：

UserServiceImplTest: 登录验证（用户不存在、密码错误、用户禁用）、获取用户信息（有效token、无效token）
MenuServiceImplTest: 菜单列表查询（有数据、空数据）
WareHouseInServiceImplTest: 仓库搜索、商品保存（新商品、已存在商品）、商品更新、出库操作（库存充足、库存不足、参数校验）