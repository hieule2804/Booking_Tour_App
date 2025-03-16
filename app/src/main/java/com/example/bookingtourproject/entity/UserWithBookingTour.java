package com.example.bookingtourproject.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithBookingTour {
    @Embedded
    public User user;  // Thông tin người dùng

    @Relation(
            parentColumn = "id",  // Trường 'id' trong User
            entityColumn = "userId"  // Trường 'userId' trong BookingTour
    )
    public List<BookingTour> bookingTours;  // Danh sách các tour đã đặt của người dùng
}

