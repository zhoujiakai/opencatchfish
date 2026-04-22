# 抓小鱼

安卓抓鱼小游戏。

## 演示

![游戏演示](assets/demo.gif)

- `client/` - Android 客户端（Java, Gradle）
- `server/` - Java Servlet 后端（Tomcat）
- `infra/database/` - MySQL 建表脚本

## 环境要求

- JDK 1.8+
- Android Studio（compileSdk 30）
- Tomcat 8.5+
- MySQL 8.0+

## 快速开始

1. 导入 `infra/database/db_dondonqiang20.sql` 到 MySQL
2. 修改 `server` 中数据库连接配置（`DBHelper.java`）
3. 将 `server` 部署到 Tomcat
4. 用 Android Studio 打开 `client`，编译运行
