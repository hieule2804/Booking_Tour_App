package com.example.bookingtourproject;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.Adapter.PopularAdapter;
import com.example.bookingtourproject.entity.Tour;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;

public class ListDetailActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewTourList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Bước 1: Tìm các View trong Activity của bạn
        ImageView bottomBtn2 = findViewById(R.id.ic_bottom_btn2);
        TextView textBtn2 = findViewById(R.id.text_bottom_btn2);
        String userEmail = getIntent().getStringExtra("email");
// Bước 2: Đặt OnClickListener cho ImageView và TextView
        View.OnClickListener profileClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bước 3: Tạo Intent để chuyển đến ViewProfileActivity
                Intent intent = new Intent(ListDetailActivity.this, ViewProfileActivity.class);

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
                Intent intent = new Intent(ListDetailActivity.this, HomeActivity.class);

                // Đặt flag để xóa tất cả các Activity trước đó
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Chuyển hướng đến HomeActivity
                startActivity(intent);
            }
        });



        recyclerViewTourList();
    }
    private void recyclerViewTourList() {
        // Lấy chiều rộng của màn hình
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        // Đo kích thước của một item (Lấy chiều rộng của item từ layout của RecyclerView)
        View itemView = LayoutInflater.from(this).inflate(R.layout.viewholder_popular, null);
        itemView.measure(0, 0);  // Đo kích thước item sau khi inflating
        int itemWidth = itemView.getMeasuredWidth(); // Lấy chiều rộng của item đã được đo

        // Tính số cột có thể hiển thị
        int spanCount = Math.max(1, screenWidth / itemWidth); // Đảm bảo rằng số cột ít nhất là 1

        // Sử dụng GridLayoutManager với số cột được tính toán
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerViewTourList = findViewById(R.id.viewTour);
        recyclerViewTourList.setLayoutManager(gridLayoutManager);

        recyclerViewTourList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = 15; // khoảng cách giữa các item
                outRect.top = 15;    // Khoảng cách phía trên
                outRect.left = 15;
                outRect.bottom = 15;
            }
        });

        ArrayList<Tour> tourList = new ArrayList<>();
        tourList.add(new Tour(1,"Tour Han Quoc 6N5D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc", 1));
        tourList.add(new Tour(2,"Tour Han Quoc 4N3D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",2));
        tourList.add(new Tour(3,"Tour Han Quoc 5N4D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",3));
        tourList.add(new Tour(4,"Tour Han Quoc 5N4D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",3));
        tourList.add(new Tour(5,"Tour Han Quoc 5N4D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",3));
        tourList.add(new Tour(6,"Tour Han Quoc 5N4D", "aaaaaa",2000, "2025-03-16","2025-03-19","ic_hanquoc",3));
        adapter = new PopularAdapter(tourList);
        recyclerViewTourList.setAdapter(adapter);
    }


}