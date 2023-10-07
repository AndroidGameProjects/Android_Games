package com.example.ex42;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ex42.Game1.GameMain.Game;
import com.example.ex42.database.ShoppingDBHelper;
import com.example.ex42.database.enity.User;
import com.example.ex42.util.GetString;
import com.example.ex42.util.HideStateBar;
import com.example.ex42.util.ToastUtil;
import com.example.ex42.util.TreadData;
import com.example.ex42.util.UniversalThread;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Handler;

public class RegisterActivity extends AppCompatActivity {

    private static ImageView Yz_code;
    private static Button btn_Register;
    private static EditText et_Account;
    private static EditText et_Password;
    private static EditText ed_Code;
    private static Button btn_back_Register;
    private static String code;
    private ShoppingDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStateBar h1 = new HideStateBar();
        h1.hideStatusBar(this);
        setContentView(R.layout.activity_register);
        initView();
        initCode();
        initListener();
        initDBHelper();
    }

    private void initDBHelper() {
        mDBHelper = ShoppingDBHelper.getInstance(getApplicationContext());
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();
    }

    private void initListener() {
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_Account.getText().toString().equals("") || et_Password.getText().toString().equals("")){
                    ToastUtil.show(RegisterActivity.this,"请输入正确的用户名或密码");
                }
                if (ed_Code.getText().toString().toLowerCase().equals(code.toLowerCase())){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = mDBHelper.queryByAccount(et_Account.getText().toString());
                            if (user == null){
                                RegisterSuccess();
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.show(RegisterActivity.this,"已存在该用户");
                                    }
                                });
                            }

                        }
                    }).start();
                }
                else{
                    ToastUtil.show(RegisterActivity.this,"验证码错误");
                }
            }
        });
        btn_back_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                finish();
//                startActivity(intent);
            }
        });
        Yz_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReCode();
            }
        });
    }



    private void RegisterSuccess() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        User user = new User();

        user.account = et_Account.getText().toString();
        user.password = et_Password.getText().toString();
        user.RegisterTime = dateString;
        user.game1 ="未购买";
        user.game2 ="未购买";

        mDBHelper.insert(user);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show(RegisterActivity.this,"注册成功");
            }
        });

        //延迟返回登录界面
        new UniversalThread(new TreadData() {
            @Override
            public void onExecute() {
                //延迟返回登录界面，并向对应Activity发送信息
                Intent userData = new Intent();
                userData.putExtra("userName", et_Account.getText().toString());
                userData.putExtra("userPassword", et_Password.getText().toString());
                setResult(RESULT_OK,userData);
                finish();
            }
        }, 1000).StartThread();
    }

    private void initView() {
        Yz_code = findViewById(R.id.Yz_code);
        et_Account = findViewById(R.id.et_Account);
        et_Password = findViewById(R.id.et_Password);
        btn_Register = findViewById(R.id.btn_Register);
        ed_Code = findViewById(R.id.ed_Code);
        btn_back_Register = findViewById(R.id.btn_back);
    }

    private void initCode() {
        code = GetString.getString();
        Bitmap captchaImage = GetCode.getCode(code);
        Yz_code.setImageBitmap(captchaImage);
    }

    private void ReCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                code = GetString.getString();
                Bitmap captchaImage = GetCode.getCode(code);
                Yz_code.setImageBitmap(captchaImage);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mDBHelper.closeLink();
    }

}