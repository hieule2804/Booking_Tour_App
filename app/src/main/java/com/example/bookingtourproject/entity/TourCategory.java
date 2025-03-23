package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tourCategory")
public class TourCategory {
    @PrimaryKey(autoGenerate = true)
    private int tourCategoryId;

    @ColumnInfo(name = "tourCategoryName")
    private String tourCategoryName;

    @ColumnInfo(name = "categoryImage")
    private String categoryImage; // New field for category image

    // Constructor with all parameters
    public TourCategory(int tourCategoryId, String tourCategoryName, String categoryImage) {
        this.tourCategoryId = tourCategoryId;
        this.tourCategoryName = tourCategoryName;
        this.categoryImage = categoryImage;
    }
    @Ignore
    public TourCategory(String tourCategoryName) {
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

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
