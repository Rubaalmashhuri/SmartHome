package com.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.Models.Send_User_Data;
import com.smarthome.Models.UserResponse;
import com.smarthome.Network.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    EditText User_Mail ,  User_Pass ;
    Button btn_LogIn ;
    TextView go_Register ;
    Client client;
    Send_User_Data send_User_data_;
    CheckBox saveLoginCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiate();
        client = new Client();
        btn_LogIn.setOnClickListener(this);
        go_Register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()) {
            case R.id.btn_login:
                send_User_data_ = new Send_User_Data();
                send_User_data_.setEmail(User_Mail.getText().toString().trim());
                send_User_data_.setPassword(User_Pass.getText().toString().trim());


                client.Login(send_User_data_, new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if (response.code() == 200) {
//                            user.setName(response.body().getUid().getId());

                            if(!response.body().getError()) {
                                Context context = getBaseContext();
                                    SharedPreferences sharedPref = context.getSharedPreferences("user_Information", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("user_Id", response.body().getId());
                                    editor.putString("user_Name", response.body().getName().trim());
                                    editor.putString("user_logIn","IN");
                                if (saveLoginCheckBox.isChecked()) {
                                    editor.putBoolean("saveLogin", true);
                                } else {
                                    editor.putBoolean("saveLogin", false);
                                }
                                    editor.apply();
                                    startActivity(new Intent(LogIn.this, MainActivity.class));
                                    finish();

                            }
                            else
                            {
                                Toast.makeText(LogIn.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                            }
                        }

                        else {
                            Toast.makeText(LogIn.this, "connection error :" + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(LogIn.this, t.toString() , Toast.LENGTH_SHORT).show();
                        Toast.makeText(LogIn.this, "noooo", Toast.LENGTH_LONG).show();
                    }
                } );

                break;

            case R.id.txt_GoRegister:
                intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void initiate() {
        User_Mail=findViewById(R.id.edt_mail);
        User_Pass=findViewById(R.id.edt_password);
        btn_LogIn=findViewById(R.id.btn_login);
        go_Register=findViewById(R.id.txt_GoRegister);
        saveLoginCheckBox = findViewById(R.id.keepmeLogIn);
    }

}
