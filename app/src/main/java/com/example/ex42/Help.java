package com.example.ex42;

import android.content.Context;
import android.util.Log;

import com.example.ex42.database.ShoppingDBHelper;
import com.example.ex42.database.enity.User;
import com.example.ex42.util.ToastUtil;

public class Help {

    private static final String  TAG = "Help";
    private User user;
    private ShoppingDBHelper mDBHelper;
    private Context context;
    private int position;

    public Help(User user, Context context, int position) {
        this.user = user;
        this.position = position;
        this.context = context;
        initDate();
    }

    private void initDate() {
        mDBHelper = ShoppingDBHelper.getInstance(context);
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();
    }

    public void buy1(){
        Log.d(TAG, "Help");;
        if (position == 0){
            user.game1 = "已购买";
            if (mDBHelper.update(user) > 0) {
                ToastUtil.show(context,"购买成功");
            }
        } else if (position == 1) {
            user.game2 = "已购买";
            if (mDBHelper.update(user) > 0) {
                ToastUtil.show(context,"购买成功");
            }
        }

    }

}
