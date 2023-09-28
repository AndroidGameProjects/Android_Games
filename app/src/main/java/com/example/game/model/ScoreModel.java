package com.example.game.model;

public class ScoreModel {

    //当前分数
    public int score;

    //最高分数
    public int maxScore;

    //加分方法2行3分 ， 3行5分 ，5行7分
    public void add(int lines) {
        if (lines == 0){
            return;
        }
        int add = lines + (lines-1);
        score += add;
    }
}
