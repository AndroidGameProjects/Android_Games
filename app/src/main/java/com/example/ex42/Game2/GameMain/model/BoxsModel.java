package com.example.ex42.Game2.GameMain.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.ex42.Game2.GameMain.Config;

import java.util.Random;

public class BoxsModel {

    //方块的类型
    public int boxType ;
    //方块大小
    public int boxsize;
    //方块
    public Point[] boxs;
    //方块画笔
    Paint boxPaint;

    //下一块方块
    public Point[] boxsNext;
    //下一块方块类型
    public int boxNextType;
    //下一块方块格子大小
    public int boxNextSize;

    public BoxsModel(int boxsize){
        //初始化方块画笔
        this.boxsize = boxsize;
        boxPaint = new Paint();
        boxPaint.setColor(Config.BOX_COLOR);
        boxPaint.setAntiAlias(true);
        boxs = new Point[]{};/////////////////////////////
    }

    //移动
    public boolean move(int x, int y, MapsModel mapsModel){
        //判断移动后是否出界
        for (int i = 0;i < boxs.length;i++){
            if (checkBoundary(boxs[i].x + x, boxs[i].y + y,mapsModel)){
                return false;
            }
        }
        //遍历方块数组 每一个都加上偏移量
        for (int i = 0;i < boxs.length;i++){
            boxs[i].x += x;
            boxs[i].y += y;
        }
        return true;
    }

    //旋转
    public boolean rotate(MapsModel mapsModel){
        //田不转
        if (boxType == 0){
            return false;
        }
        //与旋转判断旋转后是否出界
        for(int i = 0; i < boxs.length;i++){
            int checkX = -boxs[i].y + boxs[0].y + boxs[0].x;
            int checkY = boxs[i].x - boxs[0].x + boxs[0].y;
            if (checkBoundary(checkX,checkY,mapsModel)){
                return false;//出界，旋转失败
            }
        }
        //便利方块数组，每一个都绕中心点旋转顺90°
        for(int i = 0; i < boxs.length;i++){
            int checkX = -boxs[i].y + boxs[0].y + boxs[0].x;
            int checkY = boxs[i].x - boxs[0].x + boxs[0].y;
            boxs[i].x = checkX;
            boxs[i].y = checkY;
        }
        return true;
    }

    //边界判断
    public boolean checkBoundary(int x , int y , MapsModel mapsModel){//是否在边界外，true出界，false未出界
        return (x<0||y<0||x>=mapsModel.maps.length||y>=mapsModel.maps[0].length||mapsModel.maps[x][y] == true);//最后一个是判断是否与堆积的方块重复
    }

    //新的方块
    public void newBoxs() {
        if (boxsNext == null){
            newBoxsNext();
        }
        //当前方块 = 下一块
        boxs = boxsNext;
        //当前方块类型 = 下一块方块类型
        boxType = boxNextType;

        //生成下一块方块
        newBoxsNext();
    }

    //剩下一个方块
    public void newBoxsNext(){
        //随机数生成新的方块
        Random random = new Random();
        boxNextType = random.nextInt(7);
        switch(boxNextType){
            case 0://田
                boxsNext = new Point[]{new Point(4,0),new Point(5,0),new Point(4,1),new Point(5,1)};
                break;
            case 1://L
                boxsNext = new Point[]{new Point(4,1),new Point(5,0),new Point(3,1),new Point(5,1)};
                break;
            case 2://反L
                boxsNext = new Point[]{new Point(4,1),new Point(3,0),new Point(3,1),new Point(5,1)};
                break;
            case 3://长条
                boxsNext = new Point[]{new Point(5,0),new Point(4,0),new Point(6,0),new Point(7,0)};
                break;
            case 4://土
                boxsNext = new Point[]{new Point(5,1),new Point(5,0),new Point(4,1),new Point(6,1)};
                break;
            case 5://Z
                boxsNext = new Point[]{new Point(5,1),new Point(4,0),new Point(4,1),new Point(6,1)};
                break;
            case 6://反Z
                boxsNext = new Point[]{new Point(5,1),new Point(5,0),new Point(4,1),new Point(4,2)};
                break;
//            case 7://
//                boxsNext = new Point[]{}
        }
    }


    public void drawBoxs(Canvas canvas){
        //方块绘制
        for(int i = 0; i< boxs.length;i++){
            //canvas.drawRext(left,top,right,bottom,paint);矩形绘制
            canvas.drawRect(
                    boxs[i].x*boxsize,
                    boxs[i].y*boxsize,
                    boxs[i].x*boxsize+boxsize,
                    boxs[i].y*boxsize+boxsize,boxPaint);
        }
    }

    //绘制下一块预览
    public void drawNext(Canvas canvas, int width) {
        if (boxsNext != null) {
            if (boxNextSize == 0){
                boxNextSize = width / 5;
            }
            for (int i = 0; i < boxsNext.length; i++) {
                canvas.drawRect((boxsNext[i].x -3 )* boxNextSize,
                        (boxsNext[i].y + 1 ) * boxNextSize,
                        (boxsNext[i].x -3 ) * boxNextSize + boxNextSize,
                        (boxsNext[i].y + 1 ) * boxNextSize + boxNextSize,
                        boxPaint);
            }
        }
    }
}











