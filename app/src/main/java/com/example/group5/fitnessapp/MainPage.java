package com.example.group5.fitnessapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import java.util.ArrayList;

import static java.lang.Math.sqrt;


public class MainPage extends AppCompatActivity implements SensorEventListener{
    private Sensor accelerometerSensor;
    private SensorManager sensManager;
    private TextView sensorText;
    private TextView calorieText;
    public int steps = 0; //global variable keeps count of total steps taken
    private double calorie = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        sensorText = (TextView)  findViewById(R.id.stepCount);
        calorieText = (TextView) findViewById(R.id.calorieBurnt);
        sensManager = (SensorManager)getSystemService(SENSOR_SERVICE); //Use getsystemservice to retrieve a sensorManager for accessing sensors
        accelerometerSensor = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void onStepTrack(View view){
        Intent i = new Intent(MainPage.this, StepTracking.class);
        startActivity(i); //launches step tracker
    }
    public void onDetails(View view){ //Opens Self class - user personal details
        Intent i = new Intent(MainPage.this, Self.class);
        startActivity(i); //

    }
    public void onProgress(View view){ //Opens reports activity which details user progress
        Intent i = new Intent(MainPage.this, ReportsActivity.class);
        startActivity(i); //
    }
    public void onCalculator(View view){
        Intent i = new Intent(MainPage.this, CalorieCalculatorActivity.class);
        startActivity(i); //

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = (double) event.values[0]; //Convert acceleration values to doubles
        double y = (double) event.values[1];
        double z = (double) event.values[2];
        double sum = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2); //Executing that formula
        double sensorData = sqrt(sum);


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)  //Checks if accelerometer is providing data
        {
            int count = 0; //count for when person walks/jogs/runs
            if (sensorData >= 12 && sensorData <= 17) {
                calorie = calorie + (0.05); //Estimate for calories burnt per step, so 1 calories for every 20 steps. so 0.05 for 1 step.
                count++; //When person walks increment the count

            }
            steps = steps + count; //Add the count to total steps taken
            calorie = Math.floor(calorie * 100) / 100; //truncates the decimal places to two.
            sensorText.setText("Steps: " + steps); //print out total steps
            //textView.setText("Sensor Data (Vector Length): " + sensorData); //Displays meaningful sensor data
            calorieText.setText("You have burned: "+ calorie+ " kcal");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume(){ //Register sensors as soon as activity is opened
        super.onResume();
        sensManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    protected void onStop() { //When Activity is no longer visible, stop counting; take this out if you want it to count in the background
        super.onStop();
        sensManager.unregisterListener(this, accelerometerSensor);
    }
}

