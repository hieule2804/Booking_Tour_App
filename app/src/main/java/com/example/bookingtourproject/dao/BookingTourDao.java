package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingtourproject.entity.BookingTour;

import java.util.List;

@Dao
public interface BookingTourDao {

    @Insert
    void insert(BookingTour bookingTour);

    @Update
    void update(BookingTour bookingTour);

    @Delete
    void delete(BookingTour bookingTour);

    @Query("SELECT * FROM bookingTour WHERE userId = :userId")
    List<BookingTour> getBookingToursByUserId(int userId);

    @Query("SELECT * FROM bookingTour WHERE tourId = :tourId")
    List<BookingTour> getBookingToursByTourId(int tourId);
    @Query("SELECT * FROM bookingTour WHERE tourId = :i AND userId = :id")
    BookingTour getBookingTourByTourIdAndUserId(int i, int id);
}
