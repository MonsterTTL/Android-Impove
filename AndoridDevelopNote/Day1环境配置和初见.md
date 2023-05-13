# Android开发日志

## 遇到的问题和解决方案：
>1.SDK开发工具版本不匹配  
  **解决方案**：在Settings -》 System -》 Android中更改SDKTools设置   
>2.build.gradle 中文件描述的不同   
   没有构建插件和托管平台  
   具体代码也有不同


## 笔记：
### 1.Android 项目的文件结构
  来到Project目录下的文件：  
  * 1. .gradle 和 .idea 文件，Android Studio 自动生成的文件，无需关心
  * 2. app 存放着项目中的代码，资源等，是我们开发主要使用到的文件   
  * 3. build 包含了一些编译时自动生成的文件  
  * 4. gradle 包含gradle warpper的配置文件   
  * 5. .gitigonre 关于版本控制的文件
  * 6. build.gradle 项目全局的gradle构建脚本   
  * 7. gradle.properties 全局的gradle配置文件，在这里配置的属性将影响到项目中所有的gradle编译脚本     
  * 8. gradlew和gradlew.bat用来在命令行界面中执行gradle命令的，其中gradlew是在Linux 或Mac 系统中使用的，gradlew.bat是在Windows下运行的   
  * 9. MyApplication.iml是IDEA会自动生成的一个文件   
  * 10. local.properties 用于指定本集中的Android SDK 文件路径.   
  * 11. settings.gradle 用于指定项目中所有引用的模块.

### 2.app 目录下的结构  
来到app目录下的文件：  
* 1. build 包含一些在编译时自动生成的文件.   
* 2. libs 放在这个目录下的jar包都会被自动添加到构建路径中   
* 3. androidTest 用来编写Android Test测试用例的，可以对项目进行一些自动化测试  
* 4. java 放置我们所有Java代码的地方    
* 5. res 项目中所有使用到的图片，布局，字符串等资源    
* 6. AndroidMainifest.xml 知识整个Android项目的配置文件，程序中定义的四大组件都需要在这里注册，另外还可以在这个文件中给应用程序添加权限声明.    
* 7. test 用来编写Unit Test 测试用例的， 是对项目进行自动化测试的另一种方式.   
* 8. .gitigonre 用于将app模块内的指定的目录或文件排除在版本控制之外.   
* 9. app.iml IDEA自动生成的文件    
* 10. build.gradle 这是app模块的gradle构建脚本    
* 11. proguard-rules.pro 用于指定项目代码的混淆规则。

### 3.Android四大组件：
>1.活动(activity)门面   
>2.服务(Service)    
>3.广播接收器(BroadCast Receiver)和外部交换信息  
>4.内容提供器(Content Provider)和内部交换信息

### 4.Log日志相关
 **Log.等级(tag,meg);**  
  -  ->5个等级
      {
        v - 最低   
        d   
        i   
        w   
        e - 最高
      }
  -  tag 一般是该类名，用于过滤   
  - mes 是真正想要打印的内容    
 **快捷键**  
 在onCreate方法外输入logt可以生成一个常量，方便使用。  
 **过滤器** 
 用于过滤日志  
