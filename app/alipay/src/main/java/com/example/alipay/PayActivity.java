package com.example.ex42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends AppCompatActivity {
    private PayService.PalAction mPalAction;
    private EditText mPasswordBox;
    private boolean mIsBind;
    private static final String TAG = "PayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        doBindService();
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPalAction != null){
            mPalAction.onUserCancel();
        }
    }

    private void initView() {
        Intent intent = getIntent();
        String orderInfo = intent.getStringExtra(Constants.KEY_BILL_INFO);
        final float payMoney = intent.getFloatExtra(Constants.KEY_PAY_MONEY,0f);
        TextView orderInfoTv = this.findViewById(R.id.order_info_tv);
        orderInfoTv.setText("支付信息:" + orderInfo);
        TextView payMoneyTv = this.findViewById(R.id.pay_money);
        payMoneyTv.setText("支付金额:" + payMoney + "元");
        mPasswordBox = this.findViewById(R.id.pay_password_input);
        Button commitBtn = this.findViewById(R.id.pay_commit);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交点击了
                String trim = mPasswordBox.getText().toString().trim();
                if ("123456".equals(trim) && mPalAction != null){
                    mPalAction.Pay(payMoney);
                    finish();
                    Log.d(TAG, "onClick: finish");
                }else {
                    Toast.makeText(PayActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void doBindService() {
        Intent intent = new Intent(this,PayService.class);
        mIsBind = bindService(intent,mConnection,BIND_AUTO_CREATE);

    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPalAction = (PayService.PalAction)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPalAction = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind && mConnection != null){
            unbindService(mConnection);
            mIsBind = false;
            mConnection = null;
        }
    }
}