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
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

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
            username.setText("User not found");
        }

        profile = findViewById(R.id.imageView);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để chuyển đến ViewProfileActivity
                Intent intent = new Intent(HomeActivity.this, ViewProfileActivity.class);
                // Truyền email qua Intent
                intent.putExtra("email", userEmail);
                startActivity(intent);
            }
        });

        recyclerViewCategory();
        recyclerViewPopular();
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.viewPopular);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        recyclerViewPopularList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = 30; // khoảng cách giữa các item, có thể điều chỉnh giá trị này
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
