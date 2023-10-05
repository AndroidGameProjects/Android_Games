package com.example.ex42.OverWrite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex42.Game1.Game1_Enter;
import com.example.ex42.Game2.Game2_Enter;
import com.example.ex42.InternetGameNews.Maintivitytest;
import com.example.ex42.R;
import com.example.ex42.database.enity.Game;
import com.example.ex42.database.enity.User;
import com.example.ex42.util.HideStateBar;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SearchView searchView;
    //定义自动完成的列表
    private final String[] mStrings = {"OOXX","俄罗斯方块"};
    private List<Game> gameList;
    private boolean isListView2Visible = false;
    private ListView lv_game;
    private User mUser ;
    private TextView et_user_id;
    private ArrayAdapter mAdapterSear;
    private ListView listView2;
    private Button btn_Internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStateBar h1 = new HideStateBar();
        h1.hideStatusBar(this);
        setContentView(R.layout.activity_list_view);
        initView();

        listView2 = (ListView) findViewById(R.id.lv2);
        mAdapterSear = new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,mStrings);
        listView2.setAdapter(mAdapterSear);
        //为ListView启动过滤
        listView2.setTextFilterEnabled(true);
        searchView = (SearchView) findViewById(R.id.sv);
        //设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("输入您想查找的游戏");
        listView2.setVisibility(View.GONE);
        initSearchLinestener();

        mUser = (User) getIntent().getSerializableExtra("user");

        gameList = Game.getDefaultList();
        GameBaseAdapt adapter = new GameBaseAdapt(this,gameList,mUser);
        lv_game.setAdapter(adapter);
        lv_game.setOnItemClickListener(this);


        initDate();
    }

    private void initSearchLinestener() {
        //配置监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
                Toast.makeText(ListViewActivity.this,"you choose:" + query, Toast.LENGTH_SHORT).show();
                if (query.equals("OOXX")){
                    Intent intent = new Intent(ListViewActivity.this, Game1_Enter.class);
                    intent.putExtra("user", mUser);
                    startActivity(intent);
                } else if (query.equals("俄罗斯方块")) {
                    Intent intent = new Intent(ListViewActivity.this, Game2_Enter.class);
                    intent.putExtra("user", mUser);
                    startActivity(intent);
                }
                return false;
            }
            // 搜索框文本变化时触发
            @Override
            public boolean onQueryTextChange(String newText) {
                // 将输入的文字转换为小写形式
                listView2.setVisibility(View.VISIBLE);
                String queryText = newText.toLowerCase();
                if (!TextUtils.isEmpty(queryText)) {
                    List<String> filteredStrings = new ArrayList<>();
                    // 对mStrings数组进行遍历，只要搜索框输入的文字（忽略大小写）与任意一个字符串（忽略大小写）匹配就添加到filteredStrings中
                    for (String string : mStrings) {
                        if (string.toLowerCase().contains(queryText)) {
                            filteredStrings.add(string);
                        }
                    }
                    // 使用过滤后的结果更新适配器
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_1, filteredStrings);
                    listView2.setAdapter(adapter);
                } else {
                    // 如果搜索框为空，则显示全部数据
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_1, mStrings);
                    listView2.setAdapter(adapter);
                }
                return true;
            }
        });
        listView2.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<? > parent, View view, int position, long id) {
                Object string = mAdapterSear.getItem(position);
                searchView.setQuery(string.toString(),true);
            }
        });
        btn_Internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListViewActivity.this, Maintivitytest.class);
                startActivity(intent);
            }
        });

    }

    private void initDate() {
        et_user_id.setText(mUser.account + ",你好\n\t\t\t\t目前该软件有以下游戏");
    }

    private void initView() {
        lv_game = findViewById(R.id.lv_game);
        et_user_id = findViewById(R.id.et_user_id);
        btn_Internet = findViewById(R.id.btn_Internet);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}