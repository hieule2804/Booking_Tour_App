package com.example.bookingtourproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.dao.CartDao;
import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.Tour;
import com.example.se1753demoapplication.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> carts;
    private CartDao cartDao;  // DAO để truy vấn bảng Tour

    public CartAdapter(List<Cart> carts, CartDao cartDao) {
        this.carts = carts;
        this.cartDao = cartDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy Cart ở vị trí hiện tại
        Cart cart = carts.get(position);

        // Lấy Tour theo tourId từ Cart
        Tour tour = cartDao.getTourById(cart.getTourId());

        // Bind dữ liệu vào các view
        if (tour != null) {
            // Nếu bạn lưu tên tài nguyên hình ảnh trong DB
            String imageName = tour.getImage(); // tên hình ảnh từ DB
            int imgResId = getImageResourceId(imageName, holder.itemView.getContext());

            holder.bind(tour.getTourName(), String.valueOf(tour.getPrice()), imgResId);
        }
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, fee;
        ImageView imgTour;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            imgTour = itemView.findViewById(R.id.imgtour);
        }

        public void bind(String titleText, String feeText, int imgResId) {
            title.setText(titleText);
            fee.setText(feeText);
            imgTour.setImageResource(imgResId);
        }
    }

    // Phương thức để chuyển đổi tên hình ảnh thành ID tài nguyên
    private int getImageResourceId(String imageName, android.content.Context context) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
