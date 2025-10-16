# iStudy - 智能学习辅助APP

## 项目概述

iStudy是一款基于AI技术的中小学生学习辅助Android应用，旨在通过智能化手段帮助学生提升学习效率，为家长提供学习监督工具，并连接优质师资资源。

## 主要功能

### 1. 学生注册模块
- 收集学生基本信息（姓名、年龄、性别、年级）
- 用于个性化学习内容推荐和AI出题

### 2. 学习建议模块
包含四个核心子模块：

#### 作业批改
- 📷 摄像头拍摄试卷和作业
- 🤖 AI识别并判断学生答题结果
- ✅ 提供正确解答和详细解析
- 📊 根据答题情况生成个性化学习方案
- 📝 生成针对性练习试卷

#### 课程重点讲解
- 📚 收集所有学科章节信息和重点内容
- 🎯 支持按学科和章节选择
- 💡 AI根据学生学习情况提供针对性建议

#### 精选题库
- 📖 海量题库覆盖所有学科
- 🎲 AI根据学生选择生成完整试卷
- 📈 智能难度调节

#### 考前冲刺
- 📋 支持单元、期中、期末考试类型选择
- 🔍 AI总结考试知识点并概括说明
- 📄 自动生成相应试卷

### 3. 推荐老师模块
- 👥 展示平台签约老师信息
- 🔍 按位置、学科、性别等条件筛选
- 📅 在线预约一对一补习

### 4. 家长专区
- 📊 提供完整的学生学习报告
- 📈 学习进度跟踪
- 🎯 成绩分析和改进建议

## 技术架构

### 开发环境
- **IDE**: Android Studio
- **语言**: Kotlin
- **UI框架**: Jetpack Compose
- **架构模式**: MVVM + Repository Pattern

### 核心技术栈
- **依赖注入**: Hilt
- **数据库**: Room
- **网络请求**: Retrofit + OkHttp
- **导航**: Navigation Compose
- **相机功能**: CameraX
- **图像处理**: Glide
- **权限管理**: Accompanist Permissions

### 项目结构
```
app/src/main/java/com/seecolab/istudy/
├── data/
│   ├── api/               # AI服务接口
│   ├── local/
│   │   ├── dao/          # 数据访问对象
│   │   └── converter/    # 类型转换器
│   ├── model/            # 数据模型
│   └── repository/       # 数据仓库
├── di/                   # 依赖注入模块
├── ui/
│   ├── navigation/       # 导航配置
│   ├── screens/          # 界面组件
│   │   ├── learning/     # 学习模块界面
│   │   └── student/      # 学生相关界面
│   ├── theme/            # 主题配置
│   └── viewmodel/        # 视图模型
├── MainActivity.kt       # 主活动
└── StudyApplication.kt   # 应用入口
```

## 数据模型

### 核心实体
- **Student**: 学生信息
- **Teacher**: 教师信息
- **Course**: 课程信息
- **Question**: 题目信息
- **TestPaper**: 试卷信息
- **HomeworkSubmission**: 作业提交记录
- **LearningReport**: 学习报告

### 枚举类型
- **Grade**: 年级（小学1-6年级，初中1-3年级，高中1-3年级）
- **Subject**: 学科（语文、数学、英语、物理、化学等）
- **Difficulty**: 难度等级
- **ExamType**: 考试类型（单元、期中、期末、练习）

## AI集成

### AI服务功能
1. **作业分析**: 图像识别 + 答案判断 + 学习建议
2. **试卷生成**: 基于学生水平的个性化出题
3. **学习推荐**: 根据学习历史推荐学习内容
4. **知识点总结**: 考试重点内容梳理

### Mock AI服务
目前使用Mock实现模拟AI功能，可替换为真实AI服务接口：
- 支持异步处理
- 模拟真实API响应格式
- 便于功能测试和演示

## 构建和运行

### 系统要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- Android SDK API Level 24+ (Android 7.0)
- Kotlin 1.9.10
- Gradle 8.2

### 构建步骤
1. 克隆项目到本地
2. 使用Android Studio打开项目
3. 等待Gradle同步完成
4. 连接Android设备或启动模拟器
5. 点击Run按钮构建并运行应用

### 主要依赖
```gradle
// Compose BOM
implementation platform('androidx.compose:compose-bom:2023.10.01')

// Hilt依赖注入
implementation 'com.google.dagger:hilt-android:2.48'

// Room数据库
implementation 'androidx.room:room-runtime:2.6.1'
implementation 'androidx.room:room-ktx:2.6.1'

// 网络请求
implementation 'com.squareup.retrofit2:retrofit:2.9.0'

// 相机功能
implementation 'androidx.camera:camera-camera2:1.3.1'
```

## 使用说明

### 首次使用
1. 打开应用后进入学生注册界面
2. 填写学生基本信息（姓名、年龄、性别、年级）
3. 完成注册后进入主界面

### 核心功能使用
1. **作业批改**: 在学习建议模块中选择作业批改，拍照上传作业照片
2. **课程学习**: 选择对应学科和章节查看重点内容
3. **刷题练习**: 在精选题库中生成个性化试卷
4. **考前复习**: 根据考试类型获取重点知识梳理
5. **找老师**: 在推荐老师模块筛选并预约合适的老师
6. **查看报告**: 家长可在家长专区查看学习进度和分析报告

## 待完善功能

- [ ] 真实AI服务集成
- [ ] 相机功能完整实现
- [ ] 推送通知
- [ ] 用户认证系统
- [ ] 支付系统（教师预约）
- [ ] 数据同步和备份
- [ ] 性能优化

## 贡献指南

欢迎提交Issue和Pull Request来改进项目。在贡献代码之前，请确保：
1. 代码符合项目的编码规范
2. 添加适当的测试用例
3. 更新相关文档

## 许可证

此项目仅用于学习和演示目的。