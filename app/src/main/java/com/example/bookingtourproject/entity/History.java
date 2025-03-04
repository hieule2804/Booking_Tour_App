package com.example.bookingtourproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class History {
    @PrimaryKey(autoGenerate = true)
    private int historyId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "tourId")
    private int tourId;

    // Constructor with all parameters
    public History(int historyId, int userId, int tourId) {
        this.historyId = historyId;
        this.userId = userId;
        this.tourId = tourId;
    }

    // Getters and Setters
    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
}
