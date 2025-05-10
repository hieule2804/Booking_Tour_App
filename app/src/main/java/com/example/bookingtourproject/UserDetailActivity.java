package com.example.bookingtourproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class UserDetailActivity extends AppCompatActivity {
    private ImageView imgUserAvatarDetail;
    private TextView tvFullName, tvEmail, tvPhone, tvAddress, tvRole;
    private UserDao userDao;
    private ImageButton btnBack, btnEditUser, btnDeleteUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        imgUserAvatarDetail = findViewById(R.id.imgUserAvatar);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvRole = findViewById(R.id.tvRole);
        btnBack = findViewById(R.id.btnBack);
        btnEditUser = findViewById(R.id.btnEditUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        userDao = DbConnection.getInstance(this).userDao();

        int userId = getIntent().getIntExtra("userId", -1);
        if (userId != -1) {
            user = userDao.getUserById(userId);
            if (user != null) {
                tvFullName.setText("Full Name: " + user.getFullName());
                tvEmail.setText("Email: " + user.getEmail());
                tvPhone.setText("Phone: " + user.getPhone());
                tvAddress.setText("Address: " + user.getAddress());
                tvRole.setText("Role: " + user.getRole());

                String avatar = user.getImage();
                if (avatar != null && !avatar.isEmpty()) {
                    int resourceId = getResources().getIdentifier(avatar.replace(".jpg", ""), "drawable", getPackageName());
                    if (resourceId != 0) {
                        imgUserAvatarDetail.setImageResource(resourceId);
                    } else {
                        imgUserAvatarDetail.setImageResource(android.R.drawable.ic_menu_gallery);
                    }
                } else {
                    imgUserAvatarDetail.setImageResource(android.R.drawable.ic_menu_gallery);
                }
            } else {
                Log.e("UserDetailActivity", "User not found for ID: " + userId);
            }
        } else {
            Log.e("UserDetailActivity", "Invalid userId");
        }
        btnBack.setOnClickListener(v -> finish());
        btnEditUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, UserSettingActivity.class);
            intent.putExtra("userId", user.getId());
            startActivity(intent);
            finish();
        });

        // Xử lý sự kiện nút Delete
        btnDeleteUser.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete User")
                    .setMessage("Are you sure you want to delete " + user.getFullName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        userDao.delete(user);
                        Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
