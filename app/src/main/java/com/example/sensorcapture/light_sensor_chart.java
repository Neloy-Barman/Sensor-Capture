package com.example.sensorcapture;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class light_sensor_chart extends AppCompatActivity implements OnChartValueSelectedListener {

    private LineChart chart;
    SensorDB sensorDB;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor_chart);

        chart = (LineChart) findViewById(R.id.getTheGraph);
        chart.setOnChartValueSelectedListener(this);
        chart = setparams(chart);


        ArrayList<Entry> data = new ArrayList<Entry>();
        data = getIntent().getParcelableArrayListExtra("Light_table_values");
//        for(int j=0;j<data.size();j++)
//        {
//            Log.d("Normal", "I'm running from light_sensor_chart");
//            String s = String.format("%s", data.get(j));
//            Log.d("From table", s);
//        }
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
        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
        xl.setTextColor(Color.rgb(255,0,0));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.rgb(0,255,0));
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        return chart;
    }
//            entries.add(new Entry(20f, 0.0f));
////        entries.add(new Entry(30f, 3.0F));
////        entries.add(new Entry(40f, 2.0F));
////        entries.add(new Entry(50f, 1.0F));
////        entries.add(new Entry(60f, 8.0F));
//    public void getDataValues(){
//        ArrayList<Entry> dataVals = new ArrayList<>();
//        String[] columns = {"x_values", "y_values"};
//        Cursor cursor = sqLiteDatabase.query("mytable", columns, null, null, null, null, null);
//        for(int i=0;i<cursor.getCount();i++)
//        {
//            cursor.moveToNext();
//            dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(1)));
//        }
//        Log.d("Normal", "I'm running from light_sensor_chart");
//        for (int j=0;j<dataVals.size();j++)
//        {
//            String s = String.format("%s", dataVals.get(j));
//            Log.d("From table", s);
//        }
//    }

//    private void addEntry() {
//
//        LineData data = chart.getData();
//
//        if (data != null) {
//
//            ILineDataSet set = data.getDataSetByIndex(0);
//            // set.addEntry(...); // can be called as well
//
//            if (set == null) {
//                set = createSet();
//                data.addDataSet(set);
//            }
//
//            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
//            data.notifyDataChanged();
//
//            // let the chart know it's data has changed
//            chart.notifyDataSetChanged();
//
//            // limit the number of visible entries
//            chart.setVisibleXRange(6, 120);
////             chart.setVisibleYRange(30, AxisDependency.LEFT);
//
//            // move to the latest entry
//            chart.moveViewToX(data.getEntryCount()-7);
//
//            // this automatically refreshes the chart (calls invalidate())
////             chart.moveViewTo(data.getXValCount()-7, 55f,
////             YAxis.AxisDependency.LEFT);
//        }
//    }

//    @Override
//    protected void onResume() {
//
//        super.onResume();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<120;i++)
//                {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            addEntry();
//                        }
//                    });
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }).start();
//    }

//    private LineDataSet createSet() {
//
//        LineDataSet set = new LineDataSet(null, "Dynamic Data");
//        set.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set.setColor(ColorTemplate.getHoloBlue());
//        set.setCircleColor(Color.WHITE);
//        set.setLineWidth(2f);
//        set.setCircleRadius(4f);
//        set.setFillAlpha(65);
//        set.setFillColor(ColorTemplate.getHoloBlue());
//        set.setHighLightColor(Color.rgb(244, 117, 117));
//        set.setValueTextColor(Color.WHITE);
//        set.setValueTextSize(9f);
//        set.setDrawValues(false);
//        return set;
//    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

//    public void setLineChartData(LineChart chart){
//        ArrayList<Entry> entries = new ArrayList<>();
//        String[] columns = {"x_values", "y_values"};
//        Cursor cursor = sqLiteDatabase.query("mytable", columns, null, null, null, null, null);
//        for(int i=0;i<cursor.getCount();i++)
//        {
//            cursor.moveToNext();
//            entries.add(new Entry(cursor.getFloat(0), cursor.getFloat(1)));
//        }
////        entries.add(new Entry(20f, 0.0f));
////        entries.add(new Entry(30f, 3.0F));
////        entries.add(new Entry(40f, 2.0F));
////        entries.add(new Entry(50f, 1.0F));
////        entries.add(new Entry(60f, 8.0F));
//
//        LineDataSet dataSet= new LineDataSet(entries, "Time series");
//        LineData data = new LineData(dataSet);
//        chart.setData(data);
//    }



//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (thread != null) {
//            thread.interrupt();
//        }
//    }
}