package com.example.suraj.spiderappdevtask2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    TextView t;
    Ringtone r;
    ProgressBar progressBar;
    MyCountDownTimer myCountDownTimer;
    Handler handler,h;
    Runnable ru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=  (TextView) findViewById(R.id.tv);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > -4 && event.values[0]<4)
        {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAlarm();
                        }
                    },10000);

            myCountDownTimer = new MyCountDownTimer(10000,1000);
            myCountDownTimer.start();
            t.setText("Neeaaaarrrr!!");
        }


        else
        {
            if (r!=null) r.stop();
            if (handler!=null) handler.removeCallbacksAndMessages(null);
            if (myCountDownTimer!=null) myCountDownTimer.cancel();
            progressBar.setProgress(0);
            t.setText("Faaaaarrrrr!!");
            //if (myCountDownTimer!=null) myCountDownTimer.cancel();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void playAlarm ()
    {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);

            progressBar.setProgress(progressBar.getMax()-progress);
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
        }
    }


}
