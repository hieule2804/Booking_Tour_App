package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourWithCategory;
import com.example.bookingtourproject.entity.TourWithReviews;

import java.util.List;

@Dao
public interface TourDao {

    @Insert
    void insert(Tour tour);

    @Update
    void update(Tour tour);

    @Delete
    void delete(Tour tour);

    @Query("SELECT * FROM tour WHERE tourId = :tourId")
    Tour getTourById(int tourId);

    @Query("SELECT * FROM tour")
    List<Tour> getAllTours();

    // Truy vấn tất cả các tour cùng với danh mục của chúng
    @Transaction
    @Query("SELECT * FROM tour")
    List<TourWithCategory> getToursWithCategory();

    // Truy vấn tất cả các tour cùng với các đánh giá của chúng
    @Transaction
    @Query("SELECT * FROM tour")
    List<TourWithReviews> getToursWithReviews();
}
