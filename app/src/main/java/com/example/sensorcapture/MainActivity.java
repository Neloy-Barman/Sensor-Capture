package com.example.sensorcapture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Entity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorDB sensorDB;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout li=(LinearLayout)findViewById(R.id.main_linear);
        li.setBackgroundColor(Color.parseColor("#FFFFFF"));

        SensorManager sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensor_manager != null)
        {
            Sensor light_sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Sensor proximity_sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            Sensor accelerometer = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Sensor gyroscope = sensor_manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if(light_sensor != null && proximity_sensor != null && accelerometer != null && gyroscope != null)
            {
//                int delay = 300000;
                sensor_manager.registerListener(this,light_sensor, SensorManager.SENSOR_DELAY_NORMAL);
                sensor_manager.registerListener(this,proximity_sensor, SensorManager.SENSOR_DELAY_NORMAL);
                sensor_manager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensor_manager.registerListener(this,gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else{
            Toast.makeText(this, "Sensors not available", Toast.LENGTH_SHORT).show();
        }

        CardView light = (CardView)findViewById(R.id.light_card);

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_light_db = new Intent(MainActivity.this, light_sensor_chart.class);
                startActivity(go_to_light_db);
                finish();
            }
        });


        sensorDB = new SensorDB(this);
        sqLiteDatabase = sensorDB.getWritableDatabase();

    }
    long time = System.currentTimeMillis();
    long time_1 = time;
    float t = 1;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            ((TextView)findViewById(R.id.light_sensor)).setText(String.format("%s", event.values[0]));
            long time = System.currentTimeMillis();
            long elapesed_time = time-time_1;
            if(elapesed_time >= 5000){
                Log.d("My time", String.format("Time elapsed for light sensor %s", elapesed_time));
                time_1 = time;

                sensorDB.insertData(event.values[0], t);
                t++;
                getDataValues();
            }
        }
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY)
        {
            ((TextView)findViewById(R.id.proximity_sensor)).setText(String.format("%s", event.values[0]));
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            ((TextView)findViewById(R.id.accelerometer)).setText(String.format("Ax: %s\nAy: %s\nAz: %s", event.values[0], event.values[1], event.values[2]));
        }
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            ((TextView)findViewById(R.id.gyroscope)).setText(String.format("Gx: %s\nGy: %s\nGz: %s", event.values[0],event.values[1], event.values[2]));
        }
    }

    public void getDataValues(){
        ArrayList<Entry> dataVals = new ArrayList<>();
        String[] columns = {"x_values", "y_values"};
        Cursor cursor = sqLiteDatabase.query("mytable", columns, null, null, null, null, null);
        for(int i=0;i<cursor.getCount();i++)
        {
            cursor.moveToNext();
            dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(1)));
        }
        for (int j=0;j<dataVals.size();j++)
        {
            String s = String.format("%s", dataVals.get(j));
            Log.d("From table", s);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}