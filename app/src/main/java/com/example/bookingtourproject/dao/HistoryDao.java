package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingtourproject.entity.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void insert(History history);

    @Query("SELECT * FROM history WHERE userId = :userId")
    List<History> getHistoryByUserId(int userId);

    @Query("SELECT * FROM history WHERE tourId = :tourId")
    List<History> getHistoryByTourId(int tourId);
}
