package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.Tour;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM cart WHERE userId = :userId")
    List<Cart> getCartByUserId(int userId);

    @Query("SELECT * FROM cart")
    List<Cart> getAllCarts();

    @Query("SELECT * FROM tour WHERE tourId = :tourId")
    Tour getTourById(int tourId);  // Lấy dữ liệu từ bảng Tour theo tourId
}
