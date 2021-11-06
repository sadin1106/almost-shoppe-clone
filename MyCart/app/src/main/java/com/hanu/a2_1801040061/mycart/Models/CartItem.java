package com.hanu.a2_1801040061.mycart.Models;

public class CartItem implements Comparable {
    long id;
    String thumbnail;
    String description;
    int unitPrice;
    int quantity;

    public CartItem() {
    }

    public CartItem(long id, String thumbnail, String description, int unitPrice, int quantity) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public CartItem(String thumbnail, String description, int unitPrice, int quantity) {
        this.thumbnail = thumbnail;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Object o) {
        CartItem cartItem  = (CartItem) o;
        if (cartItem.getDescription().equals(this.description) && cartItem.getThumbnail().equals(this.thumbnail)){
            return 0;
        }
        return 1;
    }
}
