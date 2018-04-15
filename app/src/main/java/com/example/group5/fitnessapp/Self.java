package com.example.group5.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Self extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
    }

    public void openStepsTracker(View view) {
        Intent intent = new Intent(this, StepTracking.class);
        startActivity(intent);
    }
}
