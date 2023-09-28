package com.example.ex42;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class GetCode {

    private static final int WIDTH = 200; // 图片宽度
    private static final int HEIGHT = 80; // 图片高度
    private static final int FONT_SIZE = 40; // 字体大小

    public static Bitmap getCode(String code) {
        // 创建一个空白的Bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);

        // 创建一个Canvas对象，用于绘制图片
        Canvas canvas = new Canvas(bitmap);

        // 创建一个随机数生成器
        Random random = new Random();

        // 设置画笔颜色和字体大小
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(FONT_SIZE);

        // 在画布上绘制验证码字符串
        int x = 20;
        int y = 50;
        for (int i = 0; i < code.length(); i++) {
            canvas.drawText(String.valueOf(code.charAt(i)), x, y, paint);
            x += FONT_SIZE;
        }

        // 在画布上绘制干扰线
        for (int i = 0; i < 10; i++) {
            int startX = random.nextInt(WIDTH);
            int startY = random.nextInt(HEIGHT);
            int endX = random.nextInt(WIDTH);
            int endY = random.nextInt(HEIGHT);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }

        // 返回生成的验证码图片
        return bitmap;
    }
}
