package edu.bd.starttimercountdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button CountDownButton, StartTimerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartTimerButton=(Button) findViewById(R.id.Timer);
        CountDownButton = (Button) findViewById(R.id.CountDown);

        StartTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,TIMER.class);
                startActivity(i);
            }
        });
        CountDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CountDown.class);
                startActivity(i);
            }
        });
    }
}