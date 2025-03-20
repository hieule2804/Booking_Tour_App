package com.example.bookingtourproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;

import com.example.bookingtourproject.dao.BookingTourDao;
import com.example.bookingtourproject.dao.CartDao;
import com.example.bookingtourproject.dao.HistoryDao;
import com.example.bookingtourproject.dao.TourCategoryDao;
import com.example.bookingtourproject.dao.TourDao;
import com.example.bookingtourproject.dao.TourReviewDao;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.entity.BookingTour;
import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.History;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.bookingtourproject.entity.TourReview;
import com.example.bookingtourproject.entity.User;

@Database(entities = {User.class, Tour.class, Cart.class, BookingTour.class, History.class, TourCategory.class, TourReview.class}, version = 6)
public abstract class DbConnection extends RoomDatabase {

    private static DbConnection INSTANCE = null;

    // Abstract DAO methods
    public abstract UserDao userDao();
    public abstract TourDao tourDao();
    public abstract CartDao cartDao();
    public abstract BookingTourDao bookingTourDao();
    public abstract HistoryDao historyDao();
    public abstract TourCategoryDao tourCategoryDao();
    public abstract TourReviewDao tourReviewDao();

    public static DbConnection getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DbConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DbConnection.class, "booking_tour_database")
                            .fallbackToDestructiveMigration()  // Xóa database cũ khi có thay đổi về schema
                            .allowMainThreadQueries()  // Cho phép truy vấn trên main thread (không nên dùng trong sản phẩm thật)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}



