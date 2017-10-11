package com.xushuai.work.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xushuai.work.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * date:2017/9/28
 * author:徐帅(acer)
 * funcation:用于2秒计时跳转到主界面
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化一个Timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //开始跳转
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}