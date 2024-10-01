package com.example.terrace.model;

import java.io.Serializable;

public class Drinks implements Serializable {
    private String name,image,detail;
    private float price;

    public Drinks() {
    }

    public Drinks(String name, String image, String detail, float price) {
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
