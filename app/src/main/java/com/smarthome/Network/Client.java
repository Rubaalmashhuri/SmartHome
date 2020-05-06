package com.smarthome.Network;

import com.smarthome.Models.Send_User_Data;
import com.smarthome.Models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//Retrofit is a REST Client library (Helper Library) used in Android and Java
// to create an HTTP request and also to process the HTTP response from a REST API.

public class Client {

    public static final  String BASE_URL = "http://35.193.75.133/iot/";

    private static Retrofit retrofit = null;

    APIInterface apiInterface = getClient().create(APIInterface.class);

//how to define josn to pass the response
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //functions to be used in java classes take variables from java and pass it to api interface to connect to web service
    public void Login(Send_User_Data send_User_data_, Callback<UserResponse> callback ){
        Call<UserResponse> call = apiInterface.loginUser(send_User_data_.getEmail(), send_User_data_.getPassword());
        call.enqueue(callback);
    }


    public void SignUp(Send_User_Data send_User_data_ , Callback<UserResponse> callback ){
        Call<UserResponse> call = apiInterface.RegisterUser(send_User_data_.getName(),send_User_data_.getEmail(),send_User_data_.getPassword(),send_User_data_.getAge());
        call.enqueue(callback);
    }


    public void ReadData(Send_User_Data send_User_data_, Callback<UserResponse> callback ){
        Call<UserResponse> call = apiInterface.readData(send_User_data_.getId());
        call.enqueue(callback);
    }

    public void UpdateData(Send_User_Data send_User_data_ , Callback<UserResponse> callback ){
        Call<UserResponse> call = apiInterface.updateData(send_User_data_.getId(),send_User_data_.getDoor(),send_User_data_.getWindow(),send_User_data_.getLight(),send_User_data_.getFridge(),send_User_data_.getHeater());
        call.enqueue(callback);
    }


    public void logData(Send_User_Data send_User_data_ , Callback<UserResponse> callback ){
        Call<UserResponse> call = apiInterface.logData(send_User_data_.getId(),send_User_data_.getType(),send_User_data_.getValue());
        call.enqueue(callback);
    }
}
