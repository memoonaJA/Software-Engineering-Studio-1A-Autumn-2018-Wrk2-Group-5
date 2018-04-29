package com.example.group5.fitnessapp;

/**
 * Created by NathL on 29/04/2018.
 */

public class User {

    public String email;
    public String password;
    private String Uid;

    public User() {
        //Required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setUid(String id) {
        this.Uid = id;
    }

    public String getUid() {
        return this.Uid;
    }
}
