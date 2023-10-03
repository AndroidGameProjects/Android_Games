package com.example.ex42.Batter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ex42.R;

import java.lang.ref.WeakReference;

/**
 *  自定义View 展示界面
 * </p>
 * created by OuyangPeng at 2018/6/15 下午 03:33
 * @author OuyangPeng
 */
public class Test extends AppCompatActivity implements BaseHandlerCallBack {

    private PowerConsumptionRankingsBatteryView mPowerConsumptionRankingsBatteryView;
    private int power;

    private NoLeakHandler mHandler;
    private BroadcastReceiver mBatteryLevelReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_power);
        mHandler = new NoLeakHandler(this);
        mPowerConsumptionRankingsBatteryView = (PowerConsumptionRankingsBatteryView) findViewById(R.id.mPowerConsumptionRankingsBatteryView);

        mBatteryLevelReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                mPowerConsumptionRankingsBatteryView.setLevelHeight(batteryLevel);
                if (batteryLevel < 30) {
                    mPowerConsumptionRankingsBatteryView.setLowerPower();
                } else if (batteryLevel < 60) {
                    mPowerConsumptionRankingsBatteryView.setOffline();
                } else {
                    mPowerConsumptionRankingsBatteryView.setOnline();
                }
            }
        };

        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryLevelReceiver, batteryLevelFilter);

        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(mBatteryLevelReceiver);
    }

    @Override
    public void callBack(Message msg) {
        switch (msg.what) {
            case 0:
                mHandler.sendEmptyMessageDelayed(0, 1000); // update every second
                break;
            default:
                break;
        }
    }

    private static class NoLeakHandler<T extends BaseHandlerCallBack> extends Handler {
        private WeakReference<T> wr;

        public NoLeakHandler(T t) {
            wr = new WeakReference<>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            T t = wr.get();
            if (t != null) {
                t.callBack(msg);
            }
        }
    }
}