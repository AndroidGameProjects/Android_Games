package com.example.game;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock extends Service {
    public static Clock instance;
    private static String TAG = Clock.class.getName();
    private static final long LOOP_TIME = 1;
    private static ScheduledExecutorService mExecutorService;

    private static int count = 0;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            count++;
//            SetTime(count);
            Log.d("Clock", "=== count:" + count);
        }
    };

    private void SetTime(int time){
        Intent intent = new Intent();
        intent.putExtra("time", time);
        Game.instance.SetTimeView(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        count = 0;
        instance = this;
        mExecutorService = Executors.newScheduledThreadPool(2);
        mExecutorService.scheduleAtFixedRate(mRunnable, LOOP_TIME, LOOP_TIME, TimeUnit.SECONDS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onDestroy(){
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mExecutorService.shutdown();
        mExecutorService = null;
        mRunnable = null;
    }
}