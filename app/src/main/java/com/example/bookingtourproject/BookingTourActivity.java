package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.BookingTour;
import com.example.bookingtourproject.entity.History;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class BookingTourActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private UserDao userDao;
    EditText fullNameInput, phoneInput, emailInput, startDateInput, endDateInput;
    TextView nameText;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_tour);

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
                Intent intent = new Intent(BookingTourActivity.this, ViewProfileActivity.class);

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
                Intent intent = new Intent(BookingTourActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("email", getIntent().getStringExtra("email")); // ✅ truyền lại email
                startActivity(intent);
            }
        });
//xử lí xong header và footer

        int tourId = getIntent().getIntExtra("tourId", -1);

        fullNameInput = findViewById(R.id.fullNameInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        bookButton = findViewById(R.id.booking);
        ImageView tourImageView = findViewById(R.id.tourImageView);

// Nếu ảnh là tên file trong drawable
        Tour tour = DbConnection.getInstance(this).tourDao().getTourById(tourId);
        int imgRes = getResources().getIdentifier(tour.getImage(), "drawable", getPackageName());
        Glide.with(this).load(imgRes).into(tourImageView);

        if (tourId == -1 || email == null) {
            Toast.makeText(this, "Thiếu dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // ✅ Hiển thị dữ liệu vào các EditText
        fullNameInput.setText(user.getFullName());
        phoneInput.setText(user.getPhone());
        emailInput.setText(user.getEmail());
        startDateInput.setText(tour.getStartDate());
        endDateInput.setText(tour.getEndDate());

        // ✅ Khi ấn nút Booking
        bookButton.setOnClickListener(v -> {
            BookingTour booking = new BookingTour(
                    0,
                    tour.getTourId(),
                    user.getId(),
                    fullNameInput.getText().toString(),
                    phoneInput.getText().toString(),
                    emailInput.getText().toString(),
                    startDateInput.getText().toString(),
                    endDateInput.getText().toString()
            );
            DbConnection.getInstance(this).bookingTourDao().insertBooking(booking);

            History history = new History(0, user.getId(), tour.getTourId());
            DbConnection.getInstance(this).historyDao().insertHistory(history);

            Toast.makeText(this, "Đặt tour thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Hoặc chuyển về Home/List
        });
    };
}