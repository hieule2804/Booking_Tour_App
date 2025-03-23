package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookingTour", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Tour.class, parentColumns = "tourId", childColumns = "tourId", onDelete = ForeignKey.CASCADE)
})
public class BookingTour {
    @PrimaryKey(autoGenerate = true)
    private int bookingTourId;

    @ColumnInfo(name = "tourId")
    private int tourId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "fullName")
    private String fullName;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "startDate")
    private String startDate;

    @ColumnInfo(name = "endDate")
    private String endDate;

    public BookingTour(int bookingTourId, int tourId, int userId, String fullName, String phone, String email, String startDate, String endDate) {
        this.bookingTourId = bookingTourId;
        this.tourId = tourId;
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getBookingTourId() {
        return bookingTourId;
    }

    public void setBookingTourId(int bookingTourId) {
        this.bookingTourId = bookingTourId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}

