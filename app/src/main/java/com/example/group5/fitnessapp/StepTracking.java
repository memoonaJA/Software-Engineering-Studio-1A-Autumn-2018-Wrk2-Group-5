package com.example.group5.fitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
//GraphView objects
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StepTracking extends AppCompatActivity{
    //  BarChart barChart;
    GraphView graph;
    BarGraphSeries<DataPoint> series;
    //Calendar calendar = Calendar.getInstance(new Locale("en", "AU"));
    Calendar calendar = Calendar.getInstance(Locale.getDefault());

    TextView steps, calories;
    private int todaySteps = 0;
    private int stepsToday = 0;
    private int monday = 0;
    private int tuesday = 0;
    private int wednesday = 0;
    private int thursday = 0;
    private int friday = 0;
    private int satday = 0;
    private int sunday = 0;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tracking);

        steps = (TextView) findViewById(R.id.todayStep);
        calories = (TextView) findViewById(R.id.burntToday);

        mAuth = FirebaseAuth.getInstance();
        //Get reference to database
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void onHelp(View view) {
        Toast.makeText(this, "Days are represented by numbers starting with Sunday at 1", Toast.LENGTH_LONG).show();
    }

    public void onResetGraph(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        StepInformation step = new StepInformation("0", "0");
        databaseReference.child(user.getUid()).child("steps").setValue(step);
        Toast.makeText(this, "Graph reset please refresh the page", Toast.LENGTH_LONG).show();

    }

    public void onResetCalorie(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("steps").child("calories").setValue("0");
        Toast.makeText(this, "Daily calories reset", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //If the user is not logged in, redirect them to the login page
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Check for current date then figure out which axis to input it into.
        Date now = new Date();
        calendar.setTime(now);
        //int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        //final String day = Integer.toString(dayIndex);

        //Implement value listener which executes a method every time something changes in the database
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("steps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser current = mAuth.getCurrentUser();
                //Since we added a valueEventListner for the specific user child,
                //We can simply user the datasnapshot to get the value of variable stored under their id
                String count = (String) dataSnapshot.child("step").getValue(); //TODO this should be placeholder. then start working with actual days
                Date now = new Date();
                calendar.setTime(now);
                int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
                //int dayIndex = 3;
                final String day = Integer.toString(dayIndex);


                if (count == null) {
                    //If variable doesn't yet exist
                    //However it should've of been created via sign up
                    //so this shouldn't be happening
                    System.out.println("null");
                } else if (stepsToday >= 0){
                    todaySteps = Integer.parseInt(count);
                    //Date labels
                    Date d1 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date d2 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date d3 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date d4 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date d5 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date d6 = calendar.getTime();
                    calendar.add(Calendar.DATE, 1);
                    Date d7 = calendar.getTime();

                    int mon ;
                    int tues;
                    int wednes;
                    int thurs;
                    int fri ;
                    int sat ;
                    int sun ;

                    String caloriess = (String) dataSnapshot.child("calories").getValue();
                    calories.setText("Calories Burnt Today: " + Double.parseDouble(caloriess));

                    //Check which day it is, then use that index to choose which day we're going to be working on
                    switch (dayIndex) {
                        case 1:
                            //Its monday, or sunday for america time
                            String sunday = (String) dataSnapshot.child("sunday").getValue();
                            stepsToday = Integer.parseInt(sunday);
                            steps.setText("Steps taken today: " + stepsToday);


                            break;
                        case 2:
                            String monday = (String) dataSnapshot.child("monday").getValue();
                            stepsToday = Integer.parseInt(monday);
                            steps.setText("Steps taken today: " + stepsToday);
                            break;
                        case 3:
                            String tuesday = (String) dataSnapshot.child("tuesday").getValue();
                            stepsToday = Integer.parseInt(tuesday);
                            steps.setText("Steps taken today: " + stepsToday);
                            break;
                        //3 is tues
                        case 4:
                            String wednesday = (String) dataSnapshot.child("wednesday").getValue();
                            stepsToday = Integer.parseInt(wednesday);
                            steps.setText("Steps taken today: " + stepsToday);
                            break;
                        //4 is wed
                        case 5:
                            String thursday = (String) dataSnapshot.child("thursday").getValue();
                            stepsToday = Integer.parseInt(thursday);
                            steps.setText("Steps taken today: " + stepsToday);;
                            break;
                        //5 is thur
                        case 6:
                            String friday = (String) dataSnapshot.child("friday").getValue();
                            stepsToday = Integer.parseInt(friday);
                            steps.setText("Steps taken today: " + stepsToday);
                            break;
                        //6 is fri
                        case 7:
                            String saturday = (String) dataSnapshot.child("saturday").getValue();
                            stepsToday = Integer.parseInt(saturday);
                            steps.setText("Steps taken today: " + stepsToday);
                            break;
                        //7 is Sat
                    }

                    //Its monday, or sunday for america time
                    String sunday = (String) dataSnapshot.child("sunday").getValue();
                    sun = Integer.parseInt(sunday);

                    String monday = (String) dataSnapshot.child("monday").getValue();
                    mon = Integer.parseInt(monday);

                    String tuesday = (String) dataSnapshot.child("tuesday").getValue();
                    tues = Integer.parseInt(tuesday);

                    String wednesday = (String) dataSnapshot.child("wednesday").getValue();
                    wednes = Integer.parseInt(wednesday);

                    String thursday = (String) dataSnapshot.child("thursday").getValue();
                    thurs = Integer.parseInt(thursday);

                    String friday = (String) dataSnapshot.child("friday").getValue();
                    fri = Integer.parseInt(friday);

                    String saturday = (String) dataSnapshot.child("saturday").getValue();
                    sat = Integer.parseInt(saturday);

                    Intent intent = getIntent();
                    int total = intent.getIntExtra("steps",0);
                    //steps = (TextView)  findViewById(R.id.todayStep);
                    //steps.setText("Steps Taken Today: " + todaySteps);
                    graph = (GraphView) findViewById(R.id.graph);

                    //Need to grab data from the firebase database ;; for now we'll just grab the data from the main page
                    series = new BarGraphSeries<>(new DataPoint[] {
                            new DataPoint(1, sun), //Mon or sun
                            new DataPoint(2, mon), //T
                            new DataPoint(3,tues ), //W
                            new DataPoint(4, wednes), //Th
                            new DataPoint(5, thurs), //FR
                            new DataPoint(6, fri), //Sat
                            new DataPoint(7,sat ), //Sun but with calendar it's actually 1 and mon = 2 so SAT = 7*/
                    });
                    graph.addSeries(series);


                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                        }
                    });
                    
                   // Date now = new Date();
                    calendar.setTime(now);
                    //int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
                    //int dayIndex = 1;
                    int index = 0;
                    //String day = Integer.toString(dayIndex);

                   /* switch(dayIndex) {
                        //1 is sunday, but we want monday so set it to 7
                        case 1:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                        //2 is monday set to one
                        case 2:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                        //3 is tues
                        case 3:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                        //4 is wed
                        case 4:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                        //5 is thur
                        case 5:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                        //6 is fri
                        case 6:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                        //7 is Sat
                        case 7:
                            series.appendData(new DataPoint(dayIndex, stepsToday ), false, 10000);
                        break;
                    }
                    */

                    graph.setTitle("This Weeks Step Count");
                    graph.getViewport().setScrollable(true); //enables scrolling and zooming
                    graph.getViewport().setScrollableY(true);
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinY(0);
                    graph.getViewport().setMinX(1);
                    graph.getViewport().setMaxX(7);

                    //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
                    //StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    //staticLabelsFormatter.setHorizontalLabels(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
                    //graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.getGridLabelRenderer().setNumHorizontalLabels(7); // only 4 because of the space
                    graph.getGridLabelRenderer().setHumanRounding(true);
                    graph.getGridLabelRenderer().setPadding(32); //padding for axis labels to fit
                    series.setSpacing(2);
                    series.setDataWidth(1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
