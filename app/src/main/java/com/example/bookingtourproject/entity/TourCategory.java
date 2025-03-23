package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tourCategory")
public class TourCategory {
    @PrimaryKey(autoGenerate = true)
    private int tourCategoryId;

    @ColumnInfo(name = "tourCategoryName")
    private String tourCategoryName;

    // Constructor with all parameters
    public TourCategory( String tourCategoryName) {
        this.tourCategoryName = tourCategoryName;
    }

    // Getters and Setters
    public int getTourCategoryId() {
        return tourCategoryId;
    }

    public void setTourCategoryId(int tourCategoryId) {
        this.tourCategoryId = tourCategoryId;
    }

    public String getTourCategoryName() {
        return tourCategoryName;
    }

    public void setTourCategoryName(String tourCategoryName) {
        this.tourCategoryName = tourCategoryName;
    }
}
