package com.example.ex42.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ex42.database.enity.User;


public class ShoppingDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "GameShopping.db";
    private static ShoppingDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    // user信息表
    private static final String TABLE_USER_INFO = "users_info";

    private ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static ShoppingDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_INFO +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " account VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " RegisterTime VARCHAR,"+
                " game1 VARCHAR," +
                " game2 VARCHAR);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("account", user.account);
        values.put("password", user.password);
        values.put("RegisterTime", user.RegisterTime);
        values.put("game1", user.game1);
        values.put("game2",user.game2);

//        try {
//            mWDB.beginTransaction();//开启事务
//            mWDB.insert(TABLE_USER_INFO, null, values);
//            mWDB.setTransactionSuccessful();//到这里标记为事务运行成功
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mWDB.endTransaction();//提交事务
//        }

        return mWDB.insert(TABLE_USER_INFO,null,values);
//          return  1;
    }

    public User queryByAccount(String account){
        User user = null;
        String sql = "select * from" + TABLE_USER_INFO;
        Cursor cursor = mRDB.query(TABLE_USER_INFO, null, "account=?", new String[]{account}, null, null, null);
        if (cursor.moveToNext()) {
            user = new User();
            user.id = cursor.getInt(0);
            user.account = cursor.getString(1);
            user.password = cursor.getString(2);
            user.RegisterTime = cursor.getString(3);
            user.game1 = cursor.getString(4);
            user.game2 = cursor.getString(5);
        }
        return user;
    }

    public long update(User user) {
        ContentValues values = new ContentValues();
        values.put("account", user.account);
        values.put("password", user.password);
        values.put("RegisterTime", user.RegisterTime);
        values.put("game1", user.game1);
        values.put("game2", user.game2);
        return mWDB.update(TABLE_USER_INFO, values, "account=?", new String[]{user.account});
    }

}
