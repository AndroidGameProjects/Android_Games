package com.example.ex42;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex42.Pay.demo.Game.GameMainActivity;
import com.example.ex42.database.ShoppingDBHelper;
import com.example.ex42.database.enity.User;
import com.example.ex42.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView captchaImageView;
    private TextView et_Main_account;
    private TextView et_Main_password;
    private Button btn_login;
    private TextView tv_forgetPassword;
    private TextView tv_NewUserRegister;
    private ShoppingDBHelper mDBHelper;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(MainActivity.this);
        setContentView(R.layout.activity_main);

        initView();
        initLisnter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDBHelper();
    }

    private void initDBHelper() {
        mDBHelper = ShoppingDBHelper.getInstance(getApplicationContext());
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();
    }

    private void initLisnter() {
        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        tv_NewUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_Main_account.getText().toString().equals("") || et_Main_password.getText().toString().equals("")){
                    ToastUtil.show(MainActivity.this,"请输入正确信息");
                }else {
                    Login();
                }

            }
        });
    }

    private void Login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = mDBHelper.queryByAccount(et_Main_account.getText().toString());//查出的用户
                if (user == null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(MainActivity.this,"不存在该用户");
                        }
                    });
                }else {
                    String InputPassword = et_Main_password.getText().toString();
                    if (InputPassword.equals(user.password)){
                        Intent intent = new Intent(MainActivity.this, GameMainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show(MainActivity.this,"密码错误");
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void initView() {
        tv_forgetPassword = findViewById(R.id.tv_forgetPassword);
        tv_NewUserRegister = findViewById(R.id.tv_NewUserRegister);
        btn_login = findViewById(R.id.btn_login);
        et_Main_account = findViewById(R.id.et_Main_account);
        et_Main_password = findViewById(R.id.et_password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }

    public  void hideStatusBar(Activity activity) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (window == null) return;
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        WindowManager.LayoutParams lp = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(lp);
    }
}