package com.example.ex42.database.enity;

import java.io.Serializable;

public class RankInfo implements Serializable {

    public int id;
    public String account;
    public String OverTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOverTime() {
        return OverTime;
    }

    public void setOverTime(String overTime) {
        OverTime = overTime;
    }

    public RankInfo(int id, String account, String overTime) {
        this.id = id;
        this.account = account;
        this.OverTime = overTime;
    }

    public RankInfo() {

    }

    @Override
    public String toString() {
        return "RankInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", OverTime='" + OverTime + '\'' +
                '}';
    }


}
