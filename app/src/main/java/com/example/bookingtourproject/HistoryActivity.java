package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.adapter.HistoryAdapter;
import com.example.bookingtourproject.dao.HistoryDao;
import com.example.bookingtourproject.dao.TourDao;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.History;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
private HistoryDao historyDao;
private TourDao tourDao;
    private UserDao userDao;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private HistoryAdapter historyAdapter;
     private String email = getIntent().getStringExtra("email");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        historyDao = DbConnection.getInstance(this).historyDao();
        tourDao = DbConnection.getInstance(this).tourDao();
        userDao = DbConnection.getInstance(this).userDao();
        recyclerView = findViewById(R.id.Rview);
        historyAdapter = new HistoryAdapter(getListTour(email));
        LinearLayoutManager manager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(historyAdapter);
    }

    private List<Tour> getListTour(String email) {
        User user = userDao.getUserByEmail(email);
        List<History> list = historyDao.getHistoryByUserId(user.getId());
        List<Tour> listTour = new ArrayList<>();
        for ( History h: list) {
            Tour tour = tourDao.getTourById(h.getTourId());
            listTour.add(tour);
        }
        return listTour;
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent(HistoryActivity.this, ViewProfileActivity.class);
       intent.putExtra("email",email.toString());
        startActivity(intent);
    }
}