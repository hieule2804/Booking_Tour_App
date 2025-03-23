package com.example.bookingtourproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.Adapter.CartAdapter;
import com.example.bookingtourproject.dao.CartDao;
import com.example.bookingtourproject.dao.HistoryDao;
import com.example.bookingtourproject.dao.UserDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.History;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private TextView totalPriceTextView;
    private UserDao userDao;
    private CartDao cartDao;
    private HistoryDao historyDao;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartList = new ArrayList<>();
    private List<Cart> selectedCarts = new ArrayList<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        usernameTextView = findViewById(R.id.name);
        totalPriceTextView = findViewById(R.id.textView8);
        userDao = DbConnection.getInstance(this).userDao();
        cartDao = DbConnection.getInstance(this).cartDao();
        historyDao = DbConnection.getInstance(this).historyDao();
        recyclerView = findViewById(R.id.viewCart);

        String email = getIntent().getStringExtra("email");
        user = userDao.getUserByEmail(email);

        if (user != null) {
            usernameTextView.setText("Hello " + user.getFullName());
        } else {
            usernameTextView.setText("Hello Booking");
        }

        // Footer buttons
        ImageView bottomBtn2 = findViewById(R.id.ic_bottom_btn2);
        TextView textBtn2 = findViewById(R.id.text_bottom_btn2);
        View.OnClickListener profileClick = v -> {
            Intent intent = new Intent(this, ViewProfileActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        };
        bottomBtn2.setOnClickListener(profileClick);
        textBtn2.setOnClickListener(profileClick);

        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        if (user != null) {
            cartList = cartDao.getCartByUserId(user.getId());
            adapter = new CartAdapter(cartList, cartDao, selectedItems -> {
                selectedCarts = selectedItems;
                updateTotalPrice();
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        // Delete button
        Button deleteBtn = findViewById(R.id.btnDeleteSelected);
        deleteBtn.setOnClickListener(v -> {
            for (Cart cart : selectedCarts) {
                cartDao.deleteCart(cart);
            }
            Toast.makeText(this, "Đã xoá mục đã chọn", Toast.LENGTH_SHORT).show();
        });

        // Book button
        Button bookBtn = findViewById(R.id.button);
        bookBtn.setOnClickListener(v -> {
            if (selectedCarts.size() != 1) {
                Toast.makeText(this, "Chỉ được chọn 1 tour để đặt!", Toast.LENGTH_SHORT).show();
                return;
            }

            Cart selected = selectedCarts.get(0);
            Intent intent = new Intent(this, BookingTourActivity.class);
            intent.putExtra("tourId", selected.getTourId());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("cartId", selected.getCartId());
            startActivity(intent);
        });

    }

    private void reloadCart() {
        cartList = cartDao.getCartByUserId(user.getId());
        adapter.setCarts(cartList);
        adapter.clearSelection(); // ✅ thêm dòng này
        selectedCarts.clear();
        updateTotalPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadCart(); // ✅ mỗi lần quay lại sẽ load lại cart
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for (Cart cart : selectedCarts) {
            Tour tour = cartDao.getTourById(cart.getTourId());
            if (tour != null) {
                total += tour.getPrice();
            }
        }
        totalPriceTextView.setText("Price: $" + String.format("%.2f", total));
    }
}
