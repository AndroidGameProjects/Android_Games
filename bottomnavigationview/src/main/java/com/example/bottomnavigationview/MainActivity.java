package com.example.bottomnavigationview;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.main_vp);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv);

        initData();

        MainActivityAdapter adapter = new MainActivityAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
//        页面更改监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.menu_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.menu_service);
                        break;
//                    case 2:
//                        bottomNavigationView.setSelectedItemId(R.id.menu_rich);
//                        break;
//                    case 3:
//                        bottomNavigationView.setSelectedItemId(R.id.menu_news);
//                        break;
//                    case 4:
//                        bottomNavigationView.setSelectedItemId(R.id.menu_mine);
//                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        图标选择监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home){
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.menu_service) {
                    viewPager.setCurrentItem(1);
                }


//                switch (item.getItemId()){
//                    case R.id.menu_home:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.menu_service:
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case R.id.menu_rich:
//                        viewPager.setCurrentItem(2);
//                        break;
//                    case R.id.menu_news:
//                        viewPager.setCurrentItem(3);
//                        break;
//                    case R.id.menu_mine:
//                        viewPager.setCurrentItem(4);
//                        break;
//                }
                return true;
            }
        });
    }

    private void initData() {
        HomeFragment homeFragment = HomeFragment.newInstance("首页", "");
        fragmentList.add(homeFragment);
        ServiceFragment serviceFragment = ServiceFragment.newInstance("全部服务", "");
        fragmentList.add(serviceFragment);
//        RichFragment richFragment = RichFragment.newInstance("精准扶贫", "");
//        fragmentList.add(richFragment);
//        NewsFragment newsFragment = NewsFragment.newInstance("新闻", "");
//        fragmentList.add(newsFragment);
//
//        MineFragment mineFragment = MineFragment.newInstance("个人中心", "");
//        fragmentList.add(mineFragment);
    }


}

