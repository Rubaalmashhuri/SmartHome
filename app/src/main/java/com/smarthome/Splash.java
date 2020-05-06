package com.smarthome;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView Welcome_label;
    String User_name;
    Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Welcome_label = findViewById(R.id.welcome_label);
        SharedPreferences sharedPref = Splash.this.getSharedPreferences("user_Information", Context.MODE_PRIVATE);
        User_name = sharedPref.getString("user_Name", "");
        saveLogin=sharedPref.getBoolean("saveLogin",false);
        User_name = User_name.toUpperCase();
        final Intent intent;
        if(!saveLogin){
            intent = new Intent(this, LogIn.class);
        }
        else{
            User_name= "Welcome back "+User_name;
            Welcome_label.setText(User_name);
            intent = new Intent(this, MainActivity.class);
        }
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

