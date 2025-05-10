package com.example.bookingtourproject.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.bookingtourproject.entity.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TourCategoryDao {

    @Insert
    void insert(TourCategory tourCategory);

    @Query("SELECT * FROM tourCategory WHERE tourCategoryId = :tourCategoryId")
    TourCategory getTourCategoryById(int tourCategoryId);

    @Query("SELECT * FROM tourCategory")
    List<TourCategory> getAllCategories();

    @Query("SELECT * FROM tourCategory")
    List<TourCategory> getAllTourCategories();
}
