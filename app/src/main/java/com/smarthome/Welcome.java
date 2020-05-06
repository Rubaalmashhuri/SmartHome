package com.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    TextView Welcome_label;
    String User_name;
    Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Welcome_label = findViewById(R.id.welcome_label);

        SharedPreferences sharedPref = Welcome.this.getSharedPreferences("user_Information", Context.MODE_PRIVATE);
        User_name = sharedPref.getString("user_Name", "");
//        saveLogin=sharedPref.getBoolean("saveLogin",false);
//        if(saveLogin){
//
//        }
        User_name = User_name.toUpperCase();
        User_name= "Welcome "+User_name;
        Welcome_label.setText(User_name);
        final Intent intent = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }

        };
        timer.start();

    }
}
