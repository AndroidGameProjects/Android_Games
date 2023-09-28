package com.example.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class NumItem{
    int xNum = 0;
    int oNum = 0;

    public int GetXNum(){
        return this.xNum;
    }

    public int GetONum(){
        return this.oNum;
    }

    public void SetXNum(int num){
        this.xNum += num;
    }

    public void SetONum(int num){
        this.oNum += num;
    }
}

public class Game extends AppCompatActivity {
    public static Game instance;
    private GridView game_GridButton;

    private List<NumItem> rowNum = new ArrayList<>();
    private List<NumItem> columnNum = new ArrayList<>();
    private List<Icon> iconList = new ArrayList<>();
    private OriginState originState = new OriginState();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Clock", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Clock", "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        instance = this;

        Intent intent = new Intent(Game.this, Clock.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        startService(intent);

        Button quitButton = findViewById(R.id.Game_Quit_Button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.this.finish();
            }
        });

        InitData();

        IconAdapter iconAdapter = new IconAdapter(this, R.layout.item_grid_icon, iconList);
        game_GridButton = findViewById(R.id.Game_GridButton);

        //TODO:在执行时闪退
        game_GridButton.setAdapter(iconAdapter);

        game_GridButton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Icon icon = iconList.get(i);
                if(icon.GetCanClick()){
                    ImageView imageView = view.findViewById(R.id.item_img);
                    switch ((String)imageView.getTag()){
                        case "itemImage_null":
                            imageView.setImageResource(R.drawable.itemimage_x);
                            icon.SetIconId(1);
                            imageView.setTag("itemImage_x");
                            UpdateUI(i, 0);
                            break;
                        case "itemImage_x":
                            imageView.setImageResource(R.drawable.itemimage_o);
                            imageView.setTag("itemImage_o");
                            icon.SetIconId(2);
                            UpdateUI(i, 1);
                            break;
                        case "itemImage_o":
                            imageView.setImageResource(R.drawable.itemimage_null);
                            imageView.setTag("itemImage_null");
                            icon.SetIconId(0);
                            UpdateUI(i, 2);
                            break;
                    }
                }

                //TODO:将数据传进 IsWin() 函数
                boolean isWin = IsWin(i);

                if(isWin){
                    unbindService(serviceConnection);
                    stopService(new Intent(Game.this, Clock.class));
                    Intent intent = new Intent(Game.this, End.class);
                    startActivity(intent);
                    Game.this.finish();
                }
                else Log.d("Game", "请继续解开谜题！");
            }
        });
    }

    public void InitData(){
        TypedArray images = getResources().obtainTypedArray(R.array.images);
        int[][] intArray = originState.GetState();

        for(int i = 0; i < 8; i ++){
            NumItem rowItem = new NumItem();
            NumItem columnItem = new NumItem();

            rowNum.add(rowItem);
            columnNum.add(columnItem);
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++)
            {
                Icon icon = new Icon();

                //TODO:Set ImageId
                icon.SetIconId(intArray[i][j]);
                icon.SetImageId(images.getResourceId(intArray[i][j], 0));

                if(intArray[i][j] != 0){
                    icon.SetIsRight(true);
                    icon.SetCanClick(false);

                    switch (intArray[i][j]){
                        case 1:
                            rowNum.get(i).SetXNum(1);
                            columnNum.get(j).SetXNum(1);
                            break;
                        case 2:
                            rowNum.get(i).SetONum(1);
                            columnNum.get(j).SetONum(1);
                            break;
                    }
                }
                else{
                    icon.SetCanClick(true);
                }
                iconList.add(icon);
            }
        }

        UpdateAllItem();
        Log.d("GridView", "数组长度为" + String.valueOf(iconList.size()));
    }

    public boolean IsWin(int position){
//        //TODO:判断是否胜利
//        Log.d("GridView", "判断是否胜利");
//
//        Icon frontIcon, behindIcon = null;
//        Icon icon = iconList.get(position);
//
//        //判断水平方向
//        if(position % 8 != 0 && position % 8 != 7){
//            frontIcon = iconList.get(position - 1);
//            behindIcon = iconList.get(position + 1);
//
//            int iconId = icon.GetIconId();
//            int frontId = frontIcon.GetIconId();
//            int behindId = behindIcon.GetIconId();
//
//            if(frontId != 0 && behindId != 0 && iconId != 0){
//                if(iconId == frontId && iconId != behindId || iconId != frontId && iconId == behindId){
//                    icon.SetIsRight(true);
//                    SetChildRight(position - 1);
//                    SetChildRight(position + 1);
//                }
//                else icon.SetIsRight(false);
//            }
//            else{
//                icon.SetIsRight(false);
//            }
//        }
//        else if(icon.GetIconId() != 0){
//            icon.SetIsRight(true);
//            if(position % 8 == 0){
//                SetChildRight(position + 1);
//            }
//            else if(position % 8 == 7){
//                SetChildRight(position - 1);
//            }
//        }
//        else icon.SetIsRight(false);
//
//        //判断竖直方向
//        if(position > 7 && position < 56){
//            frontIcon = iconList.get(position - 8);
//            behindIcon = iconList.get(position + 8);
//
//            int iconId = icon.GetIconId();
//            int frontId = frontIcon.GetIconId();
//            int behindId = behindIcon.GetIconId();
//
//            if(frontId != 0 && behindId != 0 && iconId != 0){
//                if(iconId == frontId && iconId != behindId || iconId != frontId && iconId == behindId){
//                    icon.SetIsRight(true);
//                    SetChildRight(position - 8);
//                    SetChildRight(position + 8);
//                }
//                else icon.SetIsRight(false);
//            }
//            else{
//                icon.SetIsRight(false);
//            }
//        }
//        else if(icon.GetIconId() != 0){
//            icon.SetIsRight(true);
//            if(position <= 7){
//                SetChildRight(position + 8);
//            }
//            else if(position >= 56){
//                SetChildRight(position - 8);
//            }
//        }
//        else icon.SetIsRight(false);
//
//        for(int i = 0; i < 8; i++){
//            int num_x = 0, num_o = 0;
//            for(int j = 0; j < 8; j++){
//                int tmp = i * 8 + j;
//                Icon tmpIcon = iconList.get(tmp);
//
//                //有不对的立刻返回 false
//                if(!tmpIcon.GetIsRight()) return false;
//
//                if(tmpIcon.GetIconId() == 1){
//                    num_x += 1;
//                }
//                else if(tmpIcon.GetIconId() == 2){
//                    num_o += 1;
//                }
//            }
//            if(num_o != num_x) return false;
//        }
        int point = 0;
        int[][] answer = originState.GetAnswer();

        for(int i = 0; i < 8; i ++){
            for(int j = 0; j < 8; j ++){
                if(iconList.get(point).GetIconId() != answer[i][j])
                    return false;
                point += 1;
            }
        }

        return true;
    }

    public void SetChildRight(int position){
        Icon frontIcon, behindIcon = null;
        Icon icon = iconList.get(position);

        if(position % 8 != 0 && position % 8 != 7){
            frontIcon = iconList.get(position - 1);
            behindIcon = iconList.get(position + 1);

            int iconId = icon.GetIconId();
            int frontId = frontIcon.GetIconId();
            int behindId = behindIcon.GetIconId();

            if(frontId != 0 && behindId != 0 && iconId != 0){
                if(iconId == frontId && iconId != behindId || iconId != frontId && iconId == behindId){
                    icon.SetIsRight(true);
                }
            }
            else{
                icon.SetIsRight(false);
            }
        }
        else if(icon.GetIconId() != 0){
            icon.SetIsRight(true);
        }
        else icon.SetIsRight(false);

        //判断竖直方向
        if(position > 7 && position < 56){
            frontIcon = iconList.get(position - 8);
            behindIcon = iconList.get(position + 8);

            int iconId = icon.GetIconId();
            int frontId = frontIcon.GetIconId();
            int behindId = behindIcon.GetIconId();

            if(frontId != 0 && behindId != 0 && iconId != 0){
                if(iconId == frontId && iconId != behindId || iconId != frontId && iconId == behindId){
                    icon.SetIsRight(true);
                }
            }
            else{
                icon.SetIsRight(false);
            }
        }
        else if(icon.GetIconId() != 0){
            icon.SetIsRight(true);
        }
        else icon.SetIsRight(false);
    }

    //更新各个组件的显示
    private void UpdateUI(int position, int system){

        int row = position / 8;
        int column = position % 8;

        //更新行列数据
        switch (system){
            case 0:
                //从Null变成X，需要将X数量+1
                rowNum.get(row).SetXNum(1);
                columnNum.get(column).SetXNum(1);
                break;
            case 1:
                //从X变成O，需要将X数量-1，O数量+1
                rowNum.get(row).SetXNum(-1);
                columnNum.get(column).SetXNum(-1);
                rowNum.get(row).SetONum(1);
                columnNum.get(column).SetONum(1);
                break;
            case 2:
                //从O变成Null,需要将O数量-1
                rowNum.get(row).SetONum(-1);
                columnNum.get(column).SetONum(-1);
                break;
        }

        UpdateOneItem(row);
        UpdateOneItem(column);
    }

    private void UpdateAllItem(){
        for(int i = 0; i < 8; i ++){
            UpdateOneItem(i);
        }
    }
    private void UpdateOneItem(int i){
        NumItem rowItem = rowNum.get(i);
        NumItem columnItem = columnNum.get(i);
        switch (i){
            case 0:
                SetText(findViewById(R.id.Left_TextView1), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView1), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 1:
                SetText(findViewById(R.id.Left_TextView2), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView2), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 2:
                SetText(findViewById(R.id.Left_TextView3), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView3), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 3:
                SetText(findViewById(R.id.Left_TextView4), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView4), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 4:
                SetText(findViewById(R.id.Left_TextView5), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView5), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 5:
                SetText(findViewById(R.id.Left_TextView6), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView6), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 6:
                SetText(findViewById(R.id.Left_TextView7), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView7), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
            case 7:
                SetText(findViewById(R.id.Left_TextView8), String.valueOf(rowItem.xNum) + "/" + String.valueOf(rowItem.oNum));
                SetText(findViewById(R.id.Up_TextView8), String.valueOf(columnItem.xNum) + "/" + String.valueOf(columnItem.oNum));
                break;
        }
    }

    private void SetText(TextView textView, String text){
        textView.setText(text);
    }

    public void SetTimeView(Intent intent){
        TextView textView = findViewById(R.id.Game_Time);
        textView.setText(String.valueOf(intent.getIntExtra("time", 0)));
    }
}