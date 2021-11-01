package com.ahmedreda.bekia;

import android.net.Uri;

public class Products {


    private String product_name ;
    private int points ;
    private String product_value ;
    private String  image ;

    public Products() {
    }

    public Products(String product_name, int points, String product_value) {
        this.product_name = product_name;
        this.points = points;
        this.product_value = product_value;
    }

    public Products(String product_name, int points, String product_value, String image) {
        this.product_name = product_name;
        this.points = points;
        this.product_value = product_value;
        this.image = image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getProduct_value() {
        return product_value;
    }

    public void setProduct_value(String product_value) {
        this.product_value = product_value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
