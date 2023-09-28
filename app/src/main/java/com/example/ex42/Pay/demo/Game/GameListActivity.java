package com.example.ex42.Pay.demo.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ex42.MainActivity;
import com.example.ex42.database.enity.GameListItem;
import com.example.ex42.database.enity.User;
import com.example.ex42.R;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity {
    private static final String TAG = "GameList";
    private static ListView listView;
    private List<GameListItem> gameList = new ArrayList<>();
    private String[] names = null;
    private String[] infos = null;
    User user = null;

    public Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            String type = (String) msg.obj;
            if (type == null){
                return;
            }
            if (type.equals("invalidate")){
                //刷新重绘view
                listView.invalidate();
            } else if (type.equals("pause")) {

            } else if (type.equals("guideLine")) {

            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        user = (User) getIntent().getSerializableExtra("user");
        Log.d(TAG, user.toString());
        initView();
        InitData();
    }



    private void initView() {
        listView = findViewById(R.id.Game_List);
        ListAdapter listAdapter = new ListAdapter(GameListActivity.this, R.layout.game_list_item, gameList,user);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Intent intent = new Intent(GameListActivity.this, MainActivity.class);
                    GameMainActivity.instance.finish();
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(GameListActivity.this, MainActivity.class);
                    GameMainActivity.instance.finish();
                    startActivity(intent);
                }

            }
        });
    }


    void InitData() {
        names = getResources().getStringArray(R.array.gameName);
        infos = getResources().getStringArray(R.array.gameInfo);
        TypedArray images = getResources().obtainTypedArray(R.array.gameList_Image);

        int len = names.length;

        for (int i = 0; i < len; i++) {
            GameListItem item = new GameListItem();

            item.SetItemId(i);
            item.SetImageId(images.getResourceId(i, 0));
            Log.d("ListAdapter", String.valueOf(images.getResourceId(i, 0)));
            Log.d("ListAdapter", String.valueOf(item.GetImageId()));
            item.SetGameName(names[i]);
            item.SetGameInfo(infos[i]);

            gameList.add(item);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////
        String[] newNames = getResources().getStringArray(R.array.gameName2);
        String[] newInfos = getResources().getStringArray(R.array.gameInfo2);
        TypedArray newImages = getResources().obtainTypedArray(R.array.gameList_Image2);

        int newLen = newNames.length;
        for(int i = 0; i < newLen; i ++){
            GameListItem newItem = new GameListItem();
            newItem.SetItemId(len + i);
            newItem.SetImageId(newImages.getResourceId(i,0));
            newItem.SetGameName(newNames[i]);
            newItem.SetGameInfo(newInfos[i]);
            gameList.add(newItem);
        }

    }
}