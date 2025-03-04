package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingtourproject.entity.TourCategory;

import java.util.List;

@Dao
public interface TourCategoryDao {

    @Insert
    void insert(TourCategory tourCategory);

    @Query("SELECT * FROM tourCategory WHERE tourCategoryId = :tourCategoryId")
    TourCategory getTourCategoryById(int tourCategoryId);

    @Query("SELECT * FROM tourCategory")
    List<TourCategory> getAllTourCategories();
}
