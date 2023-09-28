package com.example.game;

public class GameListItem {
    int itemId;
    int imageId;
    String gameName;
    String gameInfo;

    public void SetItemId(int id){
        this.itemId = id;
    }

    public void SetImageId(int id){
        this.imageId = id;
    }

    public void SetGameName(String name){
        this.gameName = name;
    }

    public void SetGameInfo(String info){
        this.gameInfo = info;
    }

    public int GetImageId(){
        return this.imageId;
    }

    public int GetItemId(){
        return this.itemId;
    }

    public String GetGameName(){
        return this.gameName;
    }

    public String GetGameInfo(){
        return this.gameInfo;
    }
}
