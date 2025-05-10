package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

public class UserSettingActivity extends AppCompatActivity {

    private EditText fullNameInput, emailInput, passwordInput, phoneInput, addressInput;
    private Button saveButton, cancelButton;
    private UserDao userDao;
private String email1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_setting);

        // Initialize the views
        fullNameInput = findViewById(R.id.fullNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        phoneInput = findViewById(R.id.phoneInput);
        addressInput = findViewById(R.id.addressInput);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        userDao = DbConnection.getInstance(this).userDao();

        email1 = getIntent().getStringExtra("email");
        emailInput.setText(email1);
        getDataUser(email1);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userDao.getUserByEmail(email1);
                if (user != null) {
                    saveUserSettings(user.getId());
                } else {
                    Toast.makeText(UserSettingActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(v -> finish());
    }

    private void getDataUser(String email1) {
        User user = userDao.getUserByEmail(email1);
        if (user != null) {
            fullNameInput.setText(user.getFullName());
            emailInput.setText(user.getEmail());
            passwordInput.setText(user.getPassword());
            phoneInput.setText(user.getPhone());
            addressInput.setText(user.getAddress());
        }
    }

    private void saveUserSettings(int userId) {
        String fullName = fullNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String address = addressInput.getText().toString();

        // Validate the input
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CheckExist(email)) {
            Toast.makeText(this, "Email is existed ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidPhone(phone)) {
            Toast.makeText(this, "Please enter a valid phone number (starts with 09 or 03 and contains 10 digits)", Toast.LENGTH_SHORT).show();
            return;
        }
        userDao.updateUserById(userId, fullName, email, password, phone, "user", address);

        Intent intent = new Intent(UserSettingActivity.this, ViewProfileActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);

        Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean CheckExist(String email) {
        User user = userDao.getUserByEmail(email);
        if(user.getEmail().equals(email1)){
            return true;
        }
        else if(user == null ){
            return true;
        }
        else return false;
    }
    private boolean isValidPhone(String phone) {
        return phone.matches("^(09)\\d{8}$");
    }
    public void onBackPressed(View view) {
        Intent intent = new Intent(UserSettingActivity.this, ViewProfileActivity.class);
        intent.putExtra("email", emailInput.getText().toString());
        startActivity(intent);
    }
}
