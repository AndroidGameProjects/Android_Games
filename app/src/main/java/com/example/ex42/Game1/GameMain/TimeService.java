package com.example.ex42.Game1.GameMain;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeService extends Service {
    private int seconds = 0;
    private boolean isRunning = false;
    private Handler handler = new Handler();
    private String time ;
    private long startTime = 0;

    public TimeService(){
        super();
    }



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int hours = (int) (elapsedTime / 3600000);
            int minutes = (int) ((elapsedTime % 3600000) / 60000);
            int seconds = (int) ((elapsedTime % 60000) / 1000);
            time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            Intent intent = new Intent("com.example.ex42");
            intent.putExtra("time", time);
            sendBroadcast(intent);
            handler.postDelayed(this, 1000);
        }
    };

    public void pauseTimer() {
        handler.removeCallbacks(runnable);
        isRunning = false;
    }

    public void continueTimer(){

    }



    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取传递的参数
        if (intent != null && intent.hasExtra("time")) {
            time = intent.getStringExtra("time");
        }

        // 检查时间字符串是否为空
        if (time == null || time.isEmpty()) {
            // 如果时间字符串为空，则使用当前时间作为默认值
            time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        }

        // 解析时间字符串，计算启动时间
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            // 如果时间字符串无效，则使用当前时间作为默认值
            date = new Date();
        }
        long delay = date.getTime() - System.currentTimeMillis();

        // 启动计时器
        handler.postDelayed(runnable, delay);
        isRunning = true;

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}