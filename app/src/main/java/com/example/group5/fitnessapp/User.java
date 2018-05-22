package com.example.group5.fitnessapp;

public class User {

    public String email;
    public String password;
    private String Uid;

    public int age;
    public double height;
    public double weight;
    public double intake;
    public String goal;

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
