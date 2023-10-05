package com.example.ex42.InternetGameNews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {


    @Expose(serialize = false , deserialize = false)
    private Integer mId;

    @SerializedName("description")
    private String mSource;

    @SerializedName("picUrl")
    private String mPicUrl;

    @SerializedName("url")
    private String mContentUrl;

    @SerializedName("ctime")
    private String mPublishTime;


    @SerializedName("title")
    private String mTitle;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String mSource) {
        this.mSource = mSource;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public void setPicUrl(String mPicUrl) {
        this.mPicUrl = mPicUrl;
    }

    public String getContentUrl() {
        return mContentUrl;
    }

    public void setContentUrl(String mContentUrl) {
        this.mContentUrl = mContentUrl;
    }

    public String getDate() {
        return mPublishTime;
    }

    public void setPublishTime(String mPublishTime) {
        this.mPublishTime = mPublishTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public News() {
    }
}
