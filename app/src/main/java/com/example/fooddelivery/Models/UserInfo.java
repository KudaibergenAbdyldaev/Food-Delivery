package com.example.fooddelivery.Models;

public class UserInfo {

    private String address;
    private String id;
    private String phone;
    private String username;

    public UserInfo(String address, String id, String phone, String username) {
        this.address = address;
        this.id = id;
        this.phone = phone;
        this.username = username;
    }

    public UserInfo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
