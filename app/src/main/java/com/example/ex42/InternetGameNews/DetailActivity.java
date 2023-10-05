package com.example.ex42.InternetGameNews;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ex42.R;
import com.example.ex42.database.enity.User;

public class DetailActivity extends AppCompatActivity {
    private  Button btn_back;
    private TextView tvTitle;
    private TextView tvSource;
    private ImageView ivImage;
    private ImageView ivDelete;
    private TextView tvPublishTime;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvTitle = findViewById(R.id.tv_title);
        tvSource = findViewById(R.id.tv_subtitle);
        ivImage = findViewById(R.id.iv_image);
        ivDelete = findViewById(R.id.iv_delete);
        tvPublishTime = findViewById(R.id.tv_publish_time);
        btn_back = findViewById(R.id.btn_back);

        News news = (News) getIntent().getSerializableExtra("news");
        tvTitle.setText(news.getTitle());
        tvSource.setText("       " + news.getSource());
//        ivDelete.setTag(position);
        tvPublishTime.setText(news.getDate());
        Glide.with(mContext).load(news.getPicUrl()).into(ivImage);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}