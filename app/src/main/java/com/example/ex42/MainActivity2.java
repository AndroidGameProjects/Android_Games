package com.example.ex42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ex42.util.PayResult;

import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

        private Button btnPay;
        private static final int SDK_PAY_FLAG = 1001;
        private String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNYm+oveZOECAjwrH1E+RHznGxVqdAKI/teijarKYIV7RjpNyfMaEaI0ms8vd9aXtN6gEeSPvBQmWVunY1FWfLpAOkSYGJLJ8GJEgiNTAstCgkHw21DaojrD9LxoUZbvfBwWXiDLDAPUGiU6pnG7AkClJuzSETMCTWsrcB35Y9MMprnPaXgNG8+MJ6P2Z1xmN51uNQw4Z99iDrR27lrQH/OXNzLnRDzlj0rwoYFHDSPds58qmjVRTcBXCVpZoLmuf4OfSc8gplNGz/qs/rjOfKEOrcZQeKw1SCkG5U4ZHsMM5XmwbCGg20G9+BokYdHJNKFKu/+kwu69No1Mcy8RTfAgMBAAECggEAIXBCkFo5egT+VPbbN+d4ejMtWI/yBo6RW80klHN44Ug89cQsGcqXG6N07V6ZgiPMceUCVrNUN6UIeZ0cD/n8DoHACr8Hz/Wptr4mAVErD6ecRs7BYyzULJO0dKuDFzzThBPFkO0HcLAMMeQvzSsTQbLfRC1nwS4FyHGELwE+e0IQy3wug7jAid/X2crGC438pwxS7iCjZxsO44WCteCLTjIG/y2AR42wJXSRlPpsGQP6CVgUKa1ATEsoGBDoImDAitnPAyADyOvRMf3jqOcadWq8MtXKPM1KyfM1Sq+NgPawwXxdBHPXB4aDPHmoZm3qb8Nat1VkbTfnmnFNVNiGAQKBgQDGcR0xEI/oP/HRdhKQJCNguUN2dcXIfbfLj4ff9yMtQ+086W3BpJYO5rq6B8mXU66wg3crKJHwpaQ5a6CXb1U757y2J2qPccKdy3ZXed7z0bEkGxwPzwkAiNXM30KvHO9QxVFX3oILDca2qOk7h5vRrRCH9GHdZkYgf7F0WRFwnwKBgQC2ZKYOVPE881ek0SFHURuTN99M+MsciyLzJNeRpopXCBvViRV3rMvyzCRsciJEqQmZnQM7VDkqh3MtutEDnPv2Qux3Qlhk756Q8PdmS9hPl9WK8NGSSA6AQFGqrV16ngjYRm1h+fm6c6K9YFaoJXw/5qYF48X0hXRE39++TXSzwQKBgBnji/Fovb2JCh1PkCBp9ouZ3+lGeCUt8ZqHAS0A6v/uyraVpZILzN/ozheTCIPLkRDKNfPVeSSyF3i+R9c52R7VntMM1WQdbUx0zN2gsquQgdG6D7EoS35cW7g8sFB0L+yTsYcLKmASzgfqhXMUwAlc0LlL8rCVtTRsNFR/gjz1AoGAUiANmSRsHvqe+wpjRp5hoS8mL51Srz6C9SIgomdvoPJ4vfRkoyc+Ccwblmzpuyq1tOI640rwFpM4rF2S4WKdHOxTVvubm489QZwOeZQrCOOf9liqtIgXZ24Ol6BKF/zylJdZhyUsaeTJYSXwvvNp98fd94bwykIQ8TYwo5pyssECgYAZC+l1Ok0VJyisBLgOHoAuwYmWbFRC0RJAwQQoTs4/ozHiR+kFOgiHY6W7sjfgdMej+0U0gNifm2nn0lj1KRuOXiAzkzRBTkiwDChP0PAa2ns9GSbxApRVPJJzeM2NlRX4ptscjKUqWB3tgqPNWDTjW0d7iCYeFWkx0GfRgSwHaQ==";
        public static final String APPID = "2017062807585767";

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        //同步获取结果
                        String resultInfo = payResult.getResult();
                        Log.i("Pay", "Pay:" + resultInfo);
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            Toast.makeText(MainActivity2.this, "支付成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity2.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            initView();
        }

        private void initView() {
            btnPay = (Button) findViewById(R.id.btnPay);
            btnPay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btnPay:
//                    //秘钥验证的类型 true:RSA2 false:RSA
//                    boolean rsa = false;
//                    //构造支付订单参数列表
//                    Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa);
//                    //构造支付订单参数信息
//                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//                    //对支付参数信息进行签名
//                    String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa);
//                    //订单信息
//                    final String orderInfo = orderParam + "&" + sign;
//                    //异步处理
//                    Runnable payRunnable = new Runnable() {
//
//                        @Override
//                        public void run() {
//                            //新建任务
//                            PayTask alipay = new PayTask(MainActivity2.this);
//                            //获取支付结果
//                            Map<String, String> result = alipay.payV2(orderInfo, true);
//                            Message msg = new Message();
//                            msg.what = SDK_PAY_FLAG;
//                            msg.obj = result;
//                            mHandler.sendMessage(msg);
//                        }
//                    };
//                    // 必须异步调用
//                    Thread payThread = new Thread(payRunnable);
//                    payThread.start();
//                    break;
//            }
        }
}



