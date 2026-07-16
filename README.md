# JLCarCheck

吉利汽车检测系统 - 用于汽车生产过程中的质量检查管理

## 项目简介

JLCarCheck 是一个基于 Spring Boot + Vue 3 构建的汽车质量检测系统，主要用于管理汽车生产过程中的各个岗位检查项和检查结果。

## 技术栈

### 后端技术
| 技术 | 版本 | 说明 |
| --- | --- | --- |
| Spring Boot | 3.2.0 | 后端框架 |
| MyBatis | 3.5.14 | ORM框架 |
| MyBatis-Plus | 3.5.5 | MyBatis增强工具 |
| MySQL | 8.0+ | 数据库 |
| JWT | 0.12.3 | 身份认证 |
| PageHelper | 1.4.7 | 分页插件 |
| Lombok | 1.18+ | 代码简化 |

### 前端技术
| 技术 | 版本 | 说明 |
| --- | --- | --- |
| Vue | 3.5.x | 前端框架 |
| TypeScript | 6.0.x | 类型脚本 |
| Element Plus | 2.14.x | UI组件库 |
| Vue Router | 4.6.x | 路由管理 |
| Vuex | 4.1.x | 状态管理 |
| Axios | 1.16.x | HTTP客户端 |
| Vite | 8.0.x | 构建工具 |

## 项目结构

```
JLCarCheck/
├── backend/                    # 后端代码
│   ├── src/main/java/com/mvp/
│   │   ├── controller/         # 控制器层
│   │   ├── service/            # 服务接口
│   │   ├── impl/               # 服务实现
│   │   ├── mapper/             # 数据访问层
│   │   ├── entity/             # 实体类
│   │   ├── dto/                # 数据传输对象
│   │   ├── config/             # 配置类
│   │   ├── interceptor/        # 拦截器
│   │   ├── exception/          # 异常处理
│   │   ├── util/               # 工具类
│   │   └── MvpApplication.java # 启动类
│   ├── src/main/resources/
│   │   ├── mapper/             # MyBatis映射文件
│   │   └── application*.yml    # 配置文件
│   └── pom.xml                 # Maven依赖
├── frontend/vue/               # 前端代码
│   ├── src/
│   │   ├── api/                # API接口
│   │   ├── components/         # 组件
│   │   ├── views/              # 页面视图
│   │   ├── router/             # 路由配置
│   │   ├── store/              # 状态管理
│   │   ├── types/              # 类型定义
│   │   └── utils/              # 工具函数
│   └── package.json            # npm依赖
├── database/                   # 数据库脚本
│   ├── DDL.txt                 # 建表语句
│   ├── DML.txt                 # 数据插入语句
│   └── *.sql                   # 各表单独脚本
└── README.md                   # 项目说明
```

## 功能模块

### 1. 用户管理
- 用户登录/登出
- JWT身份认证
- 用户信息管理

### 2. 菜单管理
- 动态菜单加载
- 岗位权限控制

### 3. 车辆信息管理
- 车辆基础信息维护
- MES系统数据同步
- 车辆历史记录

### 4. 检查项管理
- 检查项配置
- 检查项图片管理
- 岗位检查项关联

### 5. 质量检查
- 多岗位检查（A/B/C/D/E/F）
- 检查结果记录
- 检查历史查询

### 6. 仓库管理
- 入库管理
- 出库管理
- 库存查询

### 7. 定时任务
- MES系统车辆追踪数据定时拉取（当前已注释禁用，默认每30秒执行）

## 数据库表结构

| 表名 | 说明 |
| --- | --- |
| `user` | 用户表 |
| `menu` | 菜单表 |
| `carcheckitems` | 检测项表 |
| `carinfo` | 车辆信息表 |
| `checkinfo` | 检查配置表 |
| `checkresult` | 检查结果表 |
| `mescarinfo` | MES车辆信息表 |
| `mescarinfohis` | MES车辆信息历史表 |

## 环境要求

- **Java**: 17+
- **Node.js**: 18+
- **MySQL**: 8.0+

## 快速开始

### 1. 数据库准备

创建数据库并执行初始化脚本：

```sql
CREATE DATABASE IF NOT EXISTS `sys` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `sys`;
```

执行 [DDL.txt](database/DDL.txt) 创建表结构，执行 [DML.txt](database/DML.txt) 插入初始数据。

### 2. 后端启动

```bash
cd backend
mvn clean package
mvn spring-boot:run
```

服务将在 http://localhost:8080 启动。

### 3. 前端启动

```bash
cd frontend/vue
npm install
npm run dev
```

前端将在 http://localhost:5173 启动。

## 配置说明

### 后端配置

项目支持三种环境配置：

**开发环境** (`application-dev.yml`)：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sys?allowPublicKeyRetrieval=true&useSSL=false
    username: root
    password: 123456
```

**测试环境** (`application-qa.yml`)：
```yaml
spring:
  datasource:
    url: jdbc:mysql://qa-db:3306/mvp_qa?allowPublicKeyRetrieval=true&useSSL=true&serverTimezone=Asia/Shanghai
    username: qa_user
    password: qa_password
```

**生产环境** (`application-prod.yml`)：
```yaml
spring:
  datasource:
    url: jdbc:mysql://prod-db:3306/mvp_prod?allowPublicKeyRetrieval=false&useSSL=true&serverTimezone=Asia/Shanghai&verifyServerCertificate=true
    username: prod_user
    password: prod_password
```

切换环境：修改 `application.yml` 中的 `spring.profiles.active` 值为 `dev`、`qa` 或 `prod`。

### 前端配置

**开发环境** (`frontend/vue/.env.development`)：
```
VITE_API_URL=http://localhost:8080
```

**测试环境** (`frontend/vue/.env.test`)：
```
VITE_API_URL=https://test-api.xxx.com
```

**生产环境** (`frontend/vue/.env.production`)：
```
VITE_API_URL=https://api.xxx.com
```

## API接口

### 认证接口
| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `POST` | `/user/login` | 用户登录 |
| `GET` | `/user/info` | 获取当前用户信息 |

### 菜单接口
| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `GET` | `/menu/menuList` | 获取菜单列表 |

### 车辆信息接口
| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `GET` | `/carInfo/getCarByBarCode` | 根据岗位查询待检查车辆 |
| `GET` | `/carInfo/findCarInfoAndcCheck` | 根据岗位和VIN查询车辆信息及检查状况 |

### 检查项接口
| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `GET` | `/checkItem/items` | 根据岗位查询配置的检查项目 |

### 检查历史接口
| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `POST` | `/checkHis/submit` | 提交检查结果 |


## 访问说明

1. 打开浏览器访问 http://localhost:9060
2. 使用默认账号登录（具体密码请联系管理员）：
   - 用户名：`admin`
   - 密码：`admin123`
3. 根据岗位权限访问对应检查页面（A/B/C/D/E/F岗位）

## 开发说明

### 后端开发

```bash
# 运行测试
mvn test

# 打包
mvn clean package
```

### 前端开发

```bash
# 开发模式
npm run dev

# 测试环境构建
npm run build:test

# 生产环境构建
npm run build:prod

# 预览构建结果
npm run preview
```

## 许可证

MIT License