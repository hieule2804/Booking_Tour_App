package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingtourproject.entity.Report;

import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    void insert(Report report);

    @Query("SELECT * FROM reports WHERE period = :period")
    Report getReportByPeriod(String period);

    @Query("SELECT * FROM reports")
    List<Report> getAllReports();

    @Query("DELETE FROM reports WHERE period = :period")
    void deleteByPeriod(String period);
}