package com.example.ex42.Game2.GameMain.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.ex42.Game2.GameMain.Config;



public class MapsModel {

    //地图画笔
    Paint mapPaint;
    //辅助线画笔
    Paint linePaint;
    //状态画笔
    Paint statePaint;

    //地图
    public boolean[][] maps;
    //地图宽度
    int xWidth;
    //地图高度
    int yHeight;
    //方块大小
    int boxsize;

    public MapsModel(int xWidth, int yHeight,int boxsize){
        this.boxsize = boxsize;
        this.xWidth = xWidth;
        this.yHeight = yHeight;
        //初始化地图画笔
        mapPaint = new Paint();
        mapPaint.setColor(Config.MAP_BOX_COLOR);
        mapPaint.setAntiAlias(true);
        //初始化辅助线画笔
        linePaint = new Paint();
        linePaint.setColor(Config.GUIDE_LINE_COLOR);
        linePaint.setAntiAlias(true);
        //初始化状态画笔
        statePaint = new Paint();
        statePaint.setColor(Config.STATE_COLOR);
        statePaint.setAntiAlias(true);
        statePaint.setTextSize(100);

        //初始化地图
        maps = new boolean[Config.MAPX][Config.MAPY];
    }



    public void drawMaps(Canvas canvas) {
        //绘制地图
        for(int x = 0;x < maps.length ; x++){
            for(int y = 0; y < maps[x].length;y++) {
                if (maps[x][y] == true)
                    canvas.drawRect(x*boxsize,y*boxsize,x*boxsize+boxsize,y*boxsize+boxsize,mapPaint);
            }
        }
    }

    public void drawLines(Canvas canvas,boolean isGuideLine) {
        if (!isGuideLine){
            return;
        }
        //竖直方向的线
        for(int x = 0;x < maps.length; x ++){
            //canvas.drawLine(startx,starty,stopx,stopy,paint);
            canvas.drawLine(x*boxsize,0,x*boxsize,yHeight,linePaint);
        }
        //横向的线
        for(int x = 0;x < maps[0].length;x++){
            canvas.drawLine(0,x*boxsize,xWidth,x*boxsize,linePaint);
        }
    }

    public void drawState(Canvas canvas,boolean isOver,boolean isPause) {
        //画游戏结束状态
        if (isOver){
            canvas.drawText("游戏结束",xWidth/2-statePaint.measureText("游戏结束"),yHeight/2+50,statePaint);
        }
        //画暂停状态
        if (isPause&&!isOver){
            canvas.drawText("暂停",xWidth/2-statePaint.measureText("暂停"),yHeight/2+50,statePaint);
        }
    }

    public void cleanMaps() {
        //消除地图
        for (int x = 0; x < maps.length; x++) {
            for (int y = 0; y < maps[x].length; y++) {
                maps[x][y] = false;
            }
        }
    }

    //消行处理
    public int cleanLine() {
        int lines = 0;
        for (int y = maps[0].length-1; y > 0; y --) {//从下往上遍历
            //消行判断
            if (checkLine(y)){
                //执行消行
                deleteLine(y);
                //从消掉的那一行开始重新遍历
                y++;
                lines ++ ;
            }
        }
        return lines;
    }
    //消行判断
    public boolean checkLine(int y){
        for (int x = 0; x < maps.length; x++) {
            //如果有一个不为true,则改行不能消除
            if (!maps[x][y]){
                return false;
            }
        }
        //如果每一个maps都为true才执行消行
        return true;
    }
    //执行消行
    public void deleteLine(int dy) {
        for (int y = maps[0].length-1; y > 0 ; y --) {
            for (int x = 0; x < maps.length; x++) {
                maps[x][y] = maps[x][y-1];
            }
        }
    }


}
