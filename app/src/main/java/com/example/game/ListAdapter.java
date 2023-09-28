package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {

    Context context;
    int resourceId;
    List<GameListItem> data;

    public ListAdapter(@NonNull Context context, int resource, List<GameListItem> data) {
        super(context, resource, data);
        this.context = context;
        this.resourceId = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GameListItem gameListItem = (GameListItem) getItem(position);
        View view;

        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        ImageView imageView = view.findViewById(R.id.ListItem_Image);
        TextView gameName = view.findViewById(R.id.ListItem_GameName);
        TextView gameInfo = view.findViewById(R.id.ListItem_Info);

        Log.d("ListAdapter", String.valueOf(gameListItem.GetImageId()));
        imageView.setImageResource(gameListItem.GetImageId());
        gameName.setText(gameListItem.GetGameName());
        gameInfo.setText(gameListItem.GetGameInfo());

        return view;
    }
}
