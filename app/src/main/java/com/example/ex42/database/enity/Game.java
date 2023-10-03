package com.example.ex42.database.enity;

import com.example.ex42.R;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public int Image;
    public String name;
    public String desc;

    public Game(int Image, String name, String desc) {
        this.Image = Image;
        this.name = name;
        this.desc = desc;
    }
    private static int[] iconArray = {R.drawable.ooxx, R.drawable.fk};
    private static String[] nameArray = {"OOXX", "俄罗斯方块"};
    private static String[] descArray = {
            "网格中的不同地方放置了一些X和O。游戏的目的是将X和O填充到剩余的方格内:\n" +
                    "            每一行以及每一列中没有超过俩个连续的X或O。\n" +
                    "            每一行以及每一列中的X和O数量相同。\n" +
                    "            每一行以及每一列都是唯一的。",
            "移动、旋转和摆放游戏自动输出的各种方块，使之排列成完整的一行或多行并且消除得分。"
    };

    public static List<Game> getDefaultList() {
        List<Game> planetList = new ArrayList<Game>();
        for (int i = 0; i < iconArray.length; i++) {
            planetList.add(new Game(iconArray[i], nameArray[i], descArray[i]));
        }
        return planetList;
    }

}
