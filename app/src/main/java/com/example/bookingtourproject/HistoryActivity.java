package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.adapter.ListHistoryAdapter;
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

public class HistoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ListHistoryAdapter.OnItemClickListener {
    private HistoryDao historyDao;
    private TourDao tourDao;
    private UserDao userDao;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ListHistoryAdapter listHistoryAdapter;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
searchView = findViewById(R.id.search);
        // Lấy Intent và lấy dữ liệu email
        email = getIntent().getStringExtra("email");

        if (email == null) {
            email = "default@example.com";
        }

        // Tiếp tục với các khởi tạo khác
        historyDao = DbConnection.getInstance(this).historyDao();
        tourDao = DbConnection.getInstance(this).tourDao();
        userDao = DbConnection.getInstance(this).userDao();
        recyclerView = findViewById(R.id.Rview);
        listHistoryAdapter = new ListHistoryAdapter(getListTour(email));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(listHistoryAdapter);
        listHistoryAdapter.setOnItemClickListener(this);
        searchView.setOnQueryTextListener(this);
    }

    private List<Tour> getListTour(String email) {
        User user = userDao.getUserByEmail(email);
        List<History> list = historyDao.getHistoryByUserId(user.getId());
        List<Tour> listTour = new ArrayList<>();
        for (History h : list) {
            Tour tour = tourDao.getTourById(h.getTourId());
            listTour.add(tour);
        }
        return listTour;
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent(HistoryActivity.this, ViewProfileActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Tour> filterList= new ArrayList<>();
        for(Tour tour : listHistoryAdapter.getBackup()){
            if(tour.getTourName().toLowerCase().contains(s.toLowerCase())){
                filterList.add(tour);
            }
        }
      if(filterList.isEmpty())
      {
          Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
      }else{
          listHistoryAdapter.filterList(filterList);
      }
    }

    @Override
    public void onItemClick(View view, int pos) {
        if (pos >= 0 && pos < listHistoryAdapter.getBackup().size()) {
            if (email != null && !email.isEmpty()) {
                Intent intent = new Intent(HistoryActivity.this, DetaillTourHistoryActivity.class);
                String tourId = String.valueOf(listHistoryAdapter.getBackup().get(pos).getTourId());
                intent.putExtra("tourId", tourId);
                intent.putExtra("email", email);

                startActivity(intent);
            } else {
                Toast.makeText(HistoryActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(HistoryActivity.this, "Vị trí không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }


}
