package com.example.ex42.OverWrite;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex42.Game1.Game1_Enter;
import com.example.ex42.Game2.Game2_Enter;
import com.example.ex42.R;
import com.example.ex42.database.enity.Game;
import com.example.ex42.database.enity.User;

import java.util.List;

public class GameBaseAdapt extends BaseAdapter {

    private Context mContext;
    private List<Game>mGame;
    private User mUser;

    public GameBaseAdapt(Context context, List<Game> mGame, User user) {
        this.mContext = context;
        this.mGame = mGame;
        this.mUser = user;
    }

    @Override
    public int getCount() {
        return mGame.size();
    }

    @Override
    public Object getItem(int position) {
        return mGame.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("GameBaseAdapt", mUser.toString());
        ViewHolder holder;
        if (convertView == null) {
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.game_item, null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.imageView);
            holder.tv_name = convertView.findViewById(R.id.name);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控制设置好数据
        final Game game = mGame.get(position);
        holder.iv_icon.setImageResource(game.Image);
        holder.tv_name.setText(game.name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    // 点击第一个条目，跳转到A
                    Intent intent = new Intent(mContext, Game1_Enter.class);
                    intent.putExtra("user", mUser);
                    mContext.startActivity(intent);
                } else if (position == 1) {
                    // 点击第二个条目，跳转到B
                    Intent intent = new Intent(mContext, Game2_Enter.class);
                    intent.putExtra("user", mUser);
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }



    public final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
    }




}
