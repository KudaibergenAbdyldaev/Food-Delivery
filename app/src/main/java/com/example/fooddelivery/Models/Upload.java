package com.example.fooddelivery.Models;

public class Upload {

    private String mName;
    private String desck;
    private String price;
    private String mImageUrl;

    public Upload() {

    }

    public Upload(String mName, String desck, String price, String mImageUrl) {
        this.mName = mName;
        this.desck = desck;
        this.price = price;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getDesck() {
        return desck;
    }

    public void setDesck(String desck) {
        this.desck = desck;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
