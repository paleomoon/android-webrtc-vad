package com.wzc.vad;

/**
 * Created by wangzhengcheng on 2017/10/10.
 */

public class VadUtils {

    static {
        System.loadLibrary("wzc_webrtc_vad");
    }

    /**
     * 创建vad实例
     */
    public native void create ();

    /**
     * 销毁vad实例
     */
    public native void free ();

    /**
     * vad初始化
     * @return 0初始化成功  -1初始化失败
     */
    public native int init();

    /**
     * 设置vad操作模式,可以理解为灵敏度设置，值越大灵敏度越高
     * @param mode 可设置为(0,1,2,3)
     * @return 0设置成功  -1设置失败
     */
    public native int setMode(int mode);

    /**
     * 核心处理方法
     * @param fs 采样率 支持 8000 16000 32000
     * @param buffer 音频数据
     * @param length 音频数据长度
     * @return  1 - (Active Voice),
     *          0 - (Non-active Voice),
     *         -1 - (Error)
     */
    public native int process(int fs,short[] buffer,int length);

    /**
     * We support 10,20 and 30 ms frames and the rates 8000, 16000 and 32000 Hz.
     * 所以这里应该是检查参数组合是否符合要求，例如8000采样率下必须每次传递10ms的数据，16000-20 32000-30
     * 这个方法实际过程中并未使用到，我也不确定是否还有更深的含义，这里不做重点关注了
     * @return 0 - (valid combination), -1 - (invalid combination)
     */
    public native int validRateAndFrameLength(int fs,int length);



}
