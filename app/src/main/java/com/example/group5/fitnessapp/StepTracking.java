package com.example.group5.fitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//GraphView objects
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class StepTracking extends AppCompatActivity{
  //  BarChart barChart;
    GraphView graph;
    BarGraphSeries<DataPoint> series;
    Calendar calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tracking);
    }

    protected void onResume(){ //Register sensors as soon as activity is opened,
        //This can then be used to create an object to grab the steps, everytime the activity is reopened
        super.onResume();

        //Date labels
        calendar = Calendar.getInstance();
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

        graph = (GraphView) findViewById(R.id.graph);

        //Need to grab data from the firebase database ;; for now we'll just grab the data from the main page
        series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 9),
                new DataPoint(d2,4 ),
                new DataPoint(d3, 1),
                new DataPoint(d4, 9),
                new DataPoint(d5,4 ),
                new DataPoint(d6, 1),
                new DataPoint(d7, 5)
        });
        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        graph.setTitle("This Weeks Step Count");
        graph.getViewport().setScrollable(true); //enables scrolling and zooming
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setPadding(64); //padding for axis labels to fit
        series.setSpacing(2);

    }

    protected void onPause(){

        super.onPause();

    }

}
