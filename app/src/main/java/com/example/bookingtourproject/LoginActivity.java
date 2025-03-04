package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtourproject.HomeActivity;
import com.example.bookingtourproject.activity_register;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signUpText, forgotPasswordText;
    private ImageView passwordEye;
    private boolean isPasswordVisible = false; // To track the visibility of the password
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        passwordEye = findViewById(R.id.passwordEye);

        userDao = DbConnection.getInstance(this).userDao();

        passwordEye.setOnClickListener(v -> togglePasswordVisibility());

        loginButton.setOnClickListener(v -> loginUser());

        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, activity_register.class);
            startActivity(intent);
        });

        // Handle "Forgot Password" link click
        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }

    // Toggle password visibility
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordEye.setImageResource(R.drawable.ic_eye);
        } else {
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordEye.setImageResource(R.drawable.ic_eye_open);
        }
        passwordInput.setSelection(passwordInput.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }

    // Handle login functionality
    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validate email and password inputs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(LoginActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userDao.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Successful login
            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

            if(user.getRole().toString().equals("user")) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }else{
                // chuyển sang trang của admin
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to validate email format using regex
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
