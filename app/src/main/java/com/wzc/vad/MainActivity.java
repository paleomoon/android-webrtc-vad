package com.wzc.vad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback {
    private static final String TAG = "MainActivity";
    private VadUtils mVad;
    private boolean isRecording;
    private TextView mStatusView;
    private Handler mHandler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVad = new VadUtils();
        initView();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
    }

    private void initView() {
        mStatusView = findViewById(R.id.tv_status);
        Button btn_create = findViewById(R.id.btn_create);
        Button btn_free = findViewById(R.id.btn_free);
        Button btn_init = findViewById(R.id.btn_init);
        Button btn_setMode = findViewById(R.id.btn_setMode);
        Button btn_process = findViewById(R.id.btn_process);
        Button btn_stop = findViewById(R.id.btn_stopRecord);

        btn_create.setOnClickListener(this);
        btn_free.setOnClickListener(this);
        btn_init.setOnClickListener(this);
        btn_setMode.setOnClickListener(this);
        btn_process.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create://创建
                mVad.create();
                break;
            case R.id.btn_free://销毁
                mVad.free();
                break;
            case R.id.btn_init://初始化
                int initStatus = mVad.init();
                Log.d(TAG, "init status" + initStatus);
                break;
            case R.id.btn_setMode://设置vad模型等级
                int setModeStatus = mVad.setMode(3);
                Log.d(TAG, "set mode status" + setModeStatus);
                break;
            case R.id.btn_process://处理
                isRecording = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startRecord();
                    }
                }).start();
                break;
            case R.id.btn_stopRecord:
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isRecording = false;
                break;
        }
    }

    /**
     * 开始录音测试
     */
    private void startRecord() {
        int frequency = 8000;//8K采样
        int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                frequency, channelConfiguration,
                audioEncoding, bufferSize);

        short[] buffer = new short[80];
        audioRecord.startRecording();

        while (isRecording) {
            audioRecord.read(buffer, 0, buffer.length);
            int isDetect = mVad.process(8000, buffer, buffer.length);
            Log.d(TAG, "" + isDetect);
            Message message = mHandler.obtainMessage();
            message.what = 0x01;
            message.obj = isDetect == 1 ? "Active Voice..." : "Non-active Voice...";
            mHandler.sendMessage(message);
        }
        audioRecord.stop();
        audioRecord.release();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == 0x01) {
            String obj = (String) message.obj;
            mStatusView.setText(obj);
        }
        return true;
    }
}
