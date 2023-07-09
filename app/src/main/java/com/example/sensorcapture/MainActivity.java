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

        SensorManager sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensor_manager != null)
        {
            Sensor light_sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Sensor proximity_sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            Sensor accelerometer = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Sensor gyroscope = sensor_manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if(light_sensor != null && proximity_sensor != null && accelerometer != null && gyroscope != null)
            {
                sensor_manager.registerListener(this,light_sensor, SensorManager.SENSOR_DELAY_NORMAL);
                sensor_manager.registerListener(this,proximity_sensor, SensorManager.SENSOR_DELAY_NORMAL);
                sensor_manager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensor_manager.registerListener(this,gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else{
            Toast.makeText(this, "Sensors not available", Toast.LENGTH_SHORT).show();
        }

        CardView lig = (CardView)findViewById(R.id.card_view_1);
        CardView prox = (CardView)findViewById(R.id.card_view_2);
        CardView acc = (CardView)findViewById(R.id.card_view_3);
        CardView gyr = (CardView)findViewById(R.id.card_view_4);

        sensorDB = new SensorDB(this);
        sqLiteDatabase = sensorDB.getWritableDatabase();

        lig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_light_db = new Intent(MainActivity.this, light_sensor_chart.class);
//                go_to_light_db.putParcelableArrayListExtra()
                go_to_light_db.putParcelableArrayListExtra("Light_table_values", sensorDB.retrieveTable(Queries.light));
                startActivity(go_to_light_db);
                finish();
            }
        });

        prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_prox_db = new Intent(MainActivity.this, activity_proximity_senor_chart.class);
                go_to_prox_db.putParcelableArrayListExtra("prox_table_values", sensorDB.retrieveTable(Queries.proximity));
                startActivity(go_to_prox_db);
                finish();
            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acc_db = new Intent(MainActivity.this, activity_accelerometer.class);
                acc_db.putParcelableArrayListExtra("acc_x_table_values", sensorDB.retrieveTable(Queries.acce_x));
                acc_db.putParcelableArrayListExtra("acc_y_table_values", sensorDB.retrieveTable(Queries.acce_y));
                acc_db.putParcelableArrayListExtra("acc_z_table_values", sensorDB.retrieveTable(Queries.acce_z));
                startActivity(acc_db);
                finish();
            }
        });

        gyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gyr_db = new Intent(MainActivity.this, activity_gyroscope.class);
                gyr_db.putParcelableArrayListExtra("gyr_x_table_values", sensorDB.retrieveTable(Queries.gyro_x));
                gyr_db.putParcelableArrayListExtra("gyr_y_table_values", sensorDB.retrieveTable(Queries.gyro_y));
                gyr_db.putParcelableArrayListExtra("gyr_z_table_values", sensorDB.retrieveTable(Queries.gyro_z));
                startActivity(gyr_db);
                finish();
            }
        });
    }
    long time_l = System.currentTimeMillis();
    long time_p = System.currentTimeMillis();
    long time_a = System.currentTimeMillis();
    long time_g = System.currentTimeMillis();
    long time_l_p = time_l;
    long time_p_p = time_p;
    long time_a_p = time_a;
    long time_g_p = time_g;
    float t_l = 1F;
    float t_p = 1F;
    float t_a = 1F;
    float t_g = 1F;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            ((TextView)findViewById(R.id.light_sensor)).setText(String.format("%s", event.values[0]));
            time_l = System.currentTimeMillis();
            long elapesed_time = time_l-time_l_p;
            if(elapesed_time >= 60000){
                Log.d("My time", String.format("Time elapsed for light sensor %s", elapesed_time));
                time_l_p = time_l;
                sensorDB.insertData(Queries.light, t_l, event.values[0]);
                 t_l = (elapesed_time / 60000) + 1F;
//                getDataValues();
            }
        }
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY)
        {
            ((TextView)findViewById(R.id.proximity_sensor)).setText(String.format("%s", event.values[0]));
            time_p = System.currentTimeMillis();
            if(time_p-time_p_p >= 60000){
                Log.d("My time", String.format("Time elapsed for proximity sensor %s", time_p-time_p_p));
                time_p_p = time_p;
                sensorDB.insertData(Queries.proximity, t_p, event.values[0]);
                t_p = (time_p-time_p_p / 60000) + 1F;
            }
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            ((TextView)findViewById(R.id.accelerometer)).setText(String.format("Ax: %s\nAy: %s\nAz: %s", event.values[0], event.values[1], event.values[2]));
            time_a = System.currentTimeMillis();
            if(time_a-time_a_p >= 60000){
                Log.d("My time", String.format("Time elapsed for accelerometer  %s", time_a-time_a_p));
                time_a_p = time_a;
                sensorDB.insertData(Queries.acce_x, t_p, event.values[0]);
                sensorDB.insertData(Queries.acce_y, t_p, event.values[1]);
                sensorDB.insertData(Queries.acce_z, t_p, event.values[2]);
                t_a = (time_a-time_a_p / 60000) + 1F;
            }
        }
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            ((TextView)findViewById(R.id.gyroscope)).setText(String.format("Gx: %s\nGy: %s\nGz: %s", event.values[0],event.values[1], event.values[2]));
            time_g = System.currentTimeMillis();
            if(time_g-time_g_p >= 60000){
                Log.d("My time", String.format("Time elapsed for gyroscope sensor %s", time_g-time_g_p));
                time_g_p = time_g;
                sensorDB.insertData(Queries.gyro_x, t_g, event.values[0]);
                sensorDB.insertData(Queries.gyro_y, t_g, event.values[1]);
                sensorDB.insertData(Queries.gyro_z, t_g, event.values[2]);
                t_g = (time_g-time_g_p / 60000) + 1F;
            }
        }
    }

//    public void getDataValues(){
//        ArrayList<Entry> dataVals = new ArrayList<>();
//        String[] columns = {"x_values", "y_values"};
//        Cursor cursor = sqLiteDatabase.query("mytable", columns, null, null, null, null, null);
//        for(int i=0;i<cursor.getCount();i++)
//        {
//            cursor.moveToNext();
//            dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(1)));
//        }
//        for (int j=0;j<dataVals.size();j++)
//        {
//            String s = String.format("%s", dataVals.get(j));
//            Log.d("From table", s);
//        }
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}