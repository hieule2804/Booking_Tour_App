package com.example.bookingtourproject;

import android.content.Intent;  // Thêm import Intent
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;  // Thêm import View
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookingtourproject.Adapter.CategoryAdapter;
import com.example.bookingtourproject.Adapter.PopularAdapter;
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
            username.setText("Hello Booking");
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
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("email", getIntent().getStringExtra("email")); // ✅ truyền lại email
                startActivity(intent);
            }
        });
        // Cart icon & text (bottom_btn3 & text_bottom_btn3)
        ImageView bottomBtn3 = findViewById(R.id.ic_bottom_btn3);
        TextView textBtn3 = findViewById(R.id.text_bottom_btn3);

        View.OnClickListener cartClickListener = view -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            intent.putExtra("email", getIntent().getStringExtra("email")); // ✅ truyền lại email nếu cần hiển thị tên user
            startActivity(intent);
        };

        bottomBtn3.setOnClickListener(cartClickListener);
        textBtn3.setOnClickListener(cartClickListener);
        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // ❌ Thiếu dòng này
                // ✅ Thêm dòng dưới đây để truyền email sang ListDetailActivity
                intent.putExtra("email", getIntent().getStringExtra("email"));

                startActivity(intent);
            }
        });
        EditText searchTour = findViewById(R.id.searchTourHome);

        searchTour.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String keyword = searchTour.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, ListDetailActivity.class);
                    intent.putExtra("searchKeyword", keyword); // Gửi từ khóa sang ListDetailActivity
                    intent.putExtra("email", getIntent().getStringExtra("email")); // Gửi email nếu cần
                    startActivity(intent);
                }
                return true;
            }
            return false;
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

        // Lấy dữ liệu Tour từ database
        ArrayList<Tour> tourList = new ArrayList<>();
        // Giả sử bạn có một phương thức trong DAO để lấy tất cả các Tour
        tourList.addAll(DbConnection.getInstance(this).tourDao().getAllTours());

        adapter2 = new PopularAdapter(tourList, tour -> {
            Intent intent = new Intent(HomeActivity.this, TourDetailActivity.class);
            intent.putExtra("tourId", tour.getTourId());
            intent.putExtra("email", getIntent().getStringExtra("email")); // Nếu bạn cần truyền email
            startActivity(intent);
        });
        recyclerViewPopularList.setAdapter(adapter2);

    }



    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.viewCategories);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        recyclerViewCategoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.right = 30;
                outRect.left = 15;
                outRect.top = 15;
                outRect.bottom = 15;
            }
        });

        ArrayList<TourCategory> categoryList = new ArrayList<>();
        categoryList.addAll(DbConnection.getInstance(this).tourCategoryDao().getAllTourCategories());

        adapter = new CategoryAdapter(categoryList, category -> {
            // Khi user click vào category -> chuyển sang ListDetailActivity
            Intent intent = new Intent(HomeActivity.this, ListDetailActivity.class);
            intent.putExtra("categoryId", category.getTourCategoryId()); // truyền ID
            intent.putExtra("email", getIntent().getStringExtra("email")); // vẫn truyền email nếu cần
            startActivity(intent);
        });

        recyclerViewCategoryList.setAdapter(adapter);
    }



}
