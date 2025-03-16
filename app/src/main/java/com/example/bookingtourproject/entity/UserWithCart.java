package com.example.bookingtourproject.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithCart {
    @Embedded
    public User user;  // Thông tin User

    @Relation(
            parentColumn = "id", // Trường 'id' trong User
            entityColumn = "userId" // Trường 'userId' trong Cart
    )
    public List<Cart> carts;  // Danh sách các Cart của User
}


