package com.example.shieldus.models;

import com.google.android.gms.maps.model.LatLng;

public class ServiceCenter {
    private String name;
    private String address;
    private String type;
    private LatLng location;
    private String phoneNumber;

    public ServiceCenter(String name, String address, String type, LatLng location, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getType() { return type; }
    public LatLng getLocation() { return location; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setPhone(String phone) {
        this.phoneNumber = phone;
    }
}