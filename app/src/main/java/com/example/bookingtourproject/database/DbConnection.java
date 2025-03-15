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

@Database(entities = {User.class, Tour.class, Cart.class, BookingTour.class, History.class, TourCategory.class, TourReview.class}, version = 2)
public abstract class DbConnection extends RoomDatabase {
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Thêm cột mới vào bảng "tourCategory"
            database.execSQL("ALTER TABLE tourCategory ADD COLUMN categoryImage TEXT");
        }
    };
    private static DbConnection INSTANCE = null;
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
                            .addMigrations(MIGRATION_1_2)  // Thêm Migration vào
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

