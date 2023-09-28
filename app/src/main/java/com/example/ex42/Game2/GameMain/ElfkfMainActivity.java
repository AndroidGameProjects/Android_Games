package com.example.ex42.Game2.GameMain;


import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ex42.Game2.GameMain.control.GameControl;
import com.example.ex42.R;

public class ElfkfMainActivity extends AppCompatActivity implements View.OnClickListener {

    //view层没有数据没有变量
    //游戏区域控件
    public View gamePanel;
    //下一块预览区域控件
    public View nextPanel;
    //游戏控制器
    GameControl gameControl;
    //当前分数
    public TextView textNowScore;

    // 最高分数
    private int highestScore = 0; // 添加变量用于保存最高分数

    //方块的种类
    final int TYPE = 7;

    public Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            String type = (String) msg.obj;
            if (type == null){
                return;
            }
            if (type.equals("invalidate")){
                //刷新重绘view
                gamePanel.invalidate();
                nextPanel.invalidate();
                //刷新分数
                textNowScore.setText(gameControl.scoreModel.score+"");
                // 根据当前分数实时刷新最高分
                if (gameControl.scoreModel.score > highestScore) {
                    highestScore = gameControl.scoreModel.score;
                    // 更新最高分数并保存
                    gameControl.updateHighScore();
                    // 刷新显示最高分
                    TextView textHighScore = findViewById(R.id.textMaxScore);
                    textHighScore.setText(highestScore + "");
                }
            } else if (type.equals("pause")) {
                if (msg.arg1 == 0){
                    ((Button)findViewById(R.id.btnPause)).setText("继续");
                } else {
                    ((Button)findViewById(R.id.btnPause)).setText("暂停");
                }
            } else if (type.equals("guideLine")) {
                if (msg.arg1==0){
                    //开启辅助线
                    ((Button)findViewById(R.id.btnGuideLines)).setText("辅助线-关");
                }else {
                    //关闭辅助线
                    ((Button)findViewById(R.id.btnGuideLines)).setText("辅助线-开");
                }
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_elsfk);

        //实例化游戏控制器
        gameControl = new GameControl(handler,this);

        // 更新最高分数，并显示
        highestScore = gameControl.getHighScore(this);
        TextView textHighScore = findViewById(R.id.textMaxScore);
        textHighScore.setText(highestScore + "");

        //初始化视图
        initView();
        //监听方法
        initListener();
    }

    //初始化监听方法
    private void initListener() {
        findViewById(R.id.btnLeft).setOnClickListener(this);
        findViewById(R.id.btnTop).setOnClickListener(this);
        findViewById(R.id.btnRight).setOnClickListener(this);
        findViewById(R.id.btnBottom).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnPause).setOnClickListener(this);
        findViewById(R.id.btnDown).setOnClickListener(this);
        findViewById(R.id.btnGuideLines).setOnClickListener(this);
    }

    //初始化视图
    public void initView() {

        // 在初始化视图时，显示最高分数
        TextView textHighScore = findViewById(R.id.textMaxScore);
        textHighScore.setText(gameControl.getHighScore(this) + "");
        textNowScore = (TextView) findViewById(R.id.textNowScore);
        textNowScore.setText("0");

        //2.实例化游戏区域
        gamePanel = new View(this){
            //重写游戏区域绘制
            @Override
            protected void onDraw(Canvas canvas){
                super.onDraw(canvas);
                //绘制
                gameControl.drawGame(canvas);
            }
        };
        //3.设置游戏区域大小
        gamePanel.setLayoutParams(new FrameLayout.LayoutParams(Config.XWHITH,Config.YHEIGHT));
        //设置背景颜色
        gamePanel.setBackgroundColor(Config.GAME_BG);
        //4.添加到父容器里
        FrameLayout layoutGame = (FrameLayout) findViewById(R.id.LayoutGame);
        layoutGame.setPadding(0,0,Config.PADDING,0);
        layoutGame.addView(gamePanel);

        //设置信息区域间隔
        LinearLayout LayoutInfo = (LinearLayout) findViewById(R.id.layoutInfo);
        LayoutInfo.setBackgroundColor(Config.INFO_BG);


        //实例化下一块预览区域
        nextPanel = new View(this){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                gameControl.drawNext(canvas,nextPanel.getWidth());
            }
        };
        //设置大小
        nextPanel.setLayoutParams(new FrameLayout.LayoutParams(-1,200));
        //添加到父容器
        FrameLayout layoutNext = (FrameLayout) findViewById(R.id.LayoutNext);
        layoutNext.addView(nextPanel);

        //设置toplayout背景颜色
        ((LinearLayout)findViewById(R.id.layoutTop)).setPadding(Config.PADDING,Config.PADDING,Config.PADDING,Config.PADDING);


        textNowScore = (TextView) findViewById(R.id.textNowScore);

    }


    //捕捉点击事件
    @Override
    public void onClick(View v) {
        gameControl.onClick(v.getId());

        gameControl.saveHighScore(this);

        //刷新视图重新绘制view
        gamePanel.invalidate();
        nextPanel.invalidate();
    }

}
