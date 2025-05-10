package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingtourproject.dao.BookingTourDao;
import com.example.bookingtourproject.dao.TourDao;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.BookingTour;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class DetaillTourHistoryActivity extends AppCompatActivity {

    private ImageView imgTourDetail;
    private TextView tvTourDetailName, tvTourDetailDescription, tvTourDetailPrice, tvTourDetailStartDate, tvTourDetailEndDate, tvBookingInfoTitle, tvBookingFullName, tvBookingPhone, tvBookingEmail;
    private String email;
    private String tourId;
private TourDao tourDao;
private UserDao userDao;
private BookingTourDao bookingTourDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detaill_tour_history);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("email") || !intent.hasExtra("tourId")) {
            // Handle the error, for example, show a Toast or finish the activity
            Toast.makeText(this, "Missing required data!", Toast.LENGTH_SHORT).show();
            finish();  // End the activity if the required data is missing
            return;
        }

        email = intent.getStringExtra("email");
        tourId = intent.getStringExtra("tourId");

        initData();
        tourDao = DbConnection.getInstance(this).tourDao();
        userDao = DbConnection.getInstance(this).userDao();
        bookingTourDao = DbConnection.getInstance(this).bookingTourDao();

        User user = userDao.getUserByEmail(email);
        if (user == null) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        BookingTour bookingTour = bookingTourDao.getBookingTourByTourIdAndUserId(Integer.parseInt(tourId), user.getId());
        if (bookingTour == null) {
            Toast.makeText(this, "Booking not found! "+ Integer.parseInt(tourId) + user.getId() , Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTourDetailStartDate.setText("Start Date "+bookingTour.getStartDate());
        tvTourDetailEndDate.setText("End Date :"+bookingTour.getEndDate());
        tvBookingFullName.setText("Full Name :"+bookingTour.getFullName());
        tvBookingPhone.setText("Phone :"+bookingTour.getPhone());
        tvBookingEmail.setText("Email :"+bookingTour.getEmail());

        addInforTourDetail(tourId);
    }

    private void addInforTourDetail(String tourId){
        int tourIdInt = Integer.parseInt(tourId);
        Tour tour = tourDao.getTourById(tourIdInt);
        String imageName = tour.getImage();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

            if (imageResourceId != 0) {
                imgTourDetail.setImageResource(imageResourceId);
            } else {
                imgTourDetail.setImageResource(R.drawable.logo);
            }
        } else {
            imgTourDetail.setImageResource(R.drawable.logo);
        }

        tvTourDetailName.setText("Tour Name :"+tour.getTourName());
        tvTourDetailDescription.setText("Description :"+tour.getDescription());
        tvTourDetailPrice.setText("Price: $"+tour.getPrice());

    }
    private void initData() {
        imgTourDetail = findViewById(R.id.imgTourDetail);
        tvTourDetailName = findViewById(R.id.tvTourDetailName);
        tvTourDetailDescription = findViewById(R.id.tvTourDetailDescription);
        tvTourDetailPrice = findViewById(R.id.tvTourDetailPrice);
        tvTourDetailStartDate = findViewById(R.id.tvTourDetailStartDate);
        tvTourDetailEndDate = findViewById(R.id.tvTourDetailEndDate);
        tvBookingFullName = findViewById(R.id.tvBookingFullName);
        tvBookingPhone = findViewById(R.id.tvBookingPhone);
        tvBookingEmail = findViewById(R.id.tvBookingEmail);
    }
    public void onBackPressed(View view) {
        Intent intent = new Intent(DetaillTourHistoryActivity.this, HistoryActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}