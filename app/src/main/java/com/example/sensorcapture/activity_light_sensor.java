package com.example.sensorcapture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class activity_light_sensor extends AppCompatActivity implements OnChartValueSelectedListener {

    private LineChart chart;
    SensorDB sensorDB;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        chart = (LineChart) findViewById(R.id.getTheGraph);
        chart.setOnChartValueSelectedListener(this);
        chart = setparams(chart);

        Toolbar toolbar = findViewById(R.id.light_toolbar);
        AppCompatActivity appCompatActivity = activity_light_sensor.this;
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(activity_light_sensor.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });


        ArrayList<Entry> data = new ArrayList<Entry>();
        data = getIntent().getParcelableArrayListExtra("Light_table_values");
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i=0;i<data.size();i++)
        {
            entries.add(new Entry(data.get(i).getX(),data.get(i).getY()));
        }

        LineDataSet dataSet= new LineDataSet(entries, "Time series");
        LineData chart_data = new LineData(dataSet);
        chart.setData(chart_data);

    }

    public LineChart setparams(LineChart chart)
    {
        chart.getDescription().setEnabled(false);
        chart.setNoDataText("No data at this moment.");
        chart.setHighlightPerTapEnabled(false);
        // disable touch gestures
        chart.setTouchEnabled(false);
       // disable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(false);

        XAxis x = chart.getXAxis();
        x.setTextColor(Color.rgb(255,0,0));
//        x.setAvoidFirstLastClipping(true);
//        x.setAxisMinimum(5F);
//        x.setAxisMaximum(100F);
        x.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(true);
        leftAxis.setTextColor(Color.rgb(0,255,0));
        rightAxis.setTextColor(Color.rgb(0,255,0));

        return chart;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}