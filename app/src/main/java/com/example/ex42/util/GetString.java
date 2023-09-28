package com.example.ex42.util;

import java.util.Random;



public class GetString {
    private static final int CODE_LENGTH = 4; // 验证码长度
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789"; // 验证码字符集
    private static Random random = new Random();

    public static String getString(){
        // 生成随机验证码字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char c = CHARACTERS.charAt(index);
            sb.append(c);
        }
        String code = sb.toString().toLowerCase();
        return  code;
    }

}
