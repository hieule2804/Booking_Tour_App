package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingtourproject.entity.TourReview;

import java.util.List;

@Dao
public interface TourReviewDao {

    @Insert
    void insert(TourReview tourReview);

    @Update
    void update(TourReview tourReview);

    @Delete
    void delete(TourReview tourReview);

    @Query("SELECT * FROM tourReview WHERE tourId = :tourId")
    List<TourReview> getReviewsByTourId(int tourId);

    @Query("SELECT * FROM tourReview WHERE userId = :userId")
    List<TourReview> getReviewsByUserId(int userId);
}
