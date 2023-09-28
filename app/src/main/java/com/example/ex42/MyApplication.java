package com.example.ex42;

import android.app.Application;

import com.example.ex42.database.ShoppingDBHelper;
import com.example.ex42.util.SharedUtil;

public class MyApplication extends Application {

    private static MyApplication mApp;

    public static MyApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initInfo();
    }

    private void initInfo() {
        boolean isFirst = SharedUtil.getInstance(this).readBoolean("first", true);
        // 打开数据库，把商品信息插入到表中
        ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
        dbHelper.openWriteLink();
//        dbHelper.insertGoodsInfos(list);
        dbHelper.closeLink();
        // 把是否首次打开写入共享参数
        SharedUtil.getInstance(this).writeBoolean("first", false);
    }


}
