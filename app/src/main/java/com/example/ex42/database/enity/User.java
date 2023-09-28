package com.example.ex42.database.enity;

import java.io.Serializable;

public class User implements Serializable {

    public int id;
    public String account;
    public String password;
    public String RegisterTime;
    public String game1;
    public String game2;

    public User() {
    }

    public User( String account, String password, String registerTime, String game1, String game2) {
        this.account = account;
        this.password = password;
        this.RegisterTime = registerTime;
        this.game1 = game1;
        this.game2 = game2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", RegisterTime='" + RegisterTime + '\'' +
                ", game1='" + game1 + '\'' +
                ", game2='" + game2 + '\'' +
                '}';
    }
}
