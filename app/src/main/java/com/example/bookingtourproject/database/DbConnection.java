package com.example.bookingtourproject.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Chèn dữ liệu mẫu vào cơ sở dữ liệu
                                    new Thread(() -> {
                                        try {
                                            // Tạo đối tượng DAO
                                            UserDao userDao = INSTANCE.userDao();
                                            TourCategoryDao tourCategoryDao = INSTANCE.tourCategoryDao();
                                            TourDao tourDao = INSTANCE.tourDao();
                                            BookingTourDao bookingTourDao = INSTANCE.bookingTourDao();
                                            CartDao cartDao = INSTANCE.cartDao();
                                            HistoryDao historyDao = INSTANCE.historyDao();
                                            TourReviewDao tourReviewDao = INSTANCE.tourReviewDao();

                                            // Chèn người dùng mẫu
                                            User user1 = new User(0, "123123", "John Doe", "1234567890", "Hieu1@gmail.com", "user", "123 Street");
                                            User user2 = new User(0, "password456", "Jane Smith", "0987654321", "jane.smith@example.com", "admin", "456 Avenue");
                                            User user3 = new User(0, "password789", "David Brown", "1122334455", "david.brown@example.com", "user", "789 Road");
                                            User user4 = new User(0, "password101", "Emily White", "2233445566", "emily.white@example.com", "user", "101 Parkway");
                                            User user5 = new User(0, "password202", "Michael Green", "6677889900", "michael.green@example.com", "user", "202 Boulevard");
                                            userDao.insert(user1);
                                            userDao.insert(user2);
                                            userDao.insert(user3);
                                            userDao.insert(user4);
                                            userDao.insert(user5);
                                            Log.d("DbCallback", "Users inserted");

                                            // Chèn danh mục tour mẫu
                                            TourCategory category1 = new TourCategory(0, "Adventure", "adventure_image_url");
                                            TourCategory category2 = new TourCategory(0, "Relaxation", "relaxation_image_url");
                                            TourCategory category3 = new TourCategory(0, "Cultural", "cultural_image_url");
                                            TourCategory category4 = new TourCategory(0, "Beach", "beach_image_url");
                                            TourCategory category5 = new TourCategory(0, "Nature", "nature_image_url");
                                            tourCategoryDao.insert(category1);
                                            tourCategoryDao.insert(category2);
                                            tourCategoryDao.insert(category3);
                                            tourCategoryDao.insert(category4);
                                            tourCategoryDao.insert(category5);
                                            Log.d("DbCallback", "Tour categories inserted");

                                        } catch (Exception e) {
                                            Log.e("DbCallback", "Error while inserting data: ", e);
                                        }
                                    }).start();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
