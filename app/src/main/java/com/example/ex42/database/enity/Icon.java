package com.example.ex42.database.enity;

public class Icon {
    private int iconId;
    private int imageId;
    private boolean canClick;
    private boolean isRight = false;

    public Icon() {
        this.imageId = -1;
        this.canClick = true;
    }

    public Icon(int imageId, boolean canClick) {
        this.imageId = imageId;
        this.canClick = canClick;
    }

    public void SetIconId(int iconId){
        this.iconId = iconId;
    }

    public int GetIconId(){
        return this.iconId;
    }

    public void SetImageId(int imageId){
        this.imageId = imageId;
    }

    public int GetImageId(){
        return imageId;
    }

    public void SetCanClick(boolean canClick){
        this.canClick = canClick;
    }

    public boolean GetCanClick(){
        return this.canClick;
    }

    public void SetIsRight(boolean isRight){
        this.isRight = isRight;
    }
    public boolean GetIsRight(){
        if(!this.canClick)
            return true;
        else
            return this.isRight;
    }
}
