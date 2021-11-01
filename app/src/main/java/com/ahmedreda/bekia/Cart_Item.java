package com.ahmedreda.bekia;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Cart_Item {


    private String CardId ;
    private String UserID ;
    private String Title;
    private int quantity ;
    private double price ;
    private String Image ;

    public Cart_Item() {
    }

    public Cart_Item(String CardId) {
        this.CardId = CardId ;
    }

    public Cart_Item(String cardId, String userID, String title, int quantity, double price, String image) {
        CardId = cardId;
        UserID = userID;
        Title = title;
        this.quantity = quantity;
        this.price = price;
        Image = image;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String  getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
