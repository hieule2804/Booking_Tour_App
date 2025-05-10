package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtourproject.ReportsActivity;
import com.example.bookingtourproject.TourManagementActivity;
import com.example.bookingtourproject.UserManagementActivity;
import com.example.se1753demoapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(AdminDashboardActivity.this, TourManagementActivity.class));
            } else if (itemId == R.id.nav_users) {
                startActivity(new Intent(AdminDashboardActivity.this, UserManagementActivity.class));
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(AdminDashboardActivity.this, ReportsActivity.class));
            }
            return true;
        });

    }
}
