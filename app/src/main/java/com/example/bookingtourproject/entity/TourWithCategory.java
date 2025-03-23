package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class TourWithCategory {
    @Embedded
    public Tour tour;

    @ColumnInfo(name = "categoryName")
    public String categoryName;
}
