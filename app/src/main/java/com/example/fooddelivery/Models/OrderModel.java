package com.example.fooddelivery.Models;

public class OrderModel {

    private String title;
    private String price;
    private String amount;
    private String imageUrl;

    public OrderModel(String title, String price, String amount, String imageUrl) {
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    public OrderModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
