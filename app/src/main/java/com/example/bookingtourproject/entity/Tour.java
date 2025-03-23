package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(
        tableName = "tour",
        foreignKeys = @ForeignKey(
                entity = User.class, // Liên kết với bảng User
                parentColumns = "id",
                childColumns = "guideId",
                onDelete = ForeignKey.CASCADE // Xóa User thì tour có hướng dẫn viên đó cũng bị xóa
        )
)
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

    @ColumnInfo(name = "guideId", index = true)
    private int guideId;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

    public Tour(String tourName, String description, double price, String startDate, String endDate, String image, int guideId, int categoryId) {
        this.tourName = tourName;
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
        this.guideId = guideId;
        this.categoryId = categoryId;
    }

    // Getters & Setters
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

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName(List<TourCategory> categoryList) {
        for (TourCategory category : categoryList) {
            if (category.getTourCategoryId() == this.categoryId) {
                return category.getTourCategoryName();
            }
        }
        return "Unknown";
    }
}
