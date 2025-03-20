package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtourproject.LoginActivity;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class activity_register extends AppCompatActivity {

    private EditText emailInput, passwordInput, fullNameInput, phoneInput;
    private CheckBox termsCheckBox;
    private Button registerButton;
    private TextView signInText;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        fullNameInput = findViewById(R.id.fullNameInput);
        phoneInput = findViewById(R.id.phoneInput);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        registerButton = findViewById(R.id.registerButton);
        signInText = findViewById(R.id.signInText);

        userDao = DbConnection.getInstance(this).userDao();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        signInText.setOnClickListener(v -> {
            // Redirect to Sign In Activity
            Intent intent = new Intent(activity_register.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String fullName = fullNameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(activity_register.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            Toast.makeText(activity_register.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!CheckExist(email)) {
            Toast.makeText(activity_register.this, "Email is existed ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password length (at least 6 characters)
        if (password.length() < 6) {
            Toast.makeText(activity_register.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate phone number (starts with 09 or 03 and exactly 10 digits)
        if (!isValidPhone(phone)) {
            Toast.makeText(activity_register.this, "Please enter a valid phone number starting with 09 or 03 and 10 digits long", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user agrees to the terms
        if (!termsCheckBox.isChecked()) {
            Toast.makeText(activity_register.this, "You must agree to the Terms of Service", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email is already taken
        if (userDao.isTaken(email)) {
            Toast.makeText(activity_register.this, "Email is already taken", Toast.LENGTH_SHORT).show();
        } else {
            // Create new user
            User user = new User(0, password, fullName, phone, email, "user", "");
            userDao.insert(user);

            // Show success message
            Toast.makeText(activity_register.this, "Registration successful!", Toast.LENGTH_SHORT).show();

            // Redirect to login or close the activity
            finish(); // Close the current activity
        }
    }

    private boolean CheckExist(String email) {
        User user = userDao.getUserByEmail(email);
        if(user == null){
            return true;
        }
        else return false;
    }

    // Method to validate phone number format (starts with 09 or 03 and exactly 10 digits)
    private boolean isValidPhone(String phone) {
        return phone.matches("^(09|03)\\d{8}$");
    }


    // Validate email format
    private boolean isValidEmail(String email) {
        User user = userDao.getUserByEmail(email);
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
            return true;
        }
        else return false;
    }

}
