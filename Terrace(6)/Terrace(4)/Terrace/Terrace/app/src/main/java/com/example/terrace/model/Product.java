package com.example.terrace.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String MaSP, name, image, detail;
    private int price;
    public Product() {
    }
    public Product(String maSP, String name, String image, String detail, int price) {
        this.MaSP = maSP;
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMaSP() {
        return MaSP;
    }
    public void setMaSP(String maSP) {
        MaSP = maSP;
    }
}


