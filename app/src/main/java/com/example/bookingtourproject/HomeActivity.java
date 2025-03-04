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

import com.example.se1753demoapplication.R;

public class HomeActivity extends AppCompatActivity {
private TextView home;
    private Button profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
             String userEmail = getIntent().getStringExtra("email");
        home = findViewById(R.id.textview);
        home.setText(userEmail);
        profile = findViewById(R.id.profileButon);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewProfileActivity.class);
                intent.putExtra("email",userEmail);
                startActivity(intent);
            }
        });
    }
}