package com.example.dailydoze.Models;

public class MarketModel {

    String vendor, name, price, link;

    public MarketModel(String vendor, String name, String price, String link) {
        this.vendor = vendor;
        this.name = name;
        this.price = price;
        this.link = link;
    }

    public MarketModel() {
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
