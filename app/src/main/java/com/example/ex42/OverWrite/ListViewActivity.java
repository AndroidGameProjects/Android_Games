package com.example.ex42.OverWrite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ex42.R;
import com.example.ex42.database.enity.Game;
import com.example.ex42.database.enity.User;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Game> gameList;
    private CheckBox ck_diviver;
    private CheckBox ck_selector;
    private ListView lv_game;
    private User mUser ;
    private TextView et_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mUser = (User) getIntent().getSerializableExtra("user");
        lv_game = findViewById(R.id.lv_game);
        gameList = Game.getDefaultList();
        GameBaseAdapt adapter = new GameBaseAdapt(this,gameList,mUser);
        lv_game.setAdapter(adapter);
        lv_game.setOnItemClickListener(this);


        initView();
        initDate();
    }

    private void initDate() {
        et_user_id.setText(mUser.account + "   你好\n\t\t\t\t目前该软件有以下游戏");
    }

    private void initView() {
        et_user_id = findViewById(R.id.et_user_id);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


}