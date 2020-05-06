package com.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.Models.Send_User_Data;
import com.smarthome.Models.UserResponse;
import com.smarthome.Network.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button Register;
    EditText User_name,  User_email, User_pass, User_age;
    Client client;
    Send_User_Data send_User_data_;
    int User_id;
    TextView go_login ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initiate();
        client = new Client();
        Register.setOnClickListener(this);
        go_login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()) {
            case R.id.btn_Reg_User:

                String name = User_name.getText().toString().trim();
                String email = User_email.getText().toString().trim();
                String Pass = User_pass.getText().toString().trim();
                String AGE_string = User_age.getText().toString().trim();

                if(name .isEmpty()){
                    User_name.setError("You should type a name");
                }else if(email .isEmpty()){
                    User_email.setError("You should type an email");
                }else if(Pass .isEmpty()){
                    User_pass.setError("You should type a password");
                }else if(AGE_string .isEmpty()){
                    User_age.setError("You should type an age");
                }
                else if(!isEmailValid(email)){
                    User_email.setError("this email is not valid");
                }
                else{


                    Integer age = Integer.parseInt(AGE_string);

                    send_User_data_ = new Send_User_Data();
                    send_User_data_.setAge(age);
                    send_User_data_.setEmail(email);
                    send_User_data_.setPassword(Pass);
                    send_User_data_.setName(name);


                    client.SignUp(send_User_data_, new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.code() == 200) {
                                if(!response.body().getError()) {
                                    Context context = getBaseContext();
                                    SharedPreferences sharedPref = context.getSharedPreferences("user_Information", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    User_id = response.body().getId();
                                    editor.putInt("user_Id", response.body().getId());
                                    editor.putString("user_Name", response.body().getName().trim());
                                    editor.apply();
                                    update_dummy_date();
//                                    startActivity(new Intent(SignUp.this, MainActivity.class));
//                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(SignUp.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(SignUp.this, "connection error :" + response.message(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(SignUp.this, t.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUp.this, "nnnnoooo", Toast.LENGTH_LONG).show();


                        }
                    });




                }




                break;

                case R.id.txt_gologin:
                intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
                finish();
                break;

        }
        }

    private void initiate() {
        Register = findViewById(R.id.btn_Reg_User);
        User_name = findViewById(R.id.edt_R_name);
        User_pass = findViewById(R.id.edt_R_pass);
        User_email = findViewById(R.id.edt_R_mail);
        User_age = findViewById(R.id.edt_R_age);
        go_login=findViewById(R.id.txt_gologin);
    }



    private void update_dummy_date() {

        send_User_data_.setId(User_id);
        send_User_data_.setDoor(1);
        send_User_data_.setLight(1);
        send_User_data_.setWindow(1);
        send_User_data_.setHeater(25);
        send_User_data_.setFridge(1);

        client.UpdateData(send_User_data_, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    if(!response.body().getError()) {
                        startActivity(new Intent(SignUp.this, MainActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Update_data" + response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                    }
                }

                else {
                    Toast.makeText(SignUp.this, "connection error :" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, "connection error :onFailure" , Toast.LENGTH_SHORT).show();
            }
        });
    }




    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}




