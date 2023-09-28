package com.example.ex42.Pay.demo.Game;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.ex42.database.enity.Icon;
import com.example.ex42.R;

import java.util.List;

public class IconAdapter extends BaseAdapter {

    private Context context;
    private int resourceId;
    private List<Icon> iconList;
    private LayoutInflater inflater;

    public IconAdapter(Context context, int resource, List<Icon> iconList) {
        this.context = context;
        this.resourceId = resource;
        this.iconList = iconList;
        Log.d("GridView", String.valueOf(iconList.get(10)));
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.iconList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.iconList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("IconAdapter", "Call Back");
        Icon icon = (Icon)getItem(position);

        convertView = inflater.inflate(R.layout.item_grid_icon, null);
        ImageView imageView = convertView.findViewById(R.id.item_img);
//        imageView.setClickable(false);

        int tmp = icon.GetIconId();
        if(tmp > 0){
            imageView.setImageResource(icon.GetImageId());
            SetImageTag(imageView, tmp);
        }
        else{
            imageView.setImageResource(0);
            SetImageTag(imageView, 0);
        }

//        if(icon.GetCanClick()){
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ImageView imageView = view.findViewById(R.id.item_img);
//                    switch ((String)imageView.getTag()){
//                        case "itemImage_null":
//                            imageView.setImageResource(R.drawable.itemimage_x);
//                            imageView.setTag("itemImage_x");
//                            break;
//                        case "itemImage_x":
//                            imageView.setImageResource(R.drawable.itemimage_o);
//                            imageView.setTag("itemImage_o");
//                            break;
//                        case "itemImage_o":
//                            imageView.setImageResource(R.drawable.itemimage_null);
//                            imageView.setTag("itemImage_null");
//                            break;
//                    }
//                }
//            });
//        }
        return convertView;
    }

    private void SetImageTag(ImageView imageView, int num){
        switch (num){
            case 0: imageView.setTag("itemImage_null");break;
            case 1: imageView.setTag("itemImage_x");break;
            case 2: imageView.setTag("itemImage_o");break;
        }
    }
}
