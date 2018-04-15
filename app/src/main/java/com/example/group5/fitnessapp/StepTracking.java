package com.example.group5.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StepTracking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tracking);
    }

    public void homeButton(View view) {
        // close the current steps tracker activity and return to Self.class
        this.finish();
    }
}
