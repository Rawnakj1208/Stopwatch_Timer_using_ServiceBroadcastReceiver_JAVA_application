package edu.bd.starttimercountdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CountDown extends AppCompatActivity {
    private TextView textViewTimer;
    private Button buttonStart, buttonStop, buttonPause;
    long time = 20000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        textViewTimer = (TextView)findViewById(R.id.text);
        buttonStart = (Button)findViewById(R.id.btn1);
        buttonStop = (Button)findViewById(R.id.btn2);
        buttonPause = (Button) findViewById(R.id.btn3);


        Intent i = new Intent(CountDown.this, CountDownService.class);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("time",time);
                System.out.println("Expected time"+time);
                startService(i);

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(i);
                textViewTimer.setText("00:00:00.0");
                time = 0;
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(i);
            }
        });
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(CountDownService.START_TIMER));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(br);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, CountDownService.class));
        super.onDestroy();
    }


    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            String hours = intent.getStringExtra("hours");
            String minutes = intent.getStringExtra("minutes");
            String seconds = intent.getStringExtra("seconds");
            String milliseconds = intent.getStringExtra("milliseconds");
            time = intent.getLongExtra("time",0);
            textViewTimer.setText(hours + ":" + minutes + ":" + seconds + "." + milliseconds);
        }
    }
}


