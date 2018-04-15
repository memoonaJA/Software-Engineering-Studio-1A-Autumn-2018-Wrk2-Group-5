package com.example.group5.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    // called when user presses login
    public void attemptLogin(View view) {
        Intent intent = new Intent(this, Self.class);
        // Code to authenticate user login should be triggered here.
        if (true/**Authentication code should return true/false here. temp set to true**/) {
            startActivity(intent);
        }
    }
}
