### 同步插件 文件修改  、、 20190622
1.projectRootDir/build.gradle 修改
classpath 'com.github.bxvip:AsyncPlugins:v1.0.0.6'  -->  classpath 'com.github.bxvip:AsyncPlugins:1.0.0.7'
> buildscript {
>     ext.kotlin_version = '1.2.31'
> 
>     repositories {
>         google()
>         jcenter()
>         maven { url 'https://jitpack.io' }
>     }
>     dependencies {
>         classpath 'com.android.tools.build:gradle:3.0.1'
>         classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
>         classpath 'com.qihoo360.replugin:replugin-host-gradle:2.2.4'
>         classpath 'com.qihoo360.replugin:replugin-plugin-gradle:2.2.4'
>         classpath 'com.github.bxvip:AsyncPlugins:1.0.0.7'
> 
>         // NOTE: Do not place your application dependencies here; they belong
>         // in the individual module build.gradle files
>     }
> }

### 同步插件 文件修改  、、 20190618
1.projectRootDir/build.gradle 修改
classpath 'com.github.bxvip:AsyncPlugins:v1.0.0.3'  -->  classpath 'com.github.bxvip:AsyncPlugins:1.0.0.6'
> buildscript {
>     ext.kotlin_version = '1.2.31'
> 
>     repositories {
>         google()
>         jcenter()
>         maven { url 'https://jitpack.io' }
>     }
>     dependencies {
>         classpath 'com.android.tools.build:gradle:3.0.1'
>         classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
>         classpath 'com.qihoo360.replugin:replugin-host-gradle:2.2.4'
>         classpath 'com.qihoo360.replugin:replugin-plugin-gradle:2.2.4'
>         classpath 'com.github.bxvip:AsyncPlugins:1.0.0.6'
> 
>         // NOTE: Do not place your application dependencies here; they belong
>         // in the individual module build.gradle files
>     }
> }


###新加线上插件同步功能 以前存在的项目修改步骤 // 20190418
* 1.projectRootDir/build.gradle 修改
> buildscript {
>     ext.kotlin_version = '1.2.31'
> 
>     repositories {
>         google()
>         jcenter()
>         maven { url 'https://jitpack.io' }
>     }
>     dependencies {
>         classpath 'com.android.tools.build:gradle:3.0.1'
>         classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
>         classpath 'com.qihoo360.replugin:replugin-host-gradle:2.2.4'
>         classpath 'com.qihoo360.replugin:replugin-plugin-gradle:2.2.4'
>         classpath 'com.github.bxvip:AsyncPlugins:v1.0.0.3'
> 
>         // NOTE: Do not place your application dependencies here; they belong
>         // in the individual module build.gradle files
>     }
> }

* 2.projectRootDir/host/build.gradle  添加
> apply plugin: 'asyncplugin'
> 
> apply plugin: 'com.android.application'
> apply plugin: 'kotlin-android'
> apply plugin: 'kotlin-android-extensions'
> apply plugin: 'asyncplugin'

* 3.删除 projectRootDir/host/build.gradle 底部多余的代码
>// copy http lib to git http lib
>def gitHttpPath = rootDir.absolutePath + "/into-plugin"
>
>println("copy libs jar:$gitHttpPath")
>
>// copy /into-plugin/a.b.c.d.e.jar
>def gitHttpPathFile = new File(gitHttpPath)
>if (gitHttpPathFile.exists()) {
>    println("copy libs jar:$name start!")
>    GFileUtils.copyDirectory(gitHttpPathFile, new File(projectDir.absolutePath + "/src/main/assets/plugins"))
>    println("copy libs jar:$name succeed!")
>} else {
>    println("copy libs jar:$name not exists!")
>}




##// host/libs/host-sdk-release-1.0.8.aar  版本修改底层类库更改  // 20180609
* 在已经编辑的代码上面改动步骤：
   * [host/libs/host-sdk-release-1.0.8.aar](host/libs/host-sdk-release-1.0.8.aar)  确保已经是1.0.8版本的aar
   * copy [/into-plugin/a.b.c.d.e.jar](/into-plugin/a.b.c.d.e.jar) /into-plugin/a.b.c.d.e.jar 到 /host/src/main/assets/plugins/
   * 修改 [config.gradle](host-plugin-config/config.gradle) jitParkAndroidSupportVersion = '26.0.2.10' 确认已经是 26.0.2.10
                                                              jitParkSupportHttpVersion = '1.0.10.5.5' 确认已经是 1.0.10.5.5
   * 添加[host/build.gradle](host/build.gradle)  在 dependencies 中 implementation "com.qihoo360.replugin:replugin-host-lib:$replugin_version"
   
具体内容可以看git log 提交的文件


// 注意： 已经上市场的app 可以 走 plist-admin.bxvip588.com  安卓全量更新。




##（2018-06-20）//- ！！！！！！ 由于其他开发者 说不能使用v7包，我们添加了插件[plugin-nosdklib](/plugin-nosdklib)的例子 ，不好之处是插件包非常的大，比较损耗性能，加载速度慢，不支持AppCompatActivity配置的颜色，没有宿主没有配置可用的坑位，也不打算扩展



## (2018-11-24) 修复 db库的bug，记录状态的，后台关闭了，也不影响
* 开发只要修改两个文件 把远端仓库的 host-plugin-config/config.gradle -> jitParkAndroidSupportVersion = '26.0.2.11.3" >jiParkAndroidSupportVersion = '26.0.2.11.6'
* copy [/into-plugin/a.b.c.d.e.jar](/into-plugin/a.b.c.d.e.jar) /into-plugin/a.b.c.d.e.jar