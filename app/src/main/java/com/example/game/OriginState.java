package com.example.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OriginState {

    int point = -1;
    private List<int[][]> stateList = new ArrayList<>();
    private List<int[][]> answerList = new ArrayList<>();

    public OriginState(){
        int[][] array1 = {
                {0,0,0,1,0,1,0,0},
                {0,1,0,0,0,0,0,0},
                {0,0,0,0,1,1,0,1},
                {0,0,2,0,0,0,2,0},
                {1,0,0,0,1,0,0,0},
                {0,0,0,2,0,0,1,1},
                {0,1,0,0,0,0,0,0},
                {0,0,2,0,0,0,2,0}};

        int[][] answer1 = {
                {2,2,1,1,2,1,1,2},
                {1,1,2,1,2,2,1,2},
                {2,2,1,2,1,1,2,1},
                {2,1,2,1,2,1,2,1},
                {1,2,1,2,1,2,1,2},
                {2,2,1,2,1,2,1,1},
                {1,1,2,1,2,1,2,2},
                {1,1,2,2,1,2,2,1}};

        stateList.add(array1);

        answerList.add(answer1);
    }

    private void AddArrayToList(int[][] array){
        stateList.add(array);
    }

    public int[][] GetState(){
        point = (int)(Math.random()*(stateList.size()));
        return stateList.get(point);
    }

    public int[][] GetAnswer(){
        if(point >= 0){
            Log.d("Game", String.valueOf(answerList.get(point)));
            return answerList.get(point);
        }

        return null;
    }
}
