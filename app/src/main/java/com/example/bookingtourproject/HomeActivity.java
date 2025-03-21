package com.example.bookingtourproject;

import android.content.Intent;  // Thêm import Intent
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;  // Thêm import View
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookingtourproject.adapter.CategoryAdapter;
import com.example.bookingtourproject.adapter.PopularAdapter;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private TextView username;
    private UserDao userDao;
    private ImageView profile;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Lấy email từ Intent
        String userEmail = getIntent().getStringExtra("email");

        // Khởi tạo UserDao và lấy dữ liệu người dùng từ cơ sở dữ liệu
        userDao = DbConnection.getInstance(this).userDao();
        User user = userDao.getUserByEmail(userEmail); // Giả sử phương thức này lấy user từ cơ sở dữ liệu theo email

        username = findViewById(R.id.name);

        if (user != null) {
            // Lấy fullname và hiển thị
            String fullName = user.getFullName();
            username.setText("Hello " + fullName);
        } else {
            username.setText("User not found");
        }

// Bước 1: Tìm các View trong Activity của bạn
        ImageView bottomBtn2 = findViewById(R.id.ic_bottom_btn2);
        TextView textBtn2 = findViewById(R.id.text_bottom_btn2);
        TextView seemore = findViewById(R.id.textView3);

// Bước 2: Đặt OnClickListener cho ImageView và TextView
        View.OnClickListener profileClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bước 3: Tạo Intent để chuyển đến ViewProfileActivity
                Intent intent = new Intent(HomeActivity.this, ViewProfileActivity.class);

                // Truyền email qua Intent (giả sử bạn đã có biến userEmail chứa email người dùng)
                intent.putExtra("email", userEmail);

                // Chuyển hướng đến Activity mới
                startActivity(intent);
            }
        };

// Đặt listener cho ImageView và TextView
        bottomBtn2.setOnClickListener(profileClickListener);
        textBtn2.setOnClickListener(profileClickListener);

// Bước 1: Tìm các View trong Activity của bạn
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

// Bước 2: Đặt OnClickListener cho LinearLayout
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bước 3: Tạo Intent để chuyển đến HomeActivity
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);

                // Đặt flag để xóa tất cả các Activity trước đó
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Chuyển hướng đến HomeActivity
                startActivity(intent);
            }
        });
        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListDetailActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Chuyển hướng đến HomeActivity
                startActivity(intent);
            }
        });


        recyclerViewCategory();
        recyclerViewPopular();
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.viewTour);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        recyclerViewPopularList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = 15; // khoảng cách giữa các item, có thể điều chỉnh giá trị này
                outRect.left = 15;
                outRect.top = 15;
                outRect.bottom = 15;
            }
        });

        ArrayList<Tour> tourList = new ArrayList<>();
        tourList.add(new Tour(1,"Tour Han Quoc 6N5D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc", 1));
        tourList.add(new Tour(2,"Tour Han Quoc 4N3D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",2));
        tourList.add(new Tour(3,"Tour Han Quoc 5N4D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",3));
        adapter2 = new PopularAdapter(tourList);
        recyclerViewPopularList.setAdapter(adapter2);
    }


    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.viewCategories);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        // Thêm ItemDecoration để tạo khoảng cách giữa các item
        recyclerViewCategoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = 30; // khoảng cách giữa các item, có thể điều chỉnh giá trị này
                outRect.left = 15;
                outRect.top = 15;
                outRect.bottom = 15;
            }
        });

        ArrayList<TourCategory> categoryList = new ArrayList<>();
        categoryList.add(new TourCategory(1, "Japan", "ic_japan"));
        categoryList.add(new TourCategory(2, "Korea", "ic_korea"));
        categoryList.add(new TourCategory(3, "VietNam", "ic_vietnam"));
        categoryList.add(new TourCategory(4, "China", "ic_china"));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
    }

}
