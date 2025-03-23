package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class TourDetailActivity extends AppCompatActivity {
    private ImageView tourImg;
    private TextView tourTitle, tourPrice, dateTour, descriptionTour;
    private TextView usernameTextView;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tour_detail);
//xử lí nút back về trang trước đó
        ImageView backBtn = findViewById(R.id.imageView5);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Hoặc finish();
            }
        });
// xử lí xong

//xử lí header và footer
        usernameTextView = findViewById(R.id.name); // TextView đang có ID là "name"
        userDao = DbConnection.getInstance(this).userDao();

        String email = getIntent().getStringExtra("email");
        User user = userDao.getUserByEmail(email);

        if (user != null) {
            usernameTextView.setText("Hello " + user.getFullName());
        } else {
            usernameTextView.setText("Hello Booking");
        }
        String userEmail = getIntent().getStringExtra("email");
        // Bước 1: Tìm các View trong Activity của bạn
        ImageView bottomBtn2 = findViewById(R.id.ic_bottom_btn2);
        TextView textBtn2 = findViewById(R.id.text_bottom_btn2);

// Bước 2: Đặt OnClickListener cho ImageView và TextView
        View.OnClickListener profileClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bước 3: Tạo Intent để chuyển đến ViewProfileActivity
                Intent intent = new Intent(TourDetailActivity.this, ViewProfileActivity.class);

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
                Intent intent = new Intent(TourDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("email", getIntent().getStringExtra("email")); // ✅ truyền lại email
                startActivity(intent);
            }
        });
        // Cart icon & text (bottom_btn3 & text_bottom_btn3)
        ImageView bottomBtn3 = findViewById(R.id.ic_bottom_btn3);
        TextView textBtn3 = findViewById(R.id.text_bottom_btn3);

        View.OnClickListener cartClickListener = view -> {
            Intent intent = new Intent(TourDetailActivity.this, CartActivity.class);
            intent.putExtra("email", email); // ✅ truyền lại email nếu cần hiển thị tên user
            startActivity(intent);
        };

        bottomBtn3.setOnClickListener(cartClickListener);
        textBtn3.setOnClickListener(cartClickListener);

//xử lí xong header và footer
//xử lí nút booking
        Button bookTourBtn = findViewById(R.id.booking);

        bookTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourDetailActivity.this, BookingTourActivity.class);
                intent.putExtra("tourId", getIntent().getIntExtra("tourId", -1));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });

//xử lí xong nút booking
        int tourId = getIntent().getIntExtra("tourId", -1);
        Tour tour = DbConnection.getInstance(this).tourDao().getTourById(tourId);
//xử lí nút Addcart
        Button addCartButton = findViewById(R.id.addcard); // ID trong layout của bạn

        addCartButton.setOnClickListener(v -> {
            if (user != null && tour != null) {
                Cart cart = new Cart(0, user.getId(), tour.getTourId());
                DbConnection.getInstance(this).cartDao().insertCart(cart);
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không thể thêm vào giỏ", Toast.LENGTH_SHORT).show();
            }
        });

//xử lí xong nút Addcart
        tourImg = findViewById(R.id.tourimg);
        tourTitle = findViewById(R.id.tourtitle);
        tourPrice = findViewById(R.id.tourprice);
        dateTour = findViewById(R.id.datetour);
        descriptionTour = findViewById(R.id.descriptiontour);

        if (tour != null) {
            // Nếu ảnh là tên trong drawable folder
            int imgRes = getResources().getIdentifier(tour.getImage(), "drawable", getPackageName());
            Glide.with(this).load(imgRes).into(tourImg);

            tourTitle.setText(tour.getTourName());
            tourPrice.setText("$" + tour.getPrice());
            String dateRange = tour.getStartDate() + " -> " + tour.getEndDate();
            dateTour.setText(dateRange);
            descriptionTour.setText(tour.getDescription());
        }

    }
}