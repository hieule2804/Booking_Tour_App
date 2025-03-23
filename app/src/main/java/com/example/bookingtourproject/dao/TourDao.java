package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourCategory;

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
    @Query("SELECT * FROM Tour WHERE categoryId = (SELECT tourCategoryId FROM TourCategory WHERE tourCategoryName = :categoryName)")
    List<Tour> getToursByCategory(String categoryName);
}
