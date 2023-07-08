package com.example.sensorcapture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class light_sensor_chart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor_chart);

        LineChart chart = (LineChart) findViewById(R.id.getTheGraph);
        setLineChartData(chart);
    }
    public void setLineChartData(LineChart chart){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(20f, 0.0f));
        entries.add(new Entry(30f, 3.0F));
        entries.add(new Entry(40f, 2.0F));
        entries.add(new Entry(50f, 1.0F));
        entries.add(new Entry(60f, 8.0F));

        LineDataSet dataSet= new LineDataSet(entries, "Time series");
        LineData data = new LineData(dataSet);
        chart.setData(data);
    }
}