package com.example.group5.fitnessapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Math.sqrt;


public class MainPage extends AppCompatActivity implements SensorEventListener{
    private Sensor accelerometerSensor;
    private SensorManager sensManager;
    private TextView sensorText;
    private TextView calorieText;

    public int steps = 0; //global variable keeps count of total steps taken
    private String testResult;
    private int stepCount = 0;
    private double calorie = 0;
    private double currentCalorie = 0;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    Calendar calendar = Calendar.getInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mAuth = FirebaseAuth.getInstance();
        //Get reference to database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        sensorText = (TextView)  findViewById(R.id.stepCount);
        calorieText = (TextView) findViewById(R.id.calorieBurnt);
        sensManager = (SensorManager)getSystemService(SENSOR_SERVICE); //Use getsystemservice to retrieve a sensorManager for accessing sensors
        accelerometerSensor = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onResume(){ //Register sensors as soon as activity is opened
        super.onResume();
        sensManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //If the user is not logged in, redirect them to the login page
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //TODO Potentially listener, under the current day's node e.g. sat=7 so listen for nodes there
        //Date now = new Date();
        //calendar.setTime(now);
        //int dayIndex = Calendar.DAY_OF_WEEK;
        //String day = Integer.toString(dayIndex);

        //Implement value listener which executes a method every time something changes in the database
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("steps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser current = mAuth.getCurrentUser();
                //Since we added a valueEventListner for the specific user child,
                //We can simply user the datasnapshot to get the value of variable stored under their id
                String count = (String) dataSnapshot.child("step").getValue(); //This current shows if user created a account i guess meaning step initialise to 0 upon creation
                String calorieToday = (String) dataSnapshot.child("calories").getValue();
                currentCalorie = Double.parseDouble(calorieToday);

                /*/if (count == null) {
                    //If variable doesn't yet exist
                    //However it should've of been created via sign up
                    //so this shouldn't be happening
                    System.out.println("null");
                } else if (Integer.parseInt(count) >= 0){ */
                    //stepCount = Integer.parseInt(count);
                    //testResult = "not null";
                    Date now = new Date();
                    calendar.setTime(now);
                    int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);

                if (dayIndex == 1) {
                    //1 is sunday, but we want monday so set it to 7
                    String sunday = (String) dataSnapshot.child("sunday").getValue();
                    stepCount = Integer.parseInt(sunday);
                } else if (dayIndex == 2) {
                    String monday = (String) dataSnapshot.child("monday").getValue();
                    stepCount = Integer.parseInt(monday);
                } else if (dayIndex == 3) {
                    String tuesday = (String) dataSnapshot.child("tuesday").getValue();
                    stepCount = Integer.parseInt(tuesday);

                } else if (dayIndex == 4) {
                    String wednesday = (String) dataSnapshot.child("wednesday").getValue();
                    stepCount = Integer.parseInt(wednesday);
                } else if (dayIndex == 5) {
                    String thursday = (String) dataSnapshot.child("thursday").getValue();
                    stepCount = Integer.parseInt(thursday);
                } else if (dayIndex == 6) {
                    String friday = (String) dataSnapshot.child("friday").getValue();
                    stepCount = Integer.parseInt(friday);

                }else if (dayIndex == 7) {
                    String saturday = (String) dataSnapshot.child("saturday").getValue();
                    stepCount= Integer.parseInt(saturday);

                }
                    /*
                    switch (dayIndex) {
                        case 1:
                            //Its monday, or sunday for america time
                            String sunday = (String) dataSnapshot.child("sunday").getValue();
                             stepCount = Integer.parseInt(sunday);
                            //sunday = Integer.toString(stepCount);//0
                            break;
                        case 2:
                            String monday = (String) dataSnapshot.child("monday").getValue();
                            stepCount = Integer.parseInt(monday);
                            break;
                        case 3:
                            String tuesday = (String) dataSnapshot.child("tuesday").getValue();
                            stepCount = Integer.parseInt(tuesday);
                            break;
                        //3 is tues
                        case 4:
                            String wednesday = (String) dataSnapshot.child("wednesday").getValue();
                            stepCount = Integer.parseInt(wednesday);
                            break;
                        //4 is wed
                        case 5:
                            String thursday = (String) dataSnapshot.child("thursday").getValue();
                            stepCount = Integer.parseInt(thursday);
                            break;
                        //5 is thur
                        case 6:
                            String friday = (String) dataSnapshot.child("friday").getValue();
                            stepCount = Integer.parseInt(friday);
                            break;
                        //6 is fri
                        case 7:
                            String saturday = (String) dataSnapshot.child("saturday").getValue();
                            stepCount= Integer.parseInt(saturday);
                            break;
                        //7 is Sat
                    } */
                }
            // }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void onStepTrack(View view){
        Intent i = new Intent(MainPage.this, StepTracking.class);
        saveUserInformation(stepCount, currentCalorie);
        //i.putExtra("steps", steps);
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

    public void onLogout(View view){
        //Sign out user and launch the login page
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
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
            double cal = 0;
            if (sensorData >= 12 && sensorData <= 17) {
                cal = cal + (0.05); //Estimate for calories burnt per step, so 1 calories for every 20 steps. so 0.05 for 1 step.
                calorie = calorie + (0.05);
                count++; //When person walks increment the count

            }
            //This shows steps for current session
            steps = steps + count;

            //This data will be passed into the database when user clicks on steptracker activity
            stepCount = stepCount + count;
            calorie = Math.floor(calorie * 100) / 100; //truncates the decimal places to two.
            currentCalorie = currentCalorie + cal;
            currentCalorie = Math.floor(currentCalorie * 100) /100;
            sensorText.setText("Steps currently taken: " + steps); //print out total steps
            calorieText.setText("Calories currently burned: "+ calorie + " kcal");
        }
    }

    public void saveUserInformation(int s, double c) {
        //convert the int to string for usability in firebase
        String step = Integer.toString(s);
        String calorie  = Double.toString(c);
        //String calorieBurnt = Integer.toString(calorie);
        //Create object used to store that info into the database
        //StepInformation stepInformation = new StepInformation(step, calorie);
        //Get current user
        FirebaseUser user = mAuth.getCurrentUser();

        //Get day of week
        Date now = new Date();
        calendar.setTime(now);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        //int dayIndex = 3;
        String day = Integer.toString(dayIndex);

        //add this information under that user's name
        //Also includes indices for each day
        //databaseReference.child(user.getUid()).child("steps").child(step).setValue(stepInformation);
        //TODO: Add a new child under steps representing each day
        if (dayIndex == 1) {
            //1 is sunday, but we want monday so set it to 7
            databaseReference.child(user.getUid()).child("steps").child("sunday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);
        } else if (dayIndex == 2) {
            databaseReference.child(user.getUid()).child("steps").child("monday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);
        } else if (dayIndex == 3) {
            databaseReference.child(user.getUid()).child("steps").child("tuesday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);

        } else if (dayIndex == 4) {
            databaseReference.child(user.getUid()).child("steps").child("wednesday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);
        } else if (dayIndex == 5) {
            databaseReference.child(user.getUid()).child("steps").child("thursday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);
        } else if (dayIndex == 6) {
            databaseReference.child(user.getUid()).child("steps").child("friday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);

        }else if (dayIndex == 7) {
            databaseReference.child(user.getUid()).child("steps").child("saturday").setValue(step);
            databaseReference.child(user.getUid()).child("steps").child("calories").setValue(calorie);

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    protected void onStop() { //When Activity is no longer visible, stop counting; take this out if you want it to count in the background
        super.onStop();
        sensManager.unregisterListener(this, accelerometerSensor);
    }
}

