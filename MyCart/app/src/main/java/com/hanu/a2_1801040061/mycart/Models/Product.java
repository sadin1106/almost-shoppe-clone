package com.hanu.a2_1801040061.mycart.Models;

public class Product {

    long id;
    String thumbnail;
    String description;
    int unitPrice;

    public Product(String thumbnail, String description, int unitPrice) {
        this.thumbnail = thumbnail;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public Product(long id, String thumbnail, String description, int unitPrice) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public int getUnitPrice() {
        return unitPrice;
    }
}
