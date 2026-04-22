# DonDonQiang20_Server - Java 服务端

捕鱼达人游戏的后端服务器，基于 Java Servlet 提供 RESTful API。

## 技术栈

- **语言:** Java 1.8
- **框架:** Java Servlet 3.0（原生，无 Spring）
- **服务器:** Apache Tomcat 8.5+
- **数据库:** MySQL 8.0
- **IDE:** Eclipse
- **构建方式:** Eclipse 内置构建（无 Maven/Gradle）

## 项目结构

```
server/
├── src/com/dondonqiang2/server/
│   ├── controller/
│   │   ├── LoginServlet.java       # 用户登录接口
│   │   ├── RegisterServlet.java    # 用户注册接口
│   │   ├── RankServlet.java        # 分数提交 & 排名查询接口
│   │   └── ListViewServlet.java    # 排行榜接口（Top 10）
│   ├── dao/
│   │   └── UsersDao.java           # 数据访问对象（所有 SQL 操作）
│   └── util/
│       └── DBHelper.java           # 数据库连接工具类
└── WebContent/
    └── WEB-INF/lib/                # 依赖 JAR 包
        ├── mysql-connector-java-8.0.25.jar   # MySQL 驱动
        ├── fastjson-1.2.76.jar               # JSON 序列化
        ├── commons-dbutils-1.7.jar           # JDBC 工具库
        ├── jstl.jar                          # JSP 标签库
        └── standard.jar                      # JSTL 实现
```

## API 接口

所有接口同时支持 GET/POST，响应格式为 JSON 数组。

### 登录

```
GET/POST /DonDonQiang20_Server/LoginServlet
参数: user_Name
响应: [{"user_rank":1, "user_name":"Mike", "user_password":"111", "user_maxscore":5000}]
```

### 注册

```
GET/POST /DonDonQiang20_Server/RegisterServlet
参数: user_Name, user_Password
响应: [{"ifAdded":"yes"}]       -- 注册成功
      [{"ifAdded":"existed"}]   -- 用户名已存在
      [{"ifAdded":"isNull"}]    -- 用户名为空
```

### 提交分数 / 查询排名

```
GET/POST /DonDonQiang20_Server/RankServlet
参数: user_Name, user_Score
响应: [{"user_rank":3, "user_name":"Tom", "user_maxscore":1800}]
说明: 如果新分数高于历史最高分，会自动更新
```

### 排行榜

```
GET/POST /DonDonQiang20_Server/ListViewServlet
参数: 无
响应: 前 10 名玩家排名列表（按最高分降序）
```

## 部署步骤

1. 安装 MySQL 8.0，导入数据库（见 `infra/database/README.md`）
2. 安装 Tomcat 8.5+
3. 使用 Eclipse 导入本项目（或手动将 `WebContent` 部署为 WAR）
4. 根据实际环境修改 `DBHelper.java` 中的数据库连接参数：
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/db_dondonqiang20?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "12345678";
   ```
5. 启动 Tomcat，访问 `http://localhost:8080/DonDonQiang20_Server/` 确认服务运行

## 架构说明

```
Android 客户端  -->  Servlet 控制层  -->  DAO 数据访问层  -->  MySQL 数据库
                    (controller/)      (dao/)               (db_dondonqiang20)
                          |
                    DBHelper (连接工具)
```

- 使用 `@WebServlet` 注解注册 Servlet，无需 `web.xml`
- 每次数据库操作创建新连接，无连接池
- 使用 MySQL 用户变量动态计算排名
