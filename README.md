# daza-android

[「daza.io」](https://daza.io)是一款基于技能树的技术内容聚合应用，根据你的技能对内容进行筛选，让你在这个信息过载的时代里更高效地获取你所需的内容。

[![](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=io.daza.app)

## 应用截图
![](https://oeolgl6y5.qnssl.com/topic/SJHfR0vQe/HJYNCADXe.png?imageView2/2/w/1200/h/1200)
## 项目说明
> 本项目使用 Java 语言进行开发，基于 [BlankApp](https://github.com/lijy91/BlankApp) 框架搭建。

### 目录结构
```
.
├── README.md
├── app
│   ├── build
│   ├── build.gradle
│   ├── google-services.json
│   ├── gradle.properties
│   ├── gradle.properties.example
│   ├── libs
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── java
│       │   │   └── io
│       │   │       └── daza
│       │   │           └── app
│       │   │               ├── AppContext.java
│       │   │               ├── AppStart.java
│       │   │               ├── api
│       │   │               ├── event
│       │   │               ├── handler
│       │   │               ├── model
│       │   │               ├── receiver
│       │   │               ├── ui
│       │   │               └── util
│       │   └── res
│       └── test
│           └── java
├── build.gradle
├── gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── settings.gradle
```

## 入门指南

1、下载或克隆源码

```
$ git clone https://github.com/lijy91/daza-android.git
$ cd daza-android
```

2、修改配置项
```
$ cp app/gradle.properties.example app/gradle.properties
```
> 使用编辑器打开并修改相关的配置项

3、运行
> Run -> Run 'app'

## 相关项目
- [daza-backend](https://github.com/lijy91/daza-backend)
- [daza-frontend](https://github.com/lijy91/daza-frontend)
- [daza-ios](https://github.com/lijy91/daza-ios)
- [daza-android](https://github.com/lijy91/daza-android)

## 关于作者

![](https://oeolgl6y5.qnssl.com/topic/ByRafuLR/r1no_q9R.jpg?imageView2/2/w/200)

> 如果你有什么好想法想告诉我，或者想加入讨论组（注明加入讨论组），请加我微信。

## 捐赠

![](http://obryq3mj0.bkt.clouddn.com/topic/ByRafuLR/r1WH8F90.jpg?imageView2/2/w/200)

> 如果你觉得我的工作对你有帮助，那你可以为项目捐赠运营费用。

## License

    Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
