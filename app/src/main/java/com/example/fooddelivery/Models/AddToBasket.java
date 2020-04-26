package com.example.fooddelivery.Models;

import com.google.firebase.database.Exclude;

public class AddToBasket {

    private String key;
    private String mName;
    private String price;
    private String imageUrl;
    private String amount;
    private String addressToDeliver;
    private String clientName;
    private String clientPhone;

    public AddToBasket(String mName, String price, String imageUrl) {
        this.mName = mName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public AddToBasket() {
    }

    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getAddressToDeliver() {
        return addressToDeliver;
    }

    public void setAddressToDeliver(String addressToDeliver) {
        this.addressToDeliver = addressToDeliver;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
