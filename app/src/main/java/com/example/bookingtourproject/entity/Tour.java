package com.example.bookingtourproject.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tour", foreignKeys = {
        @ForeignKey(entity = TourCategory.class, parentColumns = "tourCategoryId", childColumns = "tourCategoryId", onDelete = ForeignKey.CASCADE)
})
public class Tour {
    @PrimaryKey(autoGenerate = true)
    private int tourId;

    @ColumnInfo(name = "tourName")
    private String tourName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "startDate")
    private String startDate;

    @ColumnInfo(name = "endDate")
    private String endDate;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "tourCategoryId")  // Đổi tên thành 'tourCategoryId'
    @NonNull
    private int tourCategoryId;  // Thay vì categoryId

    // Constructor with all parameters (bao gồm tourCategoryId)
    public Tour(int tourId, String tourName, String description, double price, String startDate, String endDate, String image, int tourCategoryId) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
        this.tourCategoryId = tourCategoryId;
    }

    // Getters and Setters
    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTourCategoryId() {
        return tourCategoryId;
    }

    public void setTourCategoryId(int tourCategoryId) {
        this.tourCategoryId = tourCategoryId;
    }
}

