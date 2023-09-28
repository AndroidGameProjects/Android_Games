package com.example.ex42;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alipay.ThirdPartPayAction;
import com.example.alipay.ThirdPartPayResult;

public class MainActivity1 extends AppCompatActivity {
    private static final String TAG = "MainActivity1";
    private TextView mSobCountTv;
    private Button mButSobBtn;
    private AlipayConnection mAlipayConnection;
    private boolean mIsBind;
    private ThirdPartPayAction mThirdPartPayAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //绑定支付宝的服务，在现在开发中，这部分是由支付宝SDK完成的
        bindAlipayService();

        //找到控件
        initView();
        setListener();
    }

    //绑定支付宝的服务
    private void bindAlipayService() {
        Intent intent = new Intent();
        intent.setAction("com.example.alipay.THIRD_PART_PAY");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.example.alipay");

        mAlipayConnection = new AlipayConnection();
        mIsBind = bindService(intent,mAlipayConnection,BIND_AUTO_CREATE);
    }

    private void setListener() {
        mButSobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO:进行充值
                try {
                    if (mThirdPartPayAction != null) {
                        Log.d(TAG, "onClick: 111111111111");
                        mThirdPartPayAction.request("充值100币",100.00f,new PayCallBack());
                    }else {
                        Log.d(TAG, "onClick: 222222222222");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class PayCallBack extends ThirdPartPayResult.Stub{

        @Override
        public void onPaySuccess() throws RemoteException {
            //支付成功，修改UI内容
            //实际修改服务器数据库，实际支付宝通过url地址回调，通知服务器
            mSobCountTv.setText("100" );
            Toast.makeText(MainActivity1.this,"充值成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPayFailed(int errorCode, String msg) throws RemoteException {
            Log.d(TAG, "error is :" + errorCode + "msg:" + msg);
            Toast.makeText(MainActivity1.this,"充值成功",Toast.LENGTH_SHORT).show();
        }


    }

    private class AlipayConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + service);
            mThirdPartPayAction = ThirdPartPayAction.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + name);
        }
    }

    private void initView() {
        mSobCountTv = findViewById(R.id.sob_count_tv);
        mButSobBtn = findViewById(R.id.buy_sob_btn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind && mAlipayConnection != null) {
            Log.d(TAG, "unbindService");
            unbindService(mAlipayConnection);
            mAlipayConnection = null;
            mIsBind = false;
        }
    }

}