package com.example.sensorcapture;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LinearLayout ll = new LinearLayout(this) ;
//        ll.setBackgroundColor(Color.WHITE);
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

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            ((TextView)findViewById(R.id.light_sensor)).setText(String.format("%s", event.values[0]));
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}