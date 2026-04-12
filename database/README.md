# db_dondonqiang20 - 数据库

捕鱼达人游戏的 MySQL 数据库，包含用户数据和管理员账户。

## 环境要求

- MySQL 8.0+

## 导入数据库

```bash
mysql -u root -p < db_dondonqiang20.sql
```

导入后会创建名为 `db_dondonqiang20` 的数据库及两张表。

## 数据表结构

### users - 用户表

存储游戏玩家账户信息和最高分。

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `user_id` | INT(11) | 主键, 自增 | 用户 ID |
| `user_name` | VARCHAR(20) | NOT NULL | 用户名 |
| `user_password` | VARCHAR(20) | | 密码 |
| `user_maxscore` | INT(11) | 默认 0 | 历史最高分 |

### admin - 管理员表

存储后台管理面板的管理员账户。

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | INT(11) | 主键, 自增 | 管理员 ID |
| `admin_name` | VARCHAR(20) | NOT NULL | 管理员用户名 |
| `admin_password` | VARCHAR(20) | NOT NULL | 管理员密码 |

## 初始数据

- 管理员账户: 用户名 `admin`，密码 `admin`
- 预置约 20 个测试用户（Mike, Amy, Tom, Josh 等），最高分从 40 到 5000 不等

## 服务器连接配置

服务端连接配置硬编码在 `server/src/.../util/DBHelper.java` 中：

- **地址:** `localhost:3306`
- **数据库名:** `db_dondonqiang20`
- **用户名:** `root`
- **密码:** `12345678`

如需修改，请同步更新 `DBHelper.java` 中的连接参数。
