package com.example.ex42.Game2;

import static com.example.ex42.Pay.demo.PayDemoActivity.APPID;
import static com.example.ex42.Pay.demo.PayDemoActivity.RSA2_PRIVATE;
import static com.example.ex42.Pay.demo.PayDemoActivity.RSA_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.ex42.Game1.Game1_Enter;
import com.example.ex42.Game1.GameMain.Game;
import com.example.ex42.Game2.GameMain.ElfkfMainActivity;
import com.example.ex42.Pay.demo.AuthResult;
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

public class Game2_Enter extends AppCompatActivity implements MyViewPager.OnViewPagerTouchListener, ViewPager.OnPageChangeListener {
    private ShoppingDBHelper mDBHelper;
    private MyViewPager mLoopPager2;
    private LooperPagerAdapter mLooperPagerAdapter2;
    private static List<Integer> sPic2 = new ArrayList<>();
    private Handler mHandler2;
    private boolean mIsTouch2 = false;
    private LinearLayout mPointContainer2;
    private Button btn_gameBack2;
    private Button btn_bottom_begin2;
    private TextView et_ooxx_whetherBuy2;
    private String game1Str2 = "";
    private User mUser;
    private double Game_Price2 = 2.3;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    static {
        sPic2.add(R.drawable.fk);
        sPic2.add(R.mipmap.elsfk1);
        sPic2.add(R.mipmap.elsfk2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        HideStateBar h1 = new HideStateBar();
        h1.hideStatusBar(this);
        setContentView(R.layout.activity_game2_enter);
        mUser = (User) getIntent().getSerializableExtra("user");
        Log.d("game2_test", mUser.toString());
        game1Str2 = mUser.game2;
        initView();
        initListener();

        mHandler2 = new Handler();
    }

    private Runnable mLooperTask2 = new Runnable() {
        @Override
        public void run() {
            //轮播
            if (!mIsTouch2){
                int currentItem = mLoopPager2.getCurrentItem();
                mLoopPager2.setCurrentItem(++currentItem,false);
            }
            mHandler2.postDelayed(this,2000);
        }
    };

    private void initDate() {
        game1Str2 = mUser.game2;
        if (game1Str2.equals("已购买")){
            btn_bottom_begin2.setText("开始游玩");
            et_ooxx_whetherBuy2.setText("(已购买OVO)");
        }else {
            btn_bottom_begin2.setText("请购买");
            et_ooxx_whetherBuy2.setText("(未购买T⌓T)");
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //当我这个界面绑定到窗口的时候
        mHandler2.post(mLooperTask2);
    }

    private void initListener() {
        btn_gameBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_bottom_begin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bottom_begin2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String buttonText = btn_bottom_begin2.getText().toString();
                        if (buttonText.equals("请购买")) {
                            Pay2(v);
                        } else if (buttonText.equals("开始游玩")){
                            Intent intent = new Intent(Game2_Enter.this, ElfkfMainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    public void Pay2(View v) {
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
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,Game_Price2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Game2_Enter.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandlerPay2.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private void initView() {
        btn_gameBack2 = findViewById(R.id.btn_gameBack2);
        btn_bottom_begin2 = findViewById(R.id.btn_bottom_begin2);
        et_ooxx_whetherBuy2 = findViewById(R.id.et_elsfk_whetherBuy2);
        initDate();
        Log.d("game2xxxx", game1Str2);
        //找控件
        mLoopPager2 = (MyViewPager) findViewById(R.id.looper_pager2);
        //设置适配器
        mLooperPagerAdapter2 = new LooperPagerAdapter();
        mLooperPagerAdapter2.setData(sPic2);
        mLoopPager2.setAdapter(mLooperPagerAdapter2);


        mLoopPager2.setOnViewPagerTouchListener(this);
        mLoopPager2.addOnPageChangeListener(this);//监听轮播指令器
        //添加动态点
        mPointContainer2 = (LinearLayout) this.findViewById(R.id.points_container2);
        insertPoint();

        mLoopPager2.setCurrentItem(mLooperPagerAdapter2.getDataRealSize() * 100,false);
    }

    private void insertPoint() {
        for (int i = 0; i < sPic2.size(); i++) {
            View point = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40,40);
            point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal));
            layoutParams.leftMargin = 20;
            point.setLayoutParams(layoutParams);
            mPointContainer2.addView(point);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandlerPay2 = new Handler() {
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
                        ToastUtil.show(Game2_Enter.this, "支付成功");
                        updateBuy2();
//					showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.show(Game2_Enter.this, "支付失败");
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
                        ToastUtil.show(Game2_Enter.this, "支付成功");
                    } else {
                        // 其他状态值则为授权失败
//					showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
                        ToastUtil.show(Game2_Enter.this, "支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void initDBHelper() {
        mDBHelper = ShoppingDBHelper.getInstance(getApplicationContext());
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();
    }

    private void updateBuy2() {
        initDBHelper();
        mUser.game2 = "已购买";
        if (mDBHelper.update(mUser) > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(Game2_Enter.this, "数据库修改成功");
                }
            });
        }
        et_ooxx_whetherBuy2.setText("(已购买QvQ)");
        btn_bottom_begin2.setText("开始游玩");
    }

    @Override
    public void onPageTouch(boolean isTouch) {
        mIsTouch2 = isTouch;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //这个方法是viewpager停下来以后选中的位置
        int realPosition;
        if (mLooperPagerAdapter2.getDataRealSize() != 0){
            realPosition = position % mLooperPagerAdapter2.getDataRealSize();
        }else {
            realPosition = 0;
        }
        setSelectPoint(realPosition);
    }

    private void setSelectPoint(int realPosition) {
        for (int i = 0; i < mPointContainer2.getChildCount(); i++) {
            View point = mPointContainer2.getChildAt(i);
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