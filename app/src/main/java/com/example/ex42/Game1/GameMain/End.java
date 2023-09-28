package com.example.ex42.Game1.GameMain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ex42.R;

public class End extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Button returnMainMenu = findViewById(R.id.End_ReturnMainMenu);
        Button quitGame = findViewById(R.id.End_QuitGame);

        returnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO：返回主菜单
                End.this.finish();
            }
        });

        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO：退出游戏
//                GameList.instance.finish();
                End.this.finish();
//                System.exit(0);
            }
        });
    }
}