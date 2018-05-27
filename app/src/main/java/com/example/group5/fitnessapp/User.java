package com.example.group5.fitnessapp;

public class User {
    public String name;
    public String weight;
    public String height;
    public String age;
    public String gender;
    //public String goal;

    public User(String weight, String height, String age, String gender, String name) {
        //this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String intake) {
        this.gender = intake;
    }

    /*
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
    } */
}
