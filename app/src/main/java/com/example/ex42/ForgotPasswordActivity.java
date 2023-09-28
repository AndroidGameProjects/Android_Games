package com.example.ex42;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ex42.database.ShoppingDBHelper;
import com.example.ex42.database.enity.User;
import com.example.ex42.util.GetString;
import com.example.ex42.util.ToastUtil;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static Button btn_back_ForgotPassword;
    private ShoppingDBHelper mDBHelper;
    private EditText et_ForGot_Account;
    private EditText et_ForGot_Password;
    private EditText et_ForGot_Code;
    private Button btn_ForGot_update;
    private ImageView Yz_Forgot_code;
    private static String code;
    private static final String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        hideStatusBar(ForgotPasswordActivity.this);
        initView();
        initCode();
        initListener();

    }

    private void initCode() {
        code = GetString.getString();
        Bitmap captchaImage = GetCode.getCode(code);
        Yz_Forgot_code.setImageBitmap(captchaImage);
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

    private void initListener() {
        btn_back_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Yz_Forgot_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReCode();
            }
        });
        btn_ForGot_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_ForGot_Account.getText().toString().equals("") ||  et_ForGot_Password.getText().toString().equals("")){
                    ToastUtil.show(ForgotPasswordActivity.this,"请输入正确用户名和密码");
                    return;
                }
                String Input = et_ForGot_Code.getText().toString().toLowerCase();
                if (!Input.equals(code)){
                    ToastUtil.show(ForgotPasswordActivity.this,"验证码错误11");
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = mDBHelper.queryByAccount(et_ForGot_Account.getText().toString());
                            if (user == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.show(ForgotPasswordActivity.this,"该用户不存在");
                                    }
                                });
                            }else {
                                String InputPassword = et_ForGot_Password.getText().toString();
                                User userResult = new User(et_ForGot_Account.getText().toString(),
                                        InputPassword,
                                        user.RegisterTime,
                                        user.game1,
                                        user.game2);
                                if (mDBHelper.update(userResult) > 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.show(ForgotPasswordActivity.this, "修改成功");
                                        }
                                    });
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void ReCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                code = GetString.getString();
                Bitmap captchaImage = GetCode.getCode(code);
                Yz_Forgot_code.setImageBitmap(captchaImage);
            }
        }).start();
    }

    private void initView() {
        Yz_Forgot_code = findViewById(R.id.Yz_Forgot_code);
        btn_ForGot_update = findViewById(R.id.btn_ForGot_update);
        et_ForGot_Code = findViewById(R.id.et_ForGot_Code);
        et_ForGot_Password = findViewById(R.id.et_ForGot_Password);
        et_ForGot_Account = findViewById(R.id.et_ForGot_Account);
        btn_back_ForgotPassword = findViewById(R.id.btn_back);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mDBHelper.closeLink();
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