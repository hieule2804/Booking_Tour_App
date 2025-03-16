package com.example.bookingtourproject.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TourWithReviews {
    @Embedded
    public Tour tour;  // Thông tin tour

    @Relation(
            parentColumn = "tourId",  // Trường 'tourId' trong Tour
            entityColumn = "tourId"   // Trường 'tourId' trong TourReview
    )
    public List<TourReview> reviews;  // Danh sách các đánh giá của tour
}
