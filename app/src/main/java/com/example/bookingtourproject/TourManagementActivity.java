package com.example.bookingtourproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.adapter.TourAdapter;
import com.example.bookingtourproject.dao.TourCategoryDao;
import com.example.bookingtourproject.dao.TourDao;
import com.example.bookingtourproject.dao.TourReviewDao;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.bookingtourproject.entity.TourReview;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TourManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TourAdapter adapter;
    private List<Tour> tourList = new ArrayList<>();
    private List<TourReview> tourReviews = new ArrayList<>();
    private Spinner spinnerCategory;
    private List<TourCategory> categoryList;
    private TourCategoryDao tourCategoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_management);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTours);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DbConnection db = DbConnection.getInstance(this);
        tourCategoryDao = db.tourCategoryDao();

        categoryList = tourCategoryDao.getAllCategories();
        adapter = new TourAdapter(tourList, tourReviews, categoryList, this);
        List<String> categoryNames = new ArrayList<>();
        recyclerView.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoryNames.get(position);
                filterToursByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        new Thread(() -> {
            insertSampleUsers();
            sleep(500);

            insertSampleTourCategories();
            sleep(500);

            insertSampleTours();
            sleep(500);

            insertSampleTourReviews();

            runOnUiThread(() -> {
                loadTourCategories();
                loadTours();
            });
        }).start();
        recyclerView.requestFocus();
    }


    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void loadTourCategories() {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            TourCategoryDao tourCategoryDao = db.tourCategoryDao();

            List<TourCategory> categories = tourCategoryDao.getAllCategories();

            if (categories.isEmpty()) {
                Log.e("DB", "Không có danh mục nào để hiển thị!");
                return;
            }

            List<String> categoryNames = new ArrayList<>();
            categoryNames.add("All");
            for (TourCategory category : categories) {
                categoryNames.add(category.getTourCategoryName());
            }

            runOnUiThread(() -> setupCategorySpinner(categoryNames));
        }).start();
    }
    private void setupCategorySpinner(List<String> categoryNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoryNames.get(position);
                Log.d("SPINNER", "Selected Category: " + selectedCategory); // Debug
                filterToursByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    private void filterToursByCategory(String categoryName) {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            TourDao tourDao = db.tourDao();

            List<Tour> filteredTours;
            if (categoryName.equals("All")) {
                filteredTours = tourDao.getAllTours();
            } else {
                filteredTours = tourDao.getToursByCategory(categoryName);
            }

            runOnUiThread(() -> {
                tourList.clear();
                tourList.addAll(filteredTours);
                adapter.notifyDataSetChanged();
                Log.d("FILTER", "Lọc thành công: " + filteredTours.size() + " tour(s)");
            });
        }).start();
    }



    private void insertSampleUsers() {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            UserDao userDao = db.userDao();

            if (userDao.getAllUsers().isEmpty()) {
                userDao.insert(new User(1, "123456", "Nguyễn Văn A", "0987654321", "nguyenvana@gmail.com", "admin", "Hà Nội"));
                userDao.insert(new User(2, "password", "Trần Thị B", "0912345678", "tranthib@gmail.com", "user", "TP.HCM"));
                userDao.insert(new User(3, "admin123", "Lê Văn C", "0908765432", "levanc@gmail.com", "guide", "Đà Nẵng"));
                userDao.insert(new User(4,"securepass", "Phạm Hồng D", "0988777666", "phamd@gmail.com", "user", "Cần Thơ"));
                Log.d("DB", "Inserted sample users");
            }
        }).start();
    }


    private void insertSampleTourCategories() {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            TourCategoryDao tourCategoryDao = db.tourCategoryDao();

            if (tourCategoryDao.getAllCategories().isEmpty()) {
                tourCategoryDao.insert(new TourCategory("Du lịch biển"));
                tourCategoryDao.insert(new TourCategory("Du lịch sinh thái"));
                tourCategoryDao.insert(new TourCategory("Du lịch văn hóa"));
                tourCategoryDao.insert(new TourCategory("Du lịch nghỉ dưỡng"));
                tourCategoryDao.insert(new TourCategory("Du lịch khám phá"));
                Log.d("DB", "Inserted sample tour categories");
            }
        }).start();
    }


    private void insertSampleTours() {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            TourDao tourDao = db.tourDao();
            UserDao userDao = db.userDao();

            List<User> users = userDao.getAllUsers();
            if (users.isEmpty()) {
                Log.e("DB_ERROR", "Không có user nào! Không thể chèn Tour.");
                return;
            }

            int guideId1 = users.get(0).getId();
            int guideId2 = users.size() > 1 ? users.get(1).getId() : guideId1;
            int guideId3 = users.size() > 2 ? users.get(2).getId() : guideId1;

            if (tourDao.getAllTours().isEmpty()) {
                tourDao.insert(new Tour("Tour Hà Nội", "Tham gia hành trình khám phá vùng biển đảo tuyệt đẹp, nơi bạn sẽ được đắm mình trong làn nước trong xanh, bãi cát trắng mịn và những rặng san hô rực rỡ sắc màu. Tour này bao gồm các hoạt động thú vị như lặn biển, chèo thuyền kayak và thưởng thức hải sản tươi ngon ngay tại bãi biển. Đội ngũ hướng dẫn viên tận tâm sẽ đồng hành cùng bạn, đảm bảo một chuyến đi tràn đầy niềm vui và những kỷ niệm khó quên.", 4.8f, "25/12/2021", "31/01/2022", "hanoi.png", guideId1, 3));
                tourDao.insert(new Tour("Tour Đà Nẵng", "Trải nghiệm văn hóa và lịch sử với tour tham quan thành phố cổ, nơi lưu giữ những di tích hàng trăm năm tuổi cùng những câu chuyện hấp dẫn về quá khứ. Bạn sẽ được tham quan các ngôi chùa cổ kính, chợ truyền thống sầm uất và thưởng thức các món ăn đặc sản đậm đà hương vị địa phương. Tour được thiết kế với lịch trình linh hoạt, phù hợp cho mọi lứa tuổi, mang đến cho bạn một hành trình ý nghĩa và đầy cảm hứng.", 4.6f, "10/06/2022", "20/06/2022", "danang.png", guideId2, 1));
                tourDao.insert(new Tour("Tour Sapa", "Khám phá hành trình đầy thú vị đến vùng đất giàu truyền thống văn hóa, nơi bạn sẽ được hòa mình vào thiên nhiên hoang sơ với những ngọn núi hùng vĩ, dòng sông thơ mộng và những ngôi làng cổ kính. Tour du lịch này mang đến trải nghiệm độc đáo với các hoạt động như đi bộ đường dài, thưởng thức ẩm thực địa phương và tìm hiểu về phong tục tập quán của người dân bản địa. Hành trình được dẫn dắt bởi hướng dẫn viên chuyên nghiệp, đảm bảo bạn có một chuyến đi an toàn và đáng nhớ.", 4.9f, "01/11/2022", "10/11/2022", "sapa.png", guideId3, 5));

                Log.d("DB", "Inserted sample tours");
            }
        }).start();
    }


    private void insertSampleTourReviews() {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            TourReviewDao tourReviewDao = db.tourReviewDao();
            TourDao tourDao = db.tourDao();
            UserDao userDao = db.userDao();

            List<Tour> tours = tourDao.getAllTours();
            List<User> users = userDao.getAllUsers();

            if (tours.isEmpty() || users.isEmpty()) {
                Log.e("DB_ERROR", "Không có tour hoặc user! Không thể chèn Review.");
                return;
            }

            if (tourReviewDao.getAllReviews().isEmpty()) {
                tourReviewDao.insert(new TourReview(0, tours.get(0).getTourId(), users.get(0).getId(), 4.5f, "Trải nghiệm rất tuyệt vời!"));
                tourReviewDao.insert(new TourReview(0, tours.get(1).getTourId(), users.get(1).getId(), 4.2f, "Dịch vụ tốt nhưng giá hơi cao."));
                tourReviewDao.insert(new TourReview(0, tours.get(2).getTourId(), users.get(2).getId(), 4.8f, "Phong cảnh đẹp, đồ ăn ngon!"));

                Log.d("DB", "Inserted sample tour reviews");
            }
        }).start();
    }


    private void loadTours() {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(this);
            TourDao tourDao = db.tourDao();
            TourReviewDao tourReviewDao = db.tourReviewDao();

            List<Tour> tours = tourDao.getAllTours();
            List<TourReview> reviews = tourReviewDao.getAllReviews();

            runOnUiThread(() -> {
                tourList.clear();
                tourList.addAll(tours);
                tourReviews.clear();
                tourReviews.addAll(reviews);
                adapter.notifyDataSetChanged();
            });

            for (Tour tour : tours) {
                Log.d("DB", "Tour: " + tour.getTourName());
            }
        }).start();
    }


}
