package com.example.group5.fitnessapp;

public class StepInformation {
    public String step;
    public String calories;
    public String monday ;
    public String tuesday ;
    public String wednesday;
    public String thursday ;
    public String friday ;
    public String saturday ;
    public String sunday ;

    public StepInformation(String step, String calories) {

        this.step = step;
        this.calories = calories; //TODO pass calories into the constructor
        monday = step;
        tuesday=  step ;
        wednesday= step;
        thursday = step;
        friday = step;
        saturday = step;
        sunday = step;

    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
