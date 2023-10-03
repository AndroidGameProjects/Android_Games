package com.example.ex42.Game2.GameMain.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.ex42.Game2.GameMain.Config;
import com.example.ex42.Game2.GameMain.model.BoxsModel;
import com.example.ex42.Game2.GameMain.model.MapsModel;
import com.example.ex42.Game2.GameMain.model.ScoreModel;
import com.example.ex42.R;

public class GameControl {

    private boolean isStart = false;
    Handler handler;

    //地图模型
    MapsModel mapsModel;
    //方块模型
    BoxsModel boxsModel;
    //分数模型
    public ScoreModel scoreModel;
    //自动下落线程
    public Thread downThread;

    //暂停状态
    public  boolean isPause;
    //游戏结束状态
    public boolean isOver;
    //辅助线开关状态
    public boolean isGuideLine;

    //最高分的分数
    public int highestScore;
    private static final String PREFS_NAME = "GamePrefs";
    private static final String HIGH_SCORE_KEY = "HighScore";

    Context context;

    public GameControl(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
        initData(context);


    }

    //初始化数据
    public void initData(Context context) {
        //获得屏幕宽度
        int width = getScreenWidth(context);
        //设置游戏区域宽度 = 屏幕宽度*2/3
        Config.XWHITH = width *2/3;
        //设置游戏区域高度度 = 宽度*2
        Config.YHEIGHT = (width*2/3)*2 ;
        //设置间距 = 屏幕宽度 / 20
        Config.PADDING = width / Config.SPLIT_PADDING;


        //初始化方块大小 = 游戏区域宽度/10 因为10格
        int boxsize = Config.XWHITH / Config.MAPX;
        //实例化地图模型
        mapsModel = new MapsModel(Config.XWHITH,Config.YHEIGHT,boxsize);
        //实例化方块模型
        boxsModel = new BoxsModel(boxsize);
        //实例化分数模型
        scoreModel = new ScoreModel();

    }

    //获得屏幕宽度
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    //开始游戏
    private void startGame() {
        // 在开始游戏时获取最高分数并更新变量
        highestScore = getHighScore(context);
        scoreModel.score = 0;
//        Message msg = new Message();
//        msg.obj = "Nowzero";
//        handler.sendMessage(msg);


        if (downThread == null){
            downThread = new Thread(){
                @Override
                public void run(){
                    super.run();
                    while(true){
                        try {
                            //休眠500ms
                            sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        //先判断是否处于暂停状态或者结束状态
                        if (isPause||isOver) {
                            continue;
                        }
                        //执行一次下落
                        moveBottom();
                        //通知主线程刷新view
                        Message msg = new Message();
                        msg.obj = "invalidate";
                        handler.sendMessage(msg);
                    }
                }
            };
            downThread.start();
        }
        //消除地图
        mapsModel.cleanMaps();
        //生成新方块
        boxsModel.newBoxs();
        //重置游戏状态
        isOver = false;
        setPause(true);
    }

    //下落
    public boolean moveBottom(){
        //1.移动成功 不处理
        if (boxsModel.move(0,1,mapsModel))
            return true;
        //2.移动失败 堆积处理
        for(int i = 0;i < boxsModel.boxs.length;i ++ )
            mapsModel.maps[boxsModel.boxs[i].x][boxsModel.boxs[i].y] = true;
        //3.消行处理
        int lines = mapsModel.cleanLine();
        //4.加分
        scoreModel.add(lines);
        //5.生成新的方块
        boxsModel.newBoxs();
        //6.游戏结束判断
        isOver = checkOver();

        // 在游戏结束判断之后更新最高分数和刷新视图
        if (isOver) {
            updateHighScore();
        }

        return false;
    }



    //游戏结束判断
    public boolean checkOver() {
        for (int i = 0; i < boxsModel.boxs.length; i++) {
            if (mapsModel.maps[boxsModel.boxs[i].x][boxsModel.boxs[i].y]){
                //把暂停状态设为false
                setPause(true);
                return true;
            }
        }
        return false;
    }

    //设置暂停状态
    public void setPause(boolean reset){
        if (reset){
            isPause = true;
        }
        isPause = (isPause?false:true);
        Message msg = new Message();
        msg.obj = "pause";
        msg.arg1 = (isPause?0:1);
        handler.sendMessage(msg);
    }

    //控制绘制
//    public void draw(Canvas canvas) {
//        //绘制地图
//        mapsModel.drawMaps(canvas);
//        //绘制方块
//        boxsModel.drawBoxs(canvas);
//        //绘制地图辅助线
//        mapsModel.drawLines(canvas);
//        //绘制状态
//        mapsModel.drawState(canvas,isOver,isPause);
//    }

    public void onClick(int id) {
        if (id == R.id.btnLeft) {
            if (isPause){
                return ;
            }
            boxsModel.move(-1,0,mapsModel);
        } else if (id  == R.id.btnTop) {
            if (isPause){
                return ;
            }
            boxsModel.rotate(mapsModel);
        } else if (id  == R.id.btnRight) {
            if (isPause){
                return ;
            }
            boxsModel.move(1,0,mapsModel);
        } else if (id  == R.id.btnBottom) {
            if(isPause) {
                return;
            }
            boxsModel.move(0,1,mapsModel);
        } else if (id  == R.id.btnStart) {
            // 执行按钮开始
            Message msg = new Message();
            msg.obj = "StartMusic";
            handler.sendMessage(msg);
            isStart = true;
            startGame();
        } else if (id  == R.id.btnPause) {
            // 执行按钮暂停
            Message msg = new Message();
            msg.obj = "PauseMusic";
            handler.sendMessage(msg);
            setPause(false);
        } else if(id == R.id.btnDown){
            if (!isStart){
                return;
            }
            if (isPause){
                return ;
            }
            //快速下落
            while(true){
                //如果下落成功，则继续下落
                if (moveBottom()){

                }else {//失败结束循环
                    break;
                }
            }
        }else if (id == R.id.btnGuideLines){
            // 辅助线
            setGuideLine();
        }else {
            //其他情况
        }
    }

    //设置辅助线开关
    private void setGuideLine() {
        isGuideLine = (isGuideLine?false:true);
        Message msg = new Message();
        msg.obj = "guideLine";
        msg.arg1 = (isGuideLine?0:1); //0表示true , 1表示false
        handler.sendMessage(msg);
    }

    //绘制游戏区域
    public void drawGame(Canvas canvas) {
        //绘制地图
        mapsModel.drawMaps(canvas);
        //绘制方块
        boxsModel.drawBoxs(canvas);
        //绘制地图辅助线
        mapsModel.drawLines(canvas,isGuideLine);
        //绘制状态
        mapsModel.drawState(canvas,isOver,isPause);
    }

    //绘制下一块预览区域
    public void drawNext(Canvas canvas, int width) {
        boxsModel.drawNext(canvas,width);
    }


    // 保存最高分数
    public void saveHighScore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HIGH_SCORE_KEY, scoreModel.score);
        editor.apply();
    }

    // 获取最高分数
    public int getHighScore(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0); // 默认值为0
    }

    // 更新最高分数并发送消息给主线程刷新视图
    public void updateHighScore() {
        if (scoreModel.score > highestScore) {
            highestScore = scoreModel.score;
            saveHighScore(context); // 保存最高分数
        }
        // 发送消息给主线程，刷新视图显示最高分数
        Message msg = new Message();
        msg.obj = "invalidate";
        handler.sendMessage(msg);
    }

}
