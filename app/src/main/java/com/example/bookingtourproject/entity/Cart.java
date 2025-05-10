package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
})
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int cartId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "tourId")
    private int tourId;

    // Constructor with all parameters
    public Cart(int cartId, int userId, int tourId) {
        this.cartId = cartId;
        this.userId = userId;
        this.tourId = tourId;
    }

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
}
