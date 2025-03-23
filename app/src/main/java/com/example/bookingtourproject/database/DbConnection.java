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
                                    DbConnection.class, "booking_tour_online")
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
// query data Tour
// INSERT INTO tour (tourName, description, price, startDate, endDate, image, tourCategoryId)
//VALUES
//        ('Beach', 'Explore the beautiful beaches of Vietnam', 150.0, '2025-05-01', '2025-05-07', 'beach', 1),
//('Cambodia', 'A cultural journey through the temples of Cambodia', 120.0, '2025-06-10', '2025-06-15', 'cambodia', 2),
//        ('Bangkok', 'Experience the vibrant streets of Bangkok', 80.0, '2025-07-05', '2025-07-10', 'bangkok_streets', 3),
//        ('Himalayan', 'Adventure through the Himalayan mountains', 200.0, '2025-08-20', '2025-08-27', 'himalayan', 4),
//        ('Rome', 'Discover the ancient ruins of Rome', 250.0, '2025-09-10', '2025-09-15', 'rome', 5),
//        ('Tokyo', 'Explore the modern wonders of Tokyo', 300.0, '2025-10-01', '2025-10-10', 'tokyo', 1),
//        ('Mediterranean_cruise', 'A serene cruise through the Mediterranean', 350.0, '2025-11-01', '2025-11-07', 'mediterranean_cruise', 2),
//        ('Bali_beach', 'Relax on the beaches of Bali', 180.0, '2025-12-15', '2025-12-20', 'bali_beach', 3),
//        ('Great_wall_china', 'Explore the ancient Great Wall of China', 220.0, '2025-01-05', '2025-01-10', 'great_wall_china', 4),
//        ('swiss_alps', 'A luxury trip to the Swiss Alps', 500.0, '2025-02-15', '2025-02-20', 'swiss_alps', 5);
//
//query data history
//INSERT INTO history (userId, tourId)
//VALUES
//        (1, 1),
//(1, 2),
//        (1, 3),
//        (1, 4),
//        (1, 5),
//        (1, 6),
//        (1, 7),
//        (1, 8),
//        (1, 9),
//        (1, 10);
//
//query tour review
//INSERT INTO tourReview (tourId, userId, rating, comment)
//VALUES
//        (1, 1, 4.5, 'Great tour, had a wonderful time!'),
//(2, 1, 4.0, 'Interesting experience, but a bit too long.'),
//        (3, 1, 5.0, 'Absolutely amazing! The best trip ever.'),
//        (4, 1, 3.5, 'Good tour, but could be improved with better guides.'),
//        (5, 1, 4.5, 'Loved it, would recommend it to friends!'),
//        (6, 1, 4.0, 'Nice experience, but the accommodation could be better.'),
//        (7, 1, 4.8, 'Great trip, very well organized and enjoyable.'),
//        (8, 1, 5.0, 'Unforgettable experience, highly recommend!'),
//        (9, 1, 3.0, 'It was okay, not as exciting as I expected.'),
//        (10, 1, 4.7, 'Very enjoyable, good balance of activities and relaxation.');