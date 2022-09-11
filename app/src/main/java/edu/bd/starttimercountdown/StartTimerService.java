package edu.bd.starttimercountdown;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class StartTimerService extends Service {
    private final static String TAG = "StartTimerService";
    public static final String START_TIMER = "edu.bd.starttimercountdown.TIMER";
    Intent bi = new Intent(START_TIMER);


    private Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 100;
    private String hours;
    private String minutes;
    private String seconds;
    private String milliseconds;
    private long secs,mins,hrs;
    private boolean stopped = false;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        if (stopped) {
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else {
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Bundle b = intent.getExtras();
        if(b != null) {
            Long countTime = b.getLong("time");
            System.out.println("Service time"+countTime);
            try {
                elapsedTime = countTime;
            } catch (NumberFormatException ex){}

        }
        startTime = System.currentTimeMillis() - elapsedTime;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(startTimer);
        stopped = true;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateTimer (float time){

        secs = (long)(time/1000);
        mins = (long)((time/1000)/60);
        hrs = (long)(((time/1000)/60)/60);

        secs = secs % 60;
        seconds=String.valueOf(secs);
        if(secs == 0){
            seconds = "00";
        }
        if(secs <10 && secs > 0){
            seconds = "0"+seconds;
        }

        mins = mins % 60;
        minutes = String.valueOf(mins);
        if(mins == 0){
            minutes = "00";
        }
        if(mins <10 && mins > 0){
            minutes = "0"+ minutes;
        }


        hours=String.valueOf(hrs);
        if(hrs == 0){
            hours = "00";
        }
        if(hrs <10 && hrs > 0){
            hours = "0"+hours;
        }

        milliseconds = String.valueOf((long)time);
        if(milliseconds.length()==2){
            milliseconds = "0"+milliseconds;
        }
        if(milliseconds.length()<=1){
            milliseconds = "00";
        }
        else milliseconds = milliseconds.substring(milliseconds.length()-2, milliseconds.length()-1);

        bi.putExtra("hours", hours);
        bi.putExtra("minutes", minutes);
        bi.putExtra("seconds", seconds);
        bi.putExtra("milliseconds", milliseconds);
        long time1 = (long) time;
        bi.putExtra("time", time1);
        sendBroadcast(bi);

    }

    private Runnable startTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };
}