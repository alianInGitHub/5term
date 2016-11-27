package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private TextView text;
    private TextView stepCount;
    private TextView test;

    private SensorManager sensorManager;
    private SensorManager sensorManager2;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private int stepCounter = 0;

    private long lastUpdateAccel = 0, lastUpdateGyr = 0;
    private float[] rAccel = new float[2];
    private float accelThreashold = 9;
    private boolean accelZeroCrossed = false;
    private float accelSmoothed = 0;
    private boolean accelStepDetected = false;

    private float[] rGyro = new float[2];
    private float gyroThreashold = 0.9f;
    private boolean gyroZeroCrossed = false;
    private float gyroSmoothed = 0;
    private boolean gyroStepDetected = false;

    private float stepLength = 0.45f * 1.64f;
    private int smoothing = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Distance measure");
        test = (TextView)findViewById(R.id.textView);
        text = (TextView)findViewById(R.id.distance);
        text.setText("0 m");
        rGyro[0] = rGyro[1] = rAccel[0] = rAccel[1] = 0;
        stepCount = (TextView)findViewById(R.id.stepsCount);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManager2 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager2.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private float smoothedValue(float val, long diffTime, boolean typeAccel){
        float smoothed;
        if(typeAccel){
            smoothed = accelSmoothed;
        } else
        smoothed = gyroSmoothed;
        smoothed += diffTime * (val - smoothed) / smoothing;
        return smoothed;
    }

    private float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long currTime = System.currentTimeMillis();
            if((currTime - lastUpdateAccel) > 100){
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                rAccel[1] = (float)Math.sqrt(x * x + y * y + z * z) - 9.81f;
                rAccel[1] = smoothedValue(rAccel[1], currTime - lastUpdateAccel, true);
                if((rAccel[1] > 0) && (rAccel[0] <= 0)) {
                    rAccel[0] = rAccel[1];
                    accelZeroCrossed = true;
                    lastUpdateAccel = currTime;
                } else
                if((rAccel[1] <= 0) && (rAccel[0] > 0)){
                    rAccel[0] = rAccel[1];
                    accelZeroCrossed = false;
                    lastUpdateAccel = currTime;
                } else
                    if((accelZeroCrossed) && (rAccel[1] >= accelThreashold)){
                        accelStepDetected = true;
                        accelZeroCrossed = false;
                    }
            }
        }
        if(mySensor.getType() == Sensor.TYPE_GYROSCOPE){
            long currTime = System.currentTimeMillis();
            if(currTime - lastUpdateGyr > 100){
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                rGyro[1] = (float)Math.sqrt(x * x + y * y + z * z);
                test.setText(String.valueOf(rGyro[1]));
                rGyro[1] = smoothedValue(rGyro[1], currTime - lastUpdateGyr, false);
                if((rGyro[1] > 0) && (rGyro[0] <= 0)) {
                    gyroZeroCrossed = true;
                    rGyro[0] = rGyro[1];
                    lastUpdateGyr = currTime;
                } else
                    if((rGyro[1] <= 0) && (rGyro[0] > 0)){
                        rGyro[0] = rGyro[1];
                        gyroZeroCrossed = false;
                        lastUpdateGyr = currTime;
                    } else {
                        if(gyroZeroCrossed && (rGyro[1] >= gyroThreashold)){
                            gyroStepDetected = true;
                        }
                    }
            }
        }
        if(gyroStepDetected || accelStepDetected){
            stepCounter++;
            stepCount.setText(stepCounter + " steps");
            float val = round(stepCounter * stepLength, 1);
            text.setText(String.valueOf(val) + " m");
            gyroStepDetected = accelStepDetected = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
