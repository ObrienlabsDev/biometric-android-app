package dev.obrienlabs.biometric.android.business;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;

import java.util.List;

public class SensorService {

    private SensorManager mSensorManager;
    LocationManager lm;
    private Sensor mPressure;
    private Sensor mTemperature;
    private Sensor mGravity;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private Sensor mLight;
    private Sensor mLinearAcceleration;
    private Sensor mMagneticField;
    //private Sensor mOrientation;
    private Sensor mProximity;
    private Sensor mRelativeHumidity;
    private Sensor mRotationVector;
    private int _heartRate = 1;

    private void init() {
        //mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        
        //lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); // API 14
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY); // API 9
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); // API 9
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION); // deprecated
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mRelativeHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY); // API 14
        mRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR); // API 9
        // 20151206: api 20 to 22 move to onStartCommand()
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3f, this);
    }
}
