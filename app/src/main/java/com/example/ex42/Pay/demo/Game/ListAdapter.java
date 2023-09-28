package com.example.ex42.Pay.demo.Game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ex42.Help;
import com.example.ex42.database.enity.GameListItem;
import com.example.ex42.database.enity.User;
import com.example.ex42.R;
import com.example.ex42.util.ToastUtil;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    Handler handler = new Handler();;
    private static final String TAG = "ListAdapter";
    User user = null;
    Context context;
    int resourceId;
    List<GameListItem> data;
    private ImageView listItem_image;
    private TextView listItem_gameName;
    private TextView listItem_info;
    private Button listItem_button;


    private String gama1 = "";
    private String gama2 = "";

    public ListAdapter(@NonNull Context context, int resource, List<GameListItem> data, User user) {
        super(context, resource, data);
        this.context = context;
        this.resourceId = resource;
        this.data = data;
        this.user = user;
        this.gama1 = user.game1;
        this.gama2 = user.game2;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GameListItem gameListItem = (GameListItem) getItem(position);
        View view;

        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        listItem_image = view.findViewById(R.id.ListItem_Image);
        listItem_gameName = view.findViewById(R.id.ListItem_GameName);
        listItem_info = view.findViewById(R.id.ListItem_Info);
        listItem_button = view.findViewById(R.id.ListItem_Button);/////////////////////按钮

        listItem_image.setImageResource(gameListItem.GetImageId());
        listItem_gameName.setText(gameListItem.GetGameName());
        listItem_info.setText(gameListItem.GetGameInfo());

        // 根据字符串1和字符串2设置按钮的文字
        if (gameListItem.GetGameName().equals("XXOO")) {
            if (user.game1.equals("未购买")){
                listItem_button.setText("未购买");
            }else {
                listItem_button.setText("已购买");
            }
        } else if (gameListItem.GetGameName().equals("俄罗斯方块")) {
            if (user.game2.equals("未购买")){
                listItem_button.setText("未购买");
            }else {
                listItem_button.setText("已购买");
            }
        }
        listItem_button.setTag(position);
        listItem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (int) v.getTag();
                GameListItem clickedGameListItem = (GameListItem) getItem(clickedPosition);

                if (clickedGameListItem != null) {
                    if (clickedGameListItem.GetGameName().equals("XXOO")) {
                        if (user.game1.equals("未购买")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListAdapter.this.getContext());
                            builder.setTitle("请确认");
                            builder.setMessage("您未购买游戏，是否购买游戏");
                            builder.setPositiveButton("购买", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Help h1 = new Help(user, context, clickedPosition);
                                    h1.buy1();
                                    // 更新按钮所在项的文本
                                    Message message = handler.obtainMessage();
                                    message.obj = "invalidate";
                                    handler.sendMessage(message);
                                }
                            });
                            builder.setNegativeButton("不购买", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToastUtil.show(ListAdapter.this.getContext(), "不购买");
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            ToastUtil.show(ListAdapter.this.getContext(), "已购买");
                        }
                    } else if (clickedGameListItem.GetGameName().equals("俄罗斯方块")) {
                        if (user.game2.equals("未购买")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListAdapter.this.getContext());
                            builder.setTitle("请确认");
                            builder.setMessage("您未购买游戏，是否购买游戏");
                            builder.setPositiveButton("购买", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Help h1 = new Help(user, context, clickedPosition);
                                    h1.buy1();
                                    // 更新按钮所在项的文本
                                    Message message = handler.obtainMessage();
                                    message.obj = "invalidate";
                                    handler.sendMessage(message);
                                }
                            });
                            builder.setNegativeButton("不购买", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToastUtil.show(ListAdapter.this.getContext(), "不购买");
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            ToastUtil.show(ListAdapter.this.getContext(), "已购买");
                        }
                    }
                }
            }
        });

        return view;
    }


}
