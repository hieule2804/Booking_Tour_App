package com.example.bookingtourproject;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.dao.ReportDao;
import com.example.bookingtourproject.entity.Report;
import com.example.se1753demoapplication.R;

import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnDashboard, btnTour, btnBooking, btnUser, btnRevenue;
    private ProgressBar progressThisWeek, progressLastWeek, progressLastMonth;
    private TextView tvThisWeekPercentage, tvLastWeekPercentage, tvLastMonthPercentage;
    private ReportDao reportDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.se1753demoapplication.R.layout.activity_report);

        // Ánh xạ các view
        btnBack = findViewById(R.id.btnBack);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnTour = findViewById(R.id.btnTour);
        btnBooking = findViewById(R.id.btnBooking);
        btnUser = findViewById(R.id.btnUser);
        btnRevenue = findViewById(R.id.btnRevenue);
        progressThisWeek = findViewById(R.id.progressThisWeek);
        progressLastWeek = findViewById(R.id.progressLastWeek);
        progressLastMonth = findViewById(R.id.progressLastMonth);
        tvThisWeekPercentage = findViewById(R.id.tvThisWeekPercentage);
        tvLastWeekPercentage = findViewById(R.id.tvLastWeekPercentage);
        tvLastMonthPercentage = findViewById(R.id.tvLastMonthPercentage);

        // Khởi tạo database và DAO
        reportDao = DbConnection.getInstance(this).reportDao();

        // Thêm dữ liệu mẫu và lấy dữ liệu trên background thread
        new Thread(() -> {
            // Xóa dữ liệu cũ để đảm bảo không có dữ liệu rác
            clearOldData();
            // Thêm dữ liệu mẫu
            insertSampleData();
            // Kiểm tra dữ liệu sau khi thêm
            checkDataAfterInsert();
            // Lấy dữ liệu và hiển thị
            runOnUiThread(this::loadReportData);
        }).start();

        // Xử lý sự kiện nút Back
        btnBack.setOnClickListener(v -> finish());

        // Xử lý sự kiện các nút tab
        btnDashboard.setOnClickListener(v -> {
            resetTabStyles();
            btnDashboard.setBackgroundColor(getResources().getColor(android.R.color.white));
            btnDashboard.setTextColor(getResources().getColor(android.R.color.black));
        });

        btnTour.setOnClickListener(v -> {
            resetTabStyles();
            btnTour.setBackgroundColor(getResources().getColor(android.R.color.white));
            btnTour.setTextColor(getResources().getColor(android.R.color.black));
        });

        btnBooking.setOnClickListener(v -> {
            resetTabStyles();
            btnBooking.setBackgroundColor(getResources().getColor(android.R.color.white));
            btnBooking.setTextColor(getResources().getColor(android.R.color.black));
        });

        btnUser.setOnClickListener(v -> {
            resetTabStyles();
            btnUser.setBackgroundColor(getResources().getColor(android.R.color.white));
            btnUser.setTextColor(getResources().getColor(android.R.color.black));
            Intent intent = new Intent(ReportsActivity.this, UserManagementActivity.class);
            startActivity(intent);
        });

        btnRevenue.setOnClickListener(v -> {
            resetTabStyles();
            btnRevenue.setBackgroundColor(getResources().getColor(android.R.color.white));
            btnRevenue.setTextColor(getResources().getColor(android.R.color.black));
        });
    }

    private void resetTabStyles() {
        btnDashboard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnDashboard.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnTour.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnTour.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnBooking.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnBooking.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnUser.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnUser.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnRevenue.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnRevenue.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    private void clearOldData() {
        reportDao.deleteByPeriod("Tuần này");
        reportDao.deleteByPeriod("Tuần trước");
        reportDao.deleteByPeriod("Tháng trước");
        Log.d("ReportsActivity", "Đã xóa dữ liệu cũ trong bảng reports");
    }

    private void insertSampleData() {
        // Kiểm tra xem bảng reports có dữ liệu chưa
        List<Report> reports = reportDao.getAllReports();
        Log.d("ReportsActivity", "Số lượng bản ghi trước khi thêm: " + reports.size());
        if (reports.isEmpty()) {
            // Thêm dữ liệu mẫu
            reportDao.insert(new Report("Tuần này", 64));
            reportDao.insert(new Report("Tuần trước", 40));
            reportDao.insert(new Report("Tháng trước", 90));
            Log.d("ReportsActivity", "Đã thêm dữ liệu mẫu vào bảng reports");
        } else {
            Log.d("ReportsActivity", "Bảng reports đã có dữ liệu, không cần thêm");
        }
    }

    private void checkDataAfterInsert() {
        // Kiểm tra dữ liệu sau khi thêm
        List<Report> reports = reportDao.getAllReports();
        Log.d("ReportsActivity", "Số lượng bản ghi sau khi thêm: " + reports.size());
        for (Report report : reports) {
            Log.d("ReportsActivity", "Dữ liệu: " + report.getPeriod() + " - " + report.getPercentage() + "%");
        }
    }

    private void loadReportData() {
        // Lấy dữ liệu từ database
        Report thisWeek = reportDao.getReportByPeriod("Tuần này");
        Report lastWeek = reportDao.getReportByPeriod("Tuần trước");
        Report lastMonth = reportDao.getReportByPeriod("Tháng trước");

        // Hiển thị dữ liệu với animation
        if (thisWeek != null) {
            Log.d("ReportsActivity", "Tuần này: " + thisWeek.getPercentage() + "%");
            tvThisWeekPercentage.setText(thisWeek.getPercentage() + "%");
            animateProgressBar(progressThisWeek, thisWeek.getPercentage());
        } else {
            Log.d("ReportsActivity", "Không tìm thấy dữ liệu cho Tuần này");
            Toast.makeText(this, "Không tìm thấy dữ liệu cho Tuần này", Toast.LENGTH_SHORT).show();
        }

        if (lastWeek != null) {
            Log.d("ReportsActivity", "Tuần trước: " + lastWeek.getPercentage() + "%");
            tvLastWeekPercentage.setText(lastWeek.getPercentage() + "%");
            animateProgressBar(progressLastWeek, lastWeek.getPercentage());
        } else {
            Log.d("ReportsActivity", "Không tìm thấy dữ liệu cho Tuần trước");
            Toast.makeText(this, "Không tìm thấy dữ liệu cho Tuần trước", Toast.LENGTH_SHORT).show();
        }

        if (lastMonth != null) {
            Log.d("ReportsActivity", "Tháng trước: " + lastMonth.getPercentage() + "%");
            tvLastMonthPercentage.setText(lastMonth.getPercentage() + "%");
            animateProgressBar(progressLastMonth, lastMonth.getPercentage());
        } else {
            Log.d("ReportsActivity", "Không tìm thấy dữ liệu cho Tháng trước");
            Toast.makeText(this, "Không tìm thấy dữ liệu cho Tháng trước", Toast.LENGTH_SHORT).show();
        }
    }

    private void animateProgressBar(ProgressBar progressBar, int percentage) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, percentage);
        animation.setDuration(1000); // 1 giây
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}