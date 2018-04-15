package com.remashed.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//extends Activity class , inherits its methods,properties etcccccccccccccc
public class MainActivity extends AppCompatActivity implements SensorEventListener{ //recieves notifcations from SensorManager for new sensor data
    private SensorManager mSensorManager; //Sensor Manager allows you to access sensors
    private Sensor mStepCounterSensor; //API 19 Sensor object, Going to use properties in sensor class to get info related to a step counter sensor
    private Sensor mAccelerometerSensor; //Same objective as above but for accelerometer
    private TextView textview;
    private TextView sensorText;
    private int stepCounter = 0;
    private int counterSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //initialise activity
        setContentView(R.layout.activity_main); //call layout resource defining your UI, "activity_main.xml"
        textview = (TextView) findViewById(R.id.steptext); //assigns to a reference || Links to the textview with id provided in main xml
        sensorText = (TextView) findViewById(R.id.sensortext);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); //Use getsystemservice to retrieve a sensorManager for accessing sensors
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); //Gets the sensor for a step counter
        //mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //NOTE: Step counter doesn't reset until you turn off the device

    }

    @Override
    public void onSensorChanged(SensorEvent event) { //called when there is a new reading from the sensor using the same sensor values (but a newer timestamp)
        //Note: of accelerometers value[0] = acceleration minus Gx on the x-axis
        //http://blog.bawa.com/2013/11/create-your-own-simple-pedometer.html got the implementation here for what to do with the values.
        float valueX = event.values[0]; //Data from sensor
        int steps = (int) valueX; //convert sensors data to an int
        /*if (valueX > 0 ) {//acceleration in device //dunno what this does
            steps = (int) valueX;
        } */

        /*if (counterSteps < 1) { //This code here resets the step count when open the app again after closing it not minimise.
            counterSteps = (int) event.values[0];
        }
        stepCounter = (int)event.values[0] - counterSteps; */

        if (mStepCounterSensor != null){ //Checks if sensor exist
            if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                textview.setText("Step Count: " + steps);
            }
        } else {
            sensorText.setText("You device does not support the necessary sensors");
            //use the accelerometer stuff here
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //no need
    }

    protected void onResume(){ //Register sensors as soon as activity is opened
        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

   /* protected void onPause(){ //Occurs when activity is minimised, or some else appears in foreground but you can still see it
                                //Do you want it to keep running? (Yes), but for now i'm leaving it off for testing and learn purposes
        super.onPause();

        mSensorManager.unregisterListener(this, mStepCounterSensor);
    }
    */
    protected void onStop() { //When Activity is no longer visible, stop counting
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
    }



}
