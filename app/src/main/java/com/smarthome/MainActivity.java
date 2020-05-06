package com.smarthome;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Process;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.graphics.drawable.ArgbEvaluator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.Models.Send_User_Data;
import com.smarthome.Models.UserResponse;
import com.smarthome.Network.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int User_id;
    String temp ;
    TextView door_label,window_label,light_label,heat_label , fridge_label;
    ImageView door,window,light , fridge;
    Button btnAverage ;

    Client client;
    Send_User_Data send_User_data_;

    int door_status ,window_status,light_status , fridge_status;
    int door_update ,window_update,light_update,fridge_update;
    float temp_status,temp_update;


    ObjectAnimator textColorAnim;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor = null;
    final Context c = this;


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiate();
        textColorAnim = ObjectAnimator.ofInt(heat_label, "textColor", Color.RED, Color.BLUE);
        sharedPref = MainActivity.this.getSharedPreferences("user_Information", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        User_id = sharedPref.getInt("user_Id", 1);
        client = new Client();
        send_User_data_ = new Send_User_Data();
        send_User_data_.setId(User_id);
        refreshData();
        door.setOnClickListener(this);
        window.setOnClickListener(this);
        light.setOnClickListener(this);
        fridge.setOnClickListener(this);
        heat_label.setOnClickListener(this);

        btnAverage.setOnClickListener(this);


        final Handler handler = new Handler();
        final int delay = 4000; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                refreshData();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.heat_value:
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                temp=userInputDialogEditText.getText().toString();
                                temp_status=Integer.parseInt(temp);
                                heat_label.setText(temp_status +" °C");
                                updateData();

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();



                break;


            case R.id.fridge:
                if(fridge_status==1){
                    fridge.setImageResource(R.drawable.f1);
                    fridge_label.setText("fridge is opened");
                    fridge_status=0;
                }else{
                    fridge.setImageResource(R.drawable.f1c);
                    fridge_label.setText("fridge is closed");
                    fridge_status=1;
                }
                updateData();
                break;


            case R.id.door:
                if(door_status==1){
                    door.setImageResource(R.drawable.opened_door1);
                    door_label.setText("Door is opened");
                    door_status=0;
                }else{
                    door.setImageResource(R.drawable.closed_door1);
                    door_label.setText("Door is closed");
                    door_status=1;
                }
                updateData();
                break;
            case R.id.window:
                if(window_status==1){
                    window.setImageResource(R.drawable.opened_win);
                    window_label.setText("Window is opened");
                    window_status=0;
                }else{
                    window.setImageResource(R.drawable.closed_win);
                    window_label.setText("Window is closed");
                    window_status=1;
                }
                updateData();
                break;

            case R.id.light:
                if(light_status==1){
                    light.setImageResource(R.drawable.light_on);
                    light_label.setText("light is ON");
                    light_status=0;
                }else{
                    light.setImageResource(R.drawable.light_off);
                    light_label.setText("light is OFF");
                    light_status=1;
                }
                updateData();
                break;

            case R.id.btnAverage:
                Intent intent = new Intent(MainActivity.this, Average.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage("Please select LogOut or just Exit")
                .setCancelable(false)
                .setPositiveButton("LogOut",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        editor.putBoolean("saveLogin", false);
                        editor.remove("user_Id");
                        editor.remove("user_Name");
                        editor.apply();
                        MainActivity.this.finish();

                    }
                })
                .setNegativeButton("Exit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Toast.makeText(MainActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                        MainActivity.this.finish();
                    }
                })
                .setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
//                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                })
        ;
        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    private void initiate() {
        door_label = findViewById(R.id.door_label);
        window_label = findViewById(R.id.window_label);
        light_label = findViewById(R.id.light_label);
        heat_label = findViewById(R.id.heat_value);
        fridge_label = findViewById(R.id.fridge_label);
        btnAverage = findViewById(R.id.btnAverage);



        door = findViewById(R.id.door);
        window = findViewById(R.id.window);
        light = findViewById(R.id.light);
        fridge = findViewById(R.id.fridge);

    }

    @Override
    public void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    private void refreshData() {
        client.ReadData(send_User_data_, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    if(!response.body().getError()) {
                        door_status=response.body().getDoor();
                        window_status=response.body().getWindow();
                        light_status=response.body().getLight();
                        temp_status =response.body().getHeater();
                        fridge_status =response.body().getFridge();
                        SetIcons(door_status,window_status,light_status, temp_status ,fridge_status);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(MainActivity.this, "connection error :" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "connection error :onFailure" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {
        door_update=door_status;
        window_update=window_status;
        light_update=light_status;
        temp_update= temp_status;
        fridge_update= fridge_status;

        send_User_data_.setDoor(door_update);
        send_User_data_.setLight(light_update);
        send_User_data_.setWindow(window_update);
        send_User_data_.setHeater(temp_update);
        send_User_data_.setFridge(fridge_update);

        client.UpdateData(send_User_data_, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    if(!response.body().getError()) {
                        Toast.makeText(MainActivity.this, "Done Update", Toast.LENGTH_LONG).show();
                        door_status=response.body().getDoor();
                        window_status=response.body().getWindow();
                        light_status=response.body().getLight();
                        temp_status =response.body().getHeater();
                        fridge_status =response.body().getFridge();
                        SetIcons(door_status,window_status,light_status, temp_status ,fridge_status);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(MainActivity.this, "connection error :" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "connection error :onFailure" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("RestrictedApi")
    private void SetIcons(int door_status, int window_status, int light_status , float temp , int fridge_status ) {

        if(fridge_status==0){
            fridge.setImageResource(R.drawable.f1);
            fridge_label.setText("fridge is opened");
            fridge.setTag(0);
        }else{
            fridge.setImageResource(R.drawable.f1c);
            fridge_label.setText("fridge is closed");
            fridge.setTag(1);
        }


        if(door_status==0){
            door.setImageResource(R.drawable.opened_door1);
            door_label.setText("Door is opened");
            door.setTag(0);
        }else{
            door.setImageResource(R.drawable.closed_door1);
            door_label.setText("Door is closed");
            door.setTag(1);
        }
        if(window_status==0){
            window.setImageResource(R.drawable.opened_win);
            window_label.setText("Window is opened");
            window.setTag(0);
        }else{
            window.setImageResource(R.drawable.closed_win);
            window_label.setText("Window is closed");
            window.setTag(1);
        }
        if(light_status==0){
            light.setImageResource(R.drawable.light_on);
            light_label.setText("light is ON");
            light.setTag(0);
        }else{
            light.setImageResource(R.drawable.light_off);
            light_label.setText("light is OFF");
            light.setTag(1);
        }
        if(temp>=38){
            heat_label.setText(temp +" °C");

            textColorAnim.setDuration(1000);
            textColorAnim.setEvaluator(new ArgbEvaluator());
            textColorAnim.setRepeatCount(Animation.INFINITE);
            textColorAnim.setRepeatMode(ValueAnimator.REVERSE);
            textColorAnim.start();


        }else {
            textColorAnim.cancel();
            heat_label.setTextColor(getResources().getColor(R.color.White));
            heat_label.setText(temp + " °C");
        }
    }

}
