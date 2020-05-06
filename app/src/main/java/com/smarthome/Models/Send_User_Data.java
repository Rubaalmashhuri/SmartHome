package com.smarthome.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Send_User_Data {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("door")
    @Expose
    private int door;
    @SerializedName("heater")
    @Expose
    private float heater;
    @SerializedName("fridge")
    @Expose
    private int fridge;
    @SerializedName("window")
    @Expose
    private int window;
    @SerializedName("light")
    @Expose
    private int light;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("value")
    @Expose
    private String value;




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFridge() {
        return fridge;
    }
    public int getId() {
        return id;
    }
    public int getDoor() {
        return door;
    }
    public void setDoor(int door) {
        this.door = door;
    }
    public float getHeater() {
        return heater;
    }
    public void setHeater(float heater) {
        this.heater = heater;
    }
    public int getWindow() {
        return window;
    }
    public void setWindow(int window) {
        this.window = window;
    }
    public int getLight() {
        return light;
    }
    public void setLight(int light) {
        this.light = light;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFridge(int fridge) {
        this.fridge = fridge;
    }


}
