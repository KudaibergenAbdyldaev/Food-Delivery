package com.example.fooddelivery.Models;

public class FavouriteModel {

    private String mName;
    private String price;
    private String imageUrl;

    public FavouriteModel(String mName, String price, String imageUrl) {
        this.mName = mName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public FavouriteModel() {
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
