package com.example.sensorcapture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class activity_accelerometer extends AppCompatActivity implements OnChartValueSelectedListener {

    private LineChart chart_x, chart_y, chart_z;

    public LinearLayout acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        Toolbar toolbar = findViewById(R.id.acc_toolbar);
        AppCompatActivity appCompatActivity = activity_accelerometer.this;
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(activity_accelerometer.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });


        chart_x = (LineChart) findViewById(R.id.acc_x);
        chart_y = (LineChart) findViewById(R.id.acc_y);
        chart_z = (LineChart) findViewById(R.id.acc_z);
        chart_x.setOnChartValueSelectedListener(this);
        chart_y.setOnChartValueSelectedListener(this);
        chart_x.setOnChartValueSelectedListener(this);

        chart_x = setparams(chart_x);
        chart_y = setparams(chart_y);
        chart_z = setparams(chart_z);

        ArrayList<Entry> data_x = new ArrayList<Entry>();
        ArrayList<Entry> data_y = new ArrayList<Entry>();
        ArrayList<Entry> data_z = new ArrayList<Entry>();
        data_x = getIntent().getParcelableArrayListExtra("acc_x_table_values");
        data_y = getIntent().getParcelableArrayListExtra("acc_y_table_values");
        data_z = getIntent().getParcelableArrayListExtra("acc_z_table_values");


        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries_2 = new ArrayList<>();
        ArrayList<Entry> entries_3 = new ArrayList<>();
        for(int i=0;i<data_x.size();i++)
        {
            entries.add(new Entry(data_x.get(i).getX(),data_x.get(i).getY()));
            entries_2.add(new Entry(data_y.get(i).getX(),data_y.get(i).getY()));
            entries_3.add(new Entry(data_z.get(i).getX(),data_z.get(i).getY()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Time series");
        LineData chart_data = new LineData(dataSet);
        chart_x.setData(chart_data);

        LineDataSet dataSet2 = new LineDataSet(entries_2, "Time series");
        LineData chart_data2 = new LineData(dataSet2);
        chart_y.setData(chart_data2);

        LineDataSet dataSet3 = new LineDataSet(entries_3, "Time series");
        LineData chart_data3 = new LineData(dataSet3);
        chart_z.setData(chart_data3);

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

    }

    @Override
    public void onNothingSelected() {

    }
}