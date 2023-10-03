package com.example.ex42.Pay.demo.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ex42.OverWrite.ListViewActivity;
import com.example.ex42.database.enity.User;
import com.example.ex42.R;
import com.example.ex42.util.HideStateBar;

public class GameMainActivity extends AppCompatActivity {
    public static GameMainActivity instance;
    private static final String TAG = "GameMainActivity";
    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStateBar h1 = new HideStateBar();
        h1.hideStatusBar(this);
        setContentView(R.layout.activity_game_main);
        instance = this;
        Button button = findViewById(R.id.Game_Start);
        user = (User) getIntent().getSerializableExtra("user");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GameMainActivity.this, ListViewActivity.class);////////重写

                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }


}