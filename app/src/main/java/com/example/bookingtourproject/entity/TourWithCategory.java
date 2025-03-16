package com.example.bookingtourproject.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TourWithCategory {
    @Embedded
    public Tour tour;  // Thông tin tour

    @Relation(
            parentColumn = "tourCategoryId",  // Trường 'tourCategoryId' trong Tour
            entityColumn = "tourCategoryId"   // Trường 'tourCategoryId' trong TourCategory
    )
    public TourCategory tourCategory;  // Thông tin của danh mục tour
}

