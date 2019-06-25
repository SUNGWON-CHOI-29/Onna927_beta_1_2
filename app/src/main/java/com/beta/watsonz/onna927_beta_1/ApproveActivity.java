package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by watsonz on 2016-01-28.
 */
public class ApproveActivity extends Activity{
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 1500;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    SensorManager sm;
    SensorEventListener accL;
    Sensor accSensor;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_approve);
        sm = (SensorManager)this.getSystemService(this.SENSOR_SERVICE);    // SensorManager 인스턴스를 가져옴
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);    // 가속도 센서
        accL = new accListener();       // 가속도 센서 리스너 인스턴스
        registerSensorListener();
    }

    private class accListener implements SensorEventListener {
        public void onSensorChanged(SensorEvent event) {  // 가속도 센서 값이 바뀔때마다 호출됨
                long currentTime = System.currentTimeMillis();
                long gabOfTime = (currentTime - lastTime);
                if (gabOfTime > 100) {
                    lastTime = currentTime;
                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        Toast.makeText(getApplicationContext(), "방문이 확인되었습니다.", Toast.LENGTH_SHORT).show();
                        unregisterSensorListener();
                        finish();
                    }

                    lastX = event.values[DATA_X];
                    lastY = event.values[DATA_Y];
                    lastZ = event.values[DATA_Z];
                }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
    private void registerSensorListener() {
        sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterSensorListener() {
        sm.unregisterListener(accL);    // unregister orientation listener
    }
}
