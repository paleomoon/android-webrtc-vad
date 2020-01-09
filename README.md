# android-webrtc-vad

### Android单独抽取WebRtc-VAD(语音端点检测)模块

#### csdn:https://blog.csdn.net/always_and_forever_/article/details/81110539

### 使用说明
#### 1.下载源码，直接运行即可

### 工程解读
#### 1.根目录下的jni目录，是从webrtc源码中抽取出来的vad模块核心代码文件.
#### 2.libs目录下，为编译jni生成的so文件，您可以直接使用.
#### 3.webrtc-vad.apk 可直接安装到真机上快速体验.
#### 4.运行工程或apk，如图，根据流程您需要先点击 create-->init-->setMode，以上三步分别为创建、初始化、设置模式，之后可以startRecord，顶部状态文本会显示当前语音端点检测结果，结束录音点击stopRecord,最后销毁实例用free
#### 5.如您需要体验生成so这一步骤，可cd到jni目录下，执行ndk-build命令，前提是您下载了ndk且配置了环境变量，否则ndk命令无法识别

![Image text](https://raw.githubusercontent.com/wangzhengcheng1994/android-webrtc-vad/master/img/pic1.jpg)
![Image text](https://raw.githubusercontent.com/wangzhengcheng1994/android-webrtc-vad/master/img/pic2.jpg)
