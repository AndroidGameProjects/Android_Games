package com.example.ex42.Game1.GameMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex42.R;
import com.example.ex42.database.enity.RankInfo;

import java.util.List;

public class RankAdapter extends BaseAdapter {

    private Context mContext;
    private List<RankInfo>mRankInfos;

    public RankAdapter(Context context, List<RankInfo> rankInfos) {
        mContext = context;
        mRankInfos = rankInfos;
    }

    @Override
    public int getCount() {
        return mRankInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mRankInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //使用convertView，比如到最后一个了会将第一个置为下一个。
        ViewHolder holder;
        if (convertView == null){
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.rank_info_item, null);
            holder = new ViewHolder();
            holder.account = convertView.findViewById(R.id.nameTextView);
            holder.OverTime = convertView.findViewById(R.id.timeTextView);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控制设置好数据
        RankInfo rankInfo = mRankInfos.get(position);
        holder.account.setText("第" +Integer.valueOf(position + 1) +  ":  "+rankInfo.account);
        holder.OverTime.setText("通关时间:  " + rankInfo.OverTime);

        return convertView;
    }

    public final class ViewHolder {
        public TextView account;
        public TextView OverTime;
    }

}
