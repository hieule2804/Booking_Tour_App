package com.example.bookingtourproject;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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


import com.example.bookingtourproject.Adapter.ListTourAdapter;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;

public class ListDetailActivity extends AppCompatActivity {
    private ListTourAdapter adapter;
    private RecyclerView recyclerViewTourList;
    private EditText searchTourEditText;
    private TextView usernameTextView;
    private UserDao userDao;
    private ArrayList<Tour> allTours = new ArrayList<>();
    private ArrayList<Tour> filteredTours = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_detail);
        usernameTextView = findViewById(R.id.name); // ID của TextView
        userDao = DbConnection.getInstance(this).userDao();

        String email = getIntent().getStringExtra("email");
        User user = userDao.getUserByEmail(email);

        if (user != null) {
            String fullName = user.getFullName();
            usernameTextView.setText("Hello " + fullName);
        } else {
            usernameTextView.setText("Hello Booking");
        }

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
                Intent intent = new Intent(ListDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("email", getIntent().getStringExtra("email")); // ✅ thêm dòng này
                startActivity(intent);
            }
        });
        searchTourEditText = findViewById(R.id.searchtour); // ID của EditText bạn đã tạo

        searchTourEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTourList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        recyclerViewTourList();
    }

    private void filterTourList(String query) {
        filteredTours.clear();
        for (Tour tour : allTours) {
            if (tour.getTourName().toLowerCase().contains(query.toLowerCase())) {
                filteredTours.add(tour);
            }
        }

        adapter = new ListTourAdapter(filteredTours, tour -> {
            Intent intent = new Intent(ListDetailActivity.this, TourDetailActivity.class);
            intent.putExtra("tourId", tour.getTourId());
            intent.putExtra("email", getIntent().getStringExtra("email"));
            startActivity(intent);
        });
        recyclerViewTourList.setAdapter(adapter);
    }


    private void recyclerViewTourList() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        View itemView = LayoutInflater.from(this).inflate(R.layout.viewholder_popular, null);
        itemView.measure(0, 0);
        int itemWidth = itemView.getMeasuredWidth();
        int spanCount = Math.max(1, screenWidth / itemWidth);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerViewTourList = findViewById(R.id.viewTour);
        recyclerViewTourList.setLayoutManager(gridLayoutManager);

        recyclerViewTourList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.right = 15;
                outRect.top = 15;
                outRect.left = 15;
                outRect.bottom = 15;
            }
        });

        allTours.clear();
        filteredTours.clear();

        int categoryId = getIntent().getIntExtra("categoryId", -1);
        String searchKeyword = getIntent().getStringExtra("searchKeyword");

        if (categoryId != -1) {
            allTours.addAll(DbConnection.getInstance(this).tourDao().getToursByCategoryId(categoryId));
        } else {
            allTours.addAll(DbConnection.getInstance(this).tourDao().getAllTours());
        }

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            for (Tour tour : allTours) {
                if (tour.getTourName().toLowerCase().contains(searchKeyword.toLowerCase())) {
                    filteredTours.add(tour);
                }
            }
        } else {
            filteredTours.addAll(allTours);
        }

        adapter = new ListTourAdapter(filteredTours, tour -> {
            Intent intent = new Intent(ListDetailActivity.this, TourDetailActivity.class);
            intent.putExtra("tourId", tour.getTourId());
            intent.putExtra("email", getIntent().getStringExtra("email"));
            startActivity(intent);
        });
        recyclerViewTourList.setAdapter(adapter);
    }

}