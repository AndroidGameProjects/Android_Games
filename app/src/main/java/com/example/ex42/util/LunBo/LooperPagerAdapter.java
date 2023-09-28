package com.example.ex42.util.LunBo;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

//右键方法名，generate，override
public class LooperPagerAdapter extends PagerAdapter {

    private List<Integer> mPics = null;

    public void setData(List<Integer> colors){
        this.mPics = colors;
    }

    @Override
    public int getCount() {
        if (mPics != null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @NonNull
    @Override//初始化,主要修改这里
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position % mPics.size();
        ImageView imageView = new ImageView(container.getContext());
        //让图片拉伸，和控件一样大
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setBackgroundColor(mColors.get(position));
        imageView.setImageResource(mPics.get(realPosition));
        //设置完数据添加到容器
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public int getDataRealSize(){
        if (mPics != null){
            return mPics.size();
        }
        return 0;
    }

}