package com.example.group5.fitnessapp;

public class User {
    public String name;
    public String weight;
    public String height;
    public String age;
    public String intake;
    public String goal;

    public User(String weight, String height, String age, String intake, String goal) {
        //this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.intake = intake;
        this.goal = goal;
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

    public String getIntake() {
        return intake;
    }

    public String getGoal() {
        return goal;
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

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public void setGoal(String goal) {
        this.goal = goal;
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
