package com.smarthome.Network;


// defines
import com.smarthome.Models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


//Every method inside an interface represents one possible API call.
// It must have a HTTP annotation (GET, POST, etc.) to specify the
// request type and the relative URL.
// The return value wraps the response in a Call object with the type of the expected result.
public interface APIInterface {

    @FormUrlEncoded
    @POST("register.php")
     Call<UserResponse> RegisterUser(
            @Field("name") String name ,
            @Field("email") String email ,
            @Field("password") String password,
            @Field("age") int age
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> loginUser(
            @Field("email") String email ,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("read.php")
    Call<UserResponse> readData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<UserResponse> updateData(
            @Field("id") int id,
            @Field("door") int door,
            @Field("window") int window,
            @Field("light") int light,
            @Field("fridge") int fridge,
            @Field("heater") double heater
            );



    @FormUrlEncoded
    @POST("log.php")
    Call<UserResponse> logData(
            @Field("id") int id,
            @Field("type") String type ,
            @Field("value") String value
    );


}
