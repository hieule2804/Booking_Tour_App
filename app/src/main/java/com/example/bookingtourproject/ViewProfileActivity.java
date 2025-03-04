package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection; // Ensure this import
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView fullname, email;
    private Button setting, history, logout;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_profile);
//
//        // Initialize the views and userDao
        initData();
        getDataUser();  // Ensure this method is called to populate the data
//
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this ,UserSettingActivity.class);
                intent.putExtra("email",email.getText().toString());
                startActivity(intent);
            }
        });
//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////            Intent intent = new Intent(ViewProfileActivity.this ,UserHistory.class);
////            startActivity(intent);
//            }
//        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });
    }

//
    private void getDataUser() {
        String email1 = getIntent().getStringExtra("email");  // Ensure you're passing the correct key

        if (email1 != null) {
            User user = userDao.getUserByEmail(email1);
            if (user != null) {
                String fullnameUser = user.getFullName();
                fullname.setText(fullnameUser);
                email.setText(email1);
            } else {
                email.setText("User not found");
            }
        }
    }
//
    private void initData() {
        // Bind the views
        fullname = findViewById(R.id.fullNameProfile);
        email = findViewById(R.id.emailprofile);
        setting = findViewById(R.id.settingButton);
        history = findViewById(R.id.historyButton);
        logout = findViewById(R.id.logoutButton);

        // Initialize userDao with database connection
        userDao = DbConnection.getInstance(this).userDao();
    }
    public void onBackPressed(View view) {
        Intent intent = new Intent(ViewProfileActivity.this, HomeActivity.class);
        intent.putExtra("email",email.getText().toString());
        startActivity(intent);
    }
}
