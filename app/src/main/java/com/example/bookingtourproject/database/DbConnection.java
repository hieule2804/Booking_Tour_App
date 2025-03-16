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

@Database(entities = {User.class, Tour.class, Cart.class, BookingTour.class, History.class, TourCategory.class, TourReview.class}, version = 4)
public abstract class DbConnection extends RoomDatabase {

    // Migration từ phiên bản 1 lên 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Thêm cột mới vào bảng "tourCategory"
            database.execSQL("ALTER TABLE tourCategory ADD COLUMN categoryImage TEXT");
        }
    };

    // Migration từ phiên bản 2 lên 3 (loại bỏ trường guideId và thêm tourCategoryId)
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Bước 1: Tạo bảng mới mà không có trường 'guideId'
            database.execSQL("CREATE TABLE IF NOT EXISTS `tour_new` (" +
                    "`tourId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`tourName` TEXT, " +
                    "`description` TEXT, " +
                    "`price` REAL, " +
                    "`startDate` TEXT, " +
                    "`endDate` TEXT, " +
                    "`image` TEXT)");

            // Bước 2: Chuyển dữ liệu từ bảng cũ sang bảng mới (trừ 'guideId')
            database.execSQL("INSERT INTO `tour_new` (`tourId`, `tourName`, `description`, `price`, `startDate`, `endDate`, `image`) " +
                    "SELECT `tourId`, `tourName`, `description`, `price`, `startDate`, `endDate`, `image` FROM `tour`");

            // Bước 3: Xóa bảng cũ
            database.execSQL("DROP TABLE `tour`");

            // Bước 4: Đổi tên bảng mới thành bảng cũ
            database.execSQL("ALTER TABLE `tour_new` RENAME TO `tour`");
        }
    };
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Tạo bảng mới với cấu trúc có trường 'tourCategoryId' là NOT NULL và có giá trị mặc định là 0
            database.execSQL("CREATE TABLE IF NOT EXISTS `tour_new` (" +
                    "`tourId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`tourName` TEXT, " +
                    "`description` TEXT, " +
                    "`price` REAL, " +
                    "`startDate` TEXT, " +
                    "`endDate` TEXT, " +
                    "`image` TEXT, " +
                    "`tourCategoryId` INTEGER NOT NULL DEFAULT 1, " +
                    "FOREIGN KEY(`tourCategoryId`) REFERENCES `tourCategory`(`tourCategoryId`))");

            // Chuyển dữ liệu từ bảng cũ sang bảng mới, gán giá trị mặc định cho 'tourCategoryId' là 0
            database.execSQL("INSERT INTO `tour_new` (`tourId`, `tourName`, `description`, `price`, `startDate`, `endDate`, `image`, `tourCategoryId`) " +
                    "SELECT `tourId`, `tourName`, `description`, `price`, `startDate`, `endDate`, `image`, 1 FROM `tour`");

            // Xóa bảng cũ
            database.execSQL("DROP TABLE `tour`");

            // Đổi tên bảng mới thành bảng cũ
            database.execSQL("ALTER TABLE `tour_new` RENAME TO `tour`");
        }
    };


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
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)  // Thêm các migrations từ 1-2, 2-3, 3-4
                            .fallbackToDestructiveMigration()  // Nếu không có migration, dữ liệu sẽ bị xóa
                            .allowMainThreadQueries()  // Chạy trên main thread (nên tránh trong môi trường sản phẩm)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


