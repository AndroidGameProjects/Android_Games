package com.example.ex42.InternetGameNews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ex42.R;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    private List<News> mNewsData;
    private Context mContext;
    private int resourcedId;


    public NewsAdapter(@NonNull Context context, int resourceId, @NonNull List<News> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourcedId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        View view;
        final ViewHolder vh;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourcedId , parent , false);
            vh = new ViewHolder();
            vh.tvTitle = view.findViewById(R.id.tv_title);
            vh.tvSource = view.findViewById(R.id.tv_subtitle);
            vh.ivImage = view.findViewById(R.id.iv_image);
            vh.ivDelete = view.findViewById(R.id.iv_delete);
            vh.tvPublishTime = view.findViewById(R.id.tv_publish_time);

            view.setTag(vh);
        }else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

        vh.tvTitle.setText(news.getTitle());
        vh.tvSource.setText(news.getSource());
        vh.ivDelete.setTag(position);
        vh.tvPublishTime.setText(news.getDate());

        Glide.with(mContext).load(news.getPicUrl()).into(vh.ivImage);
        Log.d("testtest2", news.toString());
        return view;
    }

    class ViewHolder{
        TextView tvTitle;
        TextView tvSource;
        ImageView ivImage;
        TextView tvPublishTime;
        ImageView ivDelete;
    }

}
















