package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tourReview")
public class TourReview {
    @PrimaryKey(autoGenerate = true)
    private int reviewId;

    @ColumnInfo(name = "tourId")
    private int tourId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "comment")
    private String comment;

    // Constructor with all parameters
    public TourReview(int reviewId, int tourId, int userId, float rating, String comment) {
        this.reviewId = reviewId;
        this.tourId = tourId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
