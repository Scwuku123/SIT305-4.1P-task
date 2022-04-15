package com.example.studytimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
TextView tvHistory;
TextView tvTimer;
    EditText etInput;
    int count=0;
    int status=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHistory=findViewById(R.id.tv_history);
        tvTimer=findViewById(R.id.tv_timer);
        etInput=findViewById(R.id.et_input);
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "history");
        String time = helper.getString("timer");
        String task = helper.getString("task");
        if (time==null) time="00:00";
        if (task==null) task="...";
        tvHistory.setText("you spent "+time+" on "+task+" last time");


    }

    @Override
    protected void onStart(){
        super.onStart();

    }
    public void onClickStart(View view) {
        status=1;
        handler.sendEmptyMessageDelayed(0,1000);
    }

    public void onClickPause(View view) {
      status=2;
        Toast.makeText(this,"timer is puased",Toast.LENGTH_SHORT).show();
    }

    public void onClickEnd(View view) {

        if (etInput.getText().toString().isEmpty()){
            Toast.makeText(this,"task is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        status=3;
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "history");
        helper.putValues(new SharedPreferencesUtils.ContentValue("timer", tvTimer.getText().toString()));
        helper.putValues(new SharedPreferencesUtils.ContentValue("task", etInput.getText().toString()));
        Toast.makeText(this,"save successfully",Toast.LENGTH_SHORT).show();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count++;
            tvTimer.setText(secToTime(count));
            switch (msg.what) {
                case 0:
                    if (status==1)
                    handler.sendEmptyMessageDelayed(0,1000);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public static String secToTime(int seconds) {
        int hour = seconds / 3600;
        int minute = (seconds - hour * 3600) / 60;
        int second = (seconds - hour * 3600 - minute * 60);

        StringBuffer sb = new StringBuffer();
        if (hour > 0 && hour<10) {
            sb.append("0"+hour + ":");
        }else if (hour >= 10) {
            sb.append(hour + ":");
        }else{
            sb.append("00:");
        }
        if (minute >=10) {
            sb.append(minute + ":");
        }else if (minute > 0 && minute<10) {
            sb.append("0"+minute + ":");
        }else{
            sb.append("00:");
        }

        if (second > 0 && second<10) {
            sb.append("0"+second);
        }else if (second >10) {
            sb.append(second);
        }

        return sb.toString();
    }

}