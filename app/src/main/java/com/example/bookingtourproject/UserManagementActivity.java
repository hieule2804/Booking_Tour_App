package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.adapter.UserAdapter;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList;
    private UserDao userDao;
    private TextView tvTitle;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        ImageButton btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        searchView = findViewById(R.id.searchView);
        userDao = DbConnection.getInstance(this).userDao();
        try {
            recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        } catch (Exception e) {
            Log.e("UserManagementActivity", "Error setting RecyclerView layout manager: " + e.getMessage());
            Toast.makeText(this, "Error setting RecyclerView", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // Thiết lập RecyclerView
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        loadUserList();

        // Lấy dữ liệu từ bảng User
        userList = userDao.getAllUsers();

        userAdapter = new UserAdapter(this, userList, userId -> {
            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
        recyclerViewUsers.setAdapter(userAdapter);

        btnBack.setOnClickListener(v -> finish());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });
    }
    private void filterUsers(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user.getFullName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        userAdapter = new UserAdapter(this, filteredList, userId -> {
            Intent intent = new Intent(UserManagementActivity.this, UserDetailActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
        recyclerViewUsers.setAdapter(userAdapter);
    }
    private void loadUserList() {
        userList = userDao.getAllUsers();
        if (userList == null || userList.isEmpty()) {
            Log.e("UserManagementActivity", "User list is empty");
            userList = userDao.getAllUsers();
        }
        userAdapter = new UserAdapter(this, userList, userId -> {
            Intent intent = new Intent(UserManagementActivity.this, UserDetailActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
        recyclerViewUsers.setAdapter(userAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUserList();
    }
}
