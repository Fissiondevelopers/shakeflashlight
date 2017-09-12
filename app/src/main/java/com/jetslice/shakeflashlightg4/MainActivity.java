package com.jetslice.shakeflashlightg4;


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private CameraManager manager;
    private boolean isFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.**/

                if(count==4){
                    try {
                        flashlighton(false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                if(count==2) {
                    try {
                        flashlighton(true);

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }




    private void flashlighton(boolean toggle) throws CameraAccessException {
        Toast.makeText(MainActivity.this,"Flash On",Toast.LENGTH_SHORT).show();
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null; // Usually front camera is at 0 position.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraId = camManager.getCameraIdList()[0];
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {

                camManager.setTorchMode(cameraId, toggle);


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void flashLightOff(int c){
        Toast.makeText(MainActivity.this,"flash off",Toast.LENGTH_SHORT).show();
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String cameraId = null; // Usually ront camera is at 0 position.

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraId = camManager.getCameraIdList()[0];
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.d("MainActivity","Camerid not fetching");

        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                camManager.setTorchMode(cameraId, false);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Exception raised",Toast.LENGTH_SHORT).show();
        }


    }

    }









