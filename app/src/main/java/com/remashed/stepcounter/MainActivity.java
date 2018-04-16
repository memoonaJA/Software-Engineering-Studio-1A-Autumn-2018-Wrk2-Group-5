package com.remashed.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import static java.lang.Math.sqrt;
import static java.lang.Math.*;

//extends Activity class , inherits its methods,properties etcccccccccccccc
public class MainActivity extends AppCompatActivity implements SensorEventListener{ //recieves notifcations from SensorManager for new sensor data
    private SensorManager mSensorManager; //Sensor Manager allows you to access sensors
    private Sensor mAccelerometerSensor; //API 19 sensor object, Gonna use it for accelerometer
    private TextView textview;
    private TextView sensorText;
    private int steps = 0; //Keeps track of steps taken

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //initialise activity
        setContentView(R.layout.activity_main); //call layout resource defining your UI, "activity_main.xml"
        textview = (TextView) findViewById(R.id.steptext); //assigns to a reference || Links to the textview with id provided in main xml
        sensorText = (TextView) findViewById(R.id.sensortext);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); //Use getsystemservice to retrieve a sensorManager for accessing sensors
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) { //called when there is a new reading from the sensor using the same sensor values (but a newer timestamp)
        //Accelerometer provides acceleration in X, Y ,Z axis which includes force of gravity

        //Accelerometer returns float values
        double x = (double) event.values[0]; //Convert acceleration values to doubles
        double y = (double) event.values[1];
        double z = (double) event.values[2];

        //Some accurate formula found of the net, to provide a data regardless of Device orientation
        //Length of a vector is sqrt(XX + YY + ZZ) -> always ~9.8(force of gravity)  reading regardless of orientation
        //I don't understand any of the mathematical reasoning but it works sorta

        double sum = Math.pow(x,2) + Math.pow(y, 2) + Math.pow(z, 2); //Executing that formula

        //Changing RAW DATA into usable data
        double sensorData = sqrt(sum);

         if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)  //Checks if accelerometer is providing data
         {
             int count = 0; //count for when person walks/jogs/runs
             if(sensorData>= 12 && sensorData <= 17) {
                 count++; //When person walks increment the count
             }
             steps = steps + count; //Add the count to total steps taken
             textview.setText("Steps: "+ steps); //print out total steps
             sensorText.setText("Sensor Data (Vector Length): " + sensorData); //Displays meaningful sensor data
         }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Empty code but must implement as its abstract
    }

    protected void onResume(){ //Register sensors as soon as activity is opened
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /* protected void onPause(){ //Occurs when activity is minimised, or some else appears in foreground but you can still see it
                                //Do you want it to keep running? (Yes), but for now i'm leaving it off for testing and learn purposes
        super.onPause();

        mSensorManager.unregisterListener(this, mAccelerometerSensor);
    }
    */
    protected void onStop() { //When Activity is no longer visible, stop counting
        super.onStop();
        mSensorManager.unregisterListener(this, mAccelerometerSensor);
    }
}
