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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import static java.lang.Math.sqrt;


public class MainPage extends AppCompatActivity implements SensorEventListener{
    private Sensor accelerometerSensor;
    private SensorManager sensManager;
    private TextView sensorText;

    private int steps = 0; //global variable keeps count of total steps taken

    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        sensorText = (TextView)  findViewById(R.id.stepCount);
        sensManager = (SensorManager)getSystemService(SENSOR_SERVICE); //Use getsystemservice to retrieve a sensorManager for accessing sensors
        accelerometerSensor = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /*barChart = findViewById(R.id.bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(44f,1));
        barEntries.add(new BarEntry(44f,2));
        barEntries.add(new BarEntry(44f,3));
        barEntries.add(new BarEntry(44f,4));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("Monday");
        theDates.add("Tuesday");
        theDates.add("Wednesday");
        theDates.add("Thursday");
        theDates.add("Friday");

        BarData theData = new BarData((IBarDataSet) theDates,barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);*/
    }

    public void onStepTrack(View view){
        Intent i = new Intent(MainPage.this, StepTracking.class);
        startActivity(i); //launches step tracker

    }
    public void onDetails(View view){
        Intent i = new Intent(MainPage.this, ReportsActivity.class);
        startActivity(i); //launches step tracker

    }
    public void onProgress(View view){
        Intent i = new Intent(MainPage.this, Self.class);
        startActivity(i); //launches step tracker

    }
    public void onCalculator(View view){
        Intent i = new Intent(MainPage.this, CalorieCalculatorActivity.class);
        startActivity(i); //launches step tracker

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
                count++; //When person walks increment the count
            }
            steps = steps + count; //Add the count to total steps taken
            sensorText.setText("Steps: " + steps); //print out total steps
            //textView.setText("Sensor Data (Vector Length): " + sensorData); //Displays meaningful sensor data
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    protected void onResume(){ //Register sensors as soon as activity is opened
        super.onResume();
        sensManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    protected void onStop() { //When Activity is no longer visible, stop counting
        super.onStop();
        sensManager.unregisterListener(this, accelerometerSensor);
    }
}

