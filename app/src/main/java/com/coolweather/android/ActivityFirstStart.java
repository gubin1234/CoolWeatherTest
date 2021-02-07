package com.coolweather.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ActivityFirstStart extends AppCompatActivity {
    private final int time = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        ImageView iv_bg = findViewById(R.id.startbg);
        gotoActivity();
        //防止启动后再次点击图片重新启动
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }


    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
    private void gotoActivity(){
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String weatherString = prefs.getString("weather",null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//延时time秒后，将运行如下代码
                if (weatherString != null){
                    Toast.makeText(ActivityFirstStart.this , "wait 1s!" , Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ActivityFirstStart.this,WeatherActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(ActivityFirstStart.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        } , time);
    }
}