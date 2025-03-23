package com.example.bookingtourproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookingtourproject.dao.BookingTourDao;
import com.example.bookingtourproject.dao.CartDao;
import com.example.bookingtourproject.dao.HistoryDao;
import com.example.bookingtourproject.dao.ReportDao;
import com.example.bookingtourproject.dao.TourCategoryDao;
import com.example.bookingtourproject.dao.TourDao;
import com.example.bookingtourproject.dao.TourReviewDao;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.entity.BookingTour;
import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.History;
import com.example.bookingtourproject.entity.Report;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.bookingtourproject.entity.TourReview;
import com.example.bookingtourproject.entity.User;

@Database(entities = {User.class, Tour.class, Cart.class, BookingTour.class, History.class, TourCategory.class, TourReview.class, Report.class}, version = 1, exportSchema = false)
public abstract class DbConnection extends RoomDatabase {
    private static DbConnection INSTANCE = null;
    public abstract UserDao userDao();
    public abstract TourDao tourDao();
    public abstract CartDao cartDao();
    public abstract BookingTourDao bookingTourDao();
    public abstract HistoryDao historyDao();
    public abstract TourCategoryDao tourCategoryDao();
    public abstract TourReviewDao tourReviewDao();
    public abstract ReportDao reportDao();
    public  static  DbConnection getInstance(Context context){
        if (INSTANCE == null){
            synchronized (DbConnection.class){
                if (INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DbConnection.class,"booking_tour_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
            return INSTANCE;
        }
        return INSTANCE;
    }
}

