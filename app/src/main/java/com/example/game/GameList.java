package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GameList extends AppCompatActivity {
    private static final String TAG = "GameList";
    public static GameList instance;
    private ListView listView = null;
    private String[] names = null;
    private String[] infos = null;

    private List<GameListItem> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        instance = this;

        InitData();
        listView = findViewById(R.id.Game_List);

        ListAdapter listAdapter = new ListAdapter(GameList.this, R.layout.game_list_item, gameList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Intent intent = new Intent(GameList.this, Game.class);
                    MainActivity.instance.finish();
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(GameList.this, ElfkfMainActivity.class);
                    MainActivity.instance.finish();
                    startActivity(intent);
                }

            }
        });
    }

    void InitData(){
        names = getResources().getStringArray(R.array.gameName);
        infos = getResources().getStringArray(R.array.gameInfo);
        TypedArray images = getResources().obtainTypedArray(R.array.gameList_Image);

        int len = names.length;

        for(int i = 0; i < len; i ++){
            GameListItem item = new GameListItem();

            item.SetItemId(i);
            item.SetImageId(images.getResourceId(i,0));
            Log.d("ListAdapter", String.valueOf(images.getResourceId(i,0)));
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