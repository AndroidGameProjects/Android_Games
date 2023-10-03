package com.example.ex42.Game1;

import static com.example.ex42.Pay.demo.PayDemoActivity.APPID;
import static com.example.ex42.Pay.demo.PayDemoActivity.RSA2_PRIVATE;
import static com.example.ex42.Pay.demo.PayDemoActivity.RSA_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.ex42.ForgotPasswordActivity;
import com.example.ex42.Game1.GameMain.Game;
import com.example.ex42.Game2.Game2_Enter;
import com.example.ex42.Game2.GameMain.ElfkfMainActivity;
import com.example.ex42.Pay.demo.AuthResult;
import com.example.ex42.Pay.demo.PayDemoActivity;
import com.example.ex42.Pay.demo.PayResult;
import com.example.ex42.Pay.demo.PayUtil.OrderInfoUtil2_0;
import com.example.ex42.database.ShoppingDBHelper;
import com.example.ex42.util.HideStateBar;
import com.example.ex42.util.LunBo.LooperPagerAdapter;
import com.example.ex42.util.LunBo.MyViewPager;
import com.example.ex42.R;
import com.example.ex42.database.enity.User;
import com.example.ex42.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game1_Enter extends AppCompatActivity implements MyViewPager.OnViewPagerTouchListener, ViewPager.OnPageChangeListener {
    private ShoppingDBHelper mDBHelper;
    private MyViewPager mLoopPager;
    private LooperPagerAdapter mLooperPagerAdapter;
    private static List<Integer> sPic = new ArrayList<>();
    private Handler mHandler1;
    private boolean mIsTouch = false;
    private LinearLayout mPointContainer;
    private Button btn_gameBack;
    private Button btn_bottom_begin;
    private TextView et_ooxx_whetherBuy;
    private User mUser;
    private String game1Str = "";
    private double Game_Price = 1.3;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    static {
        sPic.add(R.drawable.ooxx);
        sPic.add(R.mipmap.ooxx1);
        sPic.add(R.mipmap.ooxx2);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        HideStateBar h1 = new HideStateBar();
        h1.hideStatusBar(this);
        setContentView(R.layout.activity_game1_enter);
        mUser = (User) getIntent().getSerializableExtra("user");
        game1Str = mUser.game1;
        Log.d("game1_test", mUser.toString());
        initView();
        initListener();

        mHandler1 = new Handler();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //当我这个界面绑定到窗口的时候
        mHandler1.post(mLooperTask);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler1.removeCallbacks(mLooperTask);
    }

    private Runnable mLooperTask = new Runnable() {
        @Override
        public void run() {
            //轮播
            if (!mIsTouch){
                int currentItem = mLoopPager.getCurrentItem();
                mLoopPager.setCurrentItem(++currentItem,false);
            }
            mHandler1.postDelayed(this,2000);
        }
    };


    private void initDate() {
        game1Str = mUser.game1;
        if (game1Str.equals("已购买")){
            btn_bottom_begin.setText("开始游玩");
            et_ooxx_whetherBuy.setText("(已购买OVO)");
        }else {
            btn_bottom_begin.setText("请购买");
            et_ooxx_whetherBuy.setText("(未购买T⌓T)");
        }
    }

    private void initListener() {
        btn_gameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_bottom_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bottom_begin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String buttonText = btn_bottom_begin.getText().toString();
                        if (buttonText.equals("请购买")) {
                            Pay(v);
                        } else if (buttonText.equals("开始游玩")){
                            Intent intent = new Intent(Game1_Enter.this, Game.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    public void Pay(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            return;
        }
        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,Game_Price);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Game1_Enter.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandlerPay.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandlerPay = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.show(Game1_Enter.this, "支付成功");
                        updateBuy();
//					showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.show(Game1_Enter.this, "支付失败");
//					showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//					showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
                        ToastUtil.show(Game1_Enter.this, "支付成功");
                    } else {
                        // 其他状态值则为授权失败
//					showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
                        ToastUtil.show(Game1_Enter.this, "支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void updateBuy() {
        initDBHelper();
        mUser.game1 = "已购买";
        if (mDBHelper.update(mUser) > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(Game1_Enter.this, "数据库修改成功");
                }
            });
        }
        et_ooxx_whetherBuy.setText("(已购买QvQ)");
        btn_bottom_begin.setText("开始游玩");
    }

    private void initDBHelper() {
        mDBHelper = ShoppingDBHelper.getInstance(getApplicationContext());
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();
    }

    private void initView() {
        btn_gameBack = findViewById(R.id.btn_gameBack);
        btn_bottom_begin = findViewById(R.id.btn_bottom_begin);
        et_ooxx_whetherBuy = findViewById(R.id.et_ooxx_whetherBuy);
        initDate();
        Log.d("game1xxxx", game1Str);
        //找控件
        mLoopPager = (MyViewPager) findViewById(R.id.looper_pager);
        //设置适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        mLooperPagerAdapter.setData(sPic);
        mLoopPager.setAdapter(mLooperPagerAdapter);


        mLoopPager.setOnViewPagerTouchListener(this);
        mLoopPager.addOnPageChangeListener(this);//监听轮播指令器
        //添加动态点
        mPointContainer = (LinearLayout) this.findViewById(R.id.points_container);
        insertPoint();

        mLoopPager.setCurrentItem(mLooperPagerAdapter.getDataRealSize() * 100,false);
    }

    private void insertPoint() {
        for (int i = 0; i < sPic.size(); i++) {
            View point = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40,40);
            point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal));
            layoutParams.leftMargin = 20;
            point.setLayoutParams(layoutParams);
            mPointContainer.addView(point);
        }
    }

    @Override
    public void onPageTouch(boolean isTouch) {
        mIsTouch = isTouch;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //这个方法是viewpager停下来以后选中的位置
        int realPosition;
        if (mLooperPagerAdapter.getDataRealSize() != 0){
            realPosition = position % mLooperPagerAdapter.getDataRealSize();
        }else {
            realPosition = 0;
        }
        setSelectPoint(realPosition);
    }

    private void setSelectPoint(int realPosition) {
        for (int i = 0; i < mPointContainer.getChildCount(); i++) {
            View point = mPointContainer.getChildAt(i);
            if (i != realPosition) {
                //没选择的颜色
                point.setBackgroundResource(R.drawable.shape_point_normal);
            }else {
                //选中的颜色
                point.setBackgroundResource(R.drawable.shape_point_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}