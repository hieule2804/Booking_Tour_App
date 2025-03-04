package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailInput, newPasswordInput, confirmPasswordInput;
    private Button resetButton;
    private ImageView passwordEye, confirmPasswordEye;
    private boolean isNewPasswordVisible = false, isConfirmPasswordVisible = false;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Initialize views
        emailInput = findViewById(R.id.emailInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        resetButton = findViewById(R.id.resetButton);
        passwordEye = findViewById(R.id.passwordEye);
        confirmPasswordEye = findViewById(R.id.confirmPasswordEye);

        // Initialize UserDao for database operations
        userDao = DbConnection.getInstance(this).userDao();

        // Set up listeners
        passwordEye.setOnClickListener(v -> togglePasswordVisibility(newPasswordInput, passwordEye, isNewPasswordVisible));
        confirmPasswordEye.setOnClickListener(v -> togglePasswordVisibility(confirmPasswordInput, confirmPasswordEye, isConfirmPasswordVisible));

        resetButton.setOnClickListener(v -> resetPassword());
    }

    // Method to toggle password visibility
    private void togglePasswordVisibility(EditText editText, ImageView imageView, boolean isVisible) {
        if (isVisible) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.ic_eye);
        } else {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.ic_eye_open);
        }
        editText.setSelection(editText.getText().length());
    }

    // Method to handle reset password logic
    private void resetPassword() {
        String email = emailInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Validate email
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Please enter a new password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate confirm password
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please confirm your new password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userDao.getUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userDao.update(user);

            Toast.makeText(this, "Password reset successful!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No user found with this email", Toast.LENGTH_SHORT).show();
        }
    }

    // This method will be automatically called when the back button is clicked
    public void onBackPressed(View view) {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
