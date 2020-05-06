package com.smarthome;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.Models.Send_User_Data;
import com.smarthome.Models.UserResponse;
import com.smarthome.Network.Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class Average extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
public class Average extends AppCompatActivity  {

    int User_id;
    TextView temp_value , fridge_value;
    Client client;
    Send_User_Data send_User_data_;

    float temp_status , fridge_status ;
    EditText edittext ;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor = null;
    final Context c = this;
    public RadioGroup radioGroup;
    LinearLayout date_view ;
    Calendar myCalendar ;


//    id >>>>user id
//    type :
//    t>>>today
//    w>>>this week
//    m>>>this month
//    d>>>>user chose date >>value >>>>>2020-04-22
//    f>>>>fridge

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average);
        initiate();

        sharedPref = Average.this.getSharedPreferences("user_Information", Context.MODE_PRIVATE);
        User_id = sharedPref.getInt("user_Id", 1);
        client = new Client();
        send_User_data_ = new Send_User_Data();
        send_User_data_.setId(User_id);
        send_User_data_.setValue("");
        send_User_data_.setType("f");
        fridgeData();

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getText().equals("Today")){
                    send_User_data_.setType("t");
                    logData();
                    if(date_view.getVisibility()==View.VISIBLE){date_view.setVisibility(View.INVISIBLE);}
                }else if(rb.getText().equals("Last Week")){
                    send_User_data_.setType("w");
                    logData();
                    if(date_view.getVisibility()==View.VISIBLE){date_view.setVisibility(View.INVISIBLE);}
                }else if(rb.getText().equals("Last Month")){
                    send_User_data_.setType("m");
                    logData();
                    if(date_view.getVisibility()==View.VISIBLE){date_view.setVisibility(View.INVISIBLE);}
                }else if(rb.getText().equals("Certain Date")){
                    send_User_data_.setType("d");
                    date_view.setVisibility(View.VISIBLE);
                }


            }
        });


        myCalendar = Calendar.getInstance();

        edittext= (EditText) findViewById(R.id.date_day);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                logData();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Average.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }




    private void logData() {

        client.logData(send_User_data_, new Callback<UserResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    if(!response.body().getError()) {
                        temp_status=response.body().getValue();
                        int val = Math.round(temp_status);
                        temp_value.setText(String.valueOf(val));

//                        temp_value.setText(Float.toString(response.body().getValue()));

                    }
                    else
                    {
                        Toast.makeText(Average.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(Average.this, "connection error ?? :" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(Average.this, "connection error :onFailure??" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initiate() {
        date_view = findViewById(R.id.date_view);
        temp_value = findViewById(R.id.temp_value);
        fridge_value = findViewById(R.id.fridge_value);
    }


    private void updateLabel() {
        String myFormat = "y-MM-dd"; //In which you need put here
//        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
        send_User_data_.setValue(sdf.format(myCalendar.getTime()));
    }



    private void fridgeData() {

        client.logData(send_User_data_, new Callback<UserResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    if(!response.body().getError()) {
                        fridge_status=response.body().getValue();
                        int val = Math.round(fridge_status);
//                        fridge_value.setText(Float.toString(response.body().getValue()));
                        fridge_value.setText(String.valueOf(val));

                    }
                    else
                    {
                        Toast.makeText(Average.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(Average.this, "connection error ?? :" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(Average.this, "connection error :onFailure??" , Toast.LENGTH_SHORT).show();
            }
        });

    }



}
