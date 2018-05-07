package com.example.group5.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class StepTracking extends AppCompatActivity {
    BarChart barChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tracking);

        barChart2 = findViewById(R.id.bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(44f,1));
        barEntries.add(new BarEntry(44f,2));
        barEntries.add(new BarEntry(44f,3));
        barEntries.add(new BarEntry(44f,4));
        barEntries.add(new BarEntry(44f,5));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1:00");
        theDates.add("1:30");
        theDates.add("2:00");
        theDates.add("2:30");
        theDates.add("3:00");
        theDates.add("3:30");

        BarData theData = new BarData((IBarDataSet) theDates,barDataSet);
        barChart2.setData(theData);

        barChart2.setTouchEnabled(true);
        barChart2.setDragEnabled(true);
        barChart2.setScaleEnabled(true);
    }
}
