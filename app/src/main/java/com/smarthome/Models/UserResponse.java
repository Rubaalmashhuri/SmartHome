package com.smarthome.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class UserResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("door")
    @Expose
    private Integer door;

    @SerializedName("heater")
    @Expose
    private float heater;

    public Integer getFridge() {
        return fridge;
    }

    public void setFridge(Integer fridge) {
        this.fridge = fridge;
    }

    @SerializedName("fridge")
    @Expose
    private Integer fridge;

    @SerializedName("window")
    @Expose
    private Integer window;
    @SerializedName("light")
    @Expose
    private Integer light;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private int id;



    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    @SerializedName("value")
    @Expose
    private float value ;


    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }


    public float getHeater() {
        return heater;
    }

    public void setHeater(float heater) {
        this.heater = heater;
    }



    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}