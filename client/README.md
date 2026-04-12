# DonDonQiang2.0 - Android 客户端

捕鱼达人游戏的 Android 客户端，使用原生 Java 开发。

## 技术栈

- **语言:** Java 1.8
- **构建工具:** Gradle 6.5 + Android Gradle Plugin 4.1.3
- **SDK:** compileSdkVersion 30, minSdkVersion 28 (Android 9.0)
- **UI 框架:** AndroidX (AppCompat, Material Components, ConstraintLayout)
- **网络请求:** android-async-http (loopj) 1.4.9
- **JSON 解析:** Alibaba FastJSON 1.2.76

## 项目结构

```
app/src/main/
├── AndroidManifest.xml
├── java/com/dondonqiang2/
│   ├── LoginActivity.java        # 登录 + 注册界面（启动入口）
│   ├── GameActivity.java          # 游戏主界面
│   ├── ResultActivity.java        # 结算界面
│   ├── ListActivity.java          # 排行榜界面
│   ├── RegisterDialog.java        # 注册对话框（未使用）
│   ├── SettingsDialog.java        # 设置对话框（未使用）
│   └── mysqlite/
│       ├── DBHelper.java          # SQLite 本地数据库（定义但未使用）
│       ├── UsersDao.java          # 本地数据访问对象（定义但未使用）
│       └── User.java              # 用户数据模型
└── res/
    ├── layout/                    # 界面布局文件
    ├── drawable/                  # 图片资源（鱼、背景、UI 元素等约 30 个）
    ├── mipmap-*/                  # 应用图标
    └── values/                    # 字符串、颜色、主题配置
```

## 页面导航流程

```
LoginActivity (启动)
    ├── 登录成功 --> GameActivity
    │                  ├── 游戏结束（时间到或生命值为0）--> ResultActivity
    │                  │                                    ├── 重新开始 --> GameActivity
    │                  │                                    ├── 查看排名 --> ListActivity
    │                  │                                    └── 退出游戏
    │                  └── 返回 --> LoginActivity
    └── 注册 --> AlertDialog（弹窗注册）
```

## 游戏玩法

- **目标分数:** 2000 分
- **初始生命值:** 3
- **游戏时间:** 60 秒倒计时
- **好鱼 (fish1-4):** 点击 +100 分，抓鱼数 +1
- **坏鱼 (meanfish1-2):** 点击扣 1 条命
- **空击鱼塘:** 扣 1 条命
- **难度设置:** 可调整鱼移动速度（1-3 档）

## 服务端通信

客户端通过 HTTP POST 与 Java Servlet 后端通信，所有响应为 JSON 格式：

| 接口 | 调用位置 | 用途 |
|------|----------|------|
| `/LoginServlet` | LoginActivity | 用户登录验证 |
| `/RegisterServlet` | LoginActivity | 新用户注册 |
| `/RankServlet` | ResultActivity | 提交分数、获取排名 |
| `/ListViewServlet` | ListActivity | 获取前 10 名排行榜 |

> 服务器地址默认为 `http://192.168.43.147:8080/DonDonQiang20_Server/`，可根据实际环境修改各 Activity 中的 URL。

## 安装教程（虚拟机 + Android Studio）

以下以 **Windows** 为例，介绍从安装 Android Studio 到运行本客户端的完整流程。

### 1. 安装 Android Studio

1. 下载 Android Studio: https://developer.android.com/studio
2. 运行安装程序，一路 Next 保持默认选项即可
3. 首次启动时，安装向导会自动下载 Android SDK，等待完成

### 2. 创建 Android 虚拟设备（AVD）

1. 打开 Android Studio，点击顶部菜单 **Tools → Device Manager**
2. 点击 **Create Device**
3. 选择一个手机型号（如 Pixel 4），点击 **Next**
4. 选择系统镜像，需 API Level >= 28（对应 Android 9.0）。如果没有，点击 **Download** 下载 `Q (API 29)` 或 `R (API 30)` 镜像
5. 点击 **Next → Finish** 完成创建

### 3. 导入项目

1. 打开 Android Studio，选择 **Open an Existing Project**
2. 浏览到本仓库的 `client` 目录，点击 **OK**
3. 等待 Gradle 同步完成（右下角进度条消失即可）

### 4. 运行应用

1. 在 Device Manager 中启动刚才创建的虚拟机，等待开机完成
2. 顶部工具栏的运行配置选择 **app**，设备选择刚启动的虚拟机
3. 点击 **Run**（绿色三角按钮）或按 `Shift + F10`
4. 应用将自动安装到虚拟机并启动

### 5. 连接后端服务器

应用默认连接地址为 `http://192.168.43.147:8080/DonDonQiang20_Server/`。

- 如果后端运行在本机，将各 Activity 中的 IP 地址改为 `10.0.2.2`（虚拟机访问宿主机的特殊地址），即：
  ```
  http://10.0.2.2:8080/DonDonQiang20_Server/
  ```
- 确保后端服务器和数据库已正确启动（参见 `server/` 和 `database/` 目录的 README）
