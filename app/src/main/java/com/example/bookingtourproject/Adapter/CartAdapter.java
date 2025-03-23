package com.example.bookingtourproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.dao.CartDao;
import com.example.bookingtourproject.entity.Cart;
import com.example.bookingtourproject.entity.Tour;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> carts;
    private CartDao cartDao;
    private Set<Cart> selectedCarts = new HashSet<>(); // ✅ danh sách được chọn
    private OnSelectionChangeListener listener;

    public CartAdapter(List<Cart> carts, CartDao cartDao, OnSelectionChangeListener listener) {
        this.carts = carts;
        this.cartDao = cartDao;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        Tour tour = cartDao.getTourById(cart.getTourId());

        if (tour != null) {
            int imgResId = getImageResourceId(tour.getImage(), holder.itemView.getContext());

            // Gán dữ liệu vào ViewHolder
            holder.bind(
                    tour.getTourName(),
                    "$" + tour.getPrice(),
                    tour.getStartDate() + " -> " + tour.getEndDate(),
                    imgResId,
                    selectedCarts.contains(cart)
            );

            // Xử lý sự kiện khi tick checkbox
            holder.checkBox.setOnCheckedChangeListener(null); // Clear listener cũ
            holder.checkBox.setChecked(selectedCarts.contains(cart));

            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedCarts.add(cart);
                } else {
                    selectedCarts.remove(cart);
                }

                // Gọi callback khi có thay đổi
                if (listener != null) {
                    listener.onSelectionChanged(new ArrayList<>(selectedCarts));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public List<Cart> getSelectedCarts() {
        return new ArrayList<>(selectedCarts);
    }

    public interface OnSelectionChangeListener {
        void onSelectionChanged(List<Cart> selectedItems);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee, date;
        ImageView imgTour;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            date = itemView.findViewById(R.id.date);
            imgTour = itemView.findViewById(R.id.imgtour);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        public void bind(String titleText, String feeText, String dateText, int imgResId, boolean isChecked) {
            title.setText(titleText);
            fee.setText(feeText);
            date.setText(dateText);
            imgTour.setImageResource(imgResId);
            checkBox.setChecked(isChecked);
        }
    }
    public void setCarts(List<Cart> newCarts) {
        this.carts = newCarts;
        notifyDataSetChanged();
    }
    public void clearSelection() {
        selectedCarts.clear();
        notifyDataSetChanged(); // cập nhật lại checkbox
    }
    private int getImageResourceId(String imageName, Context context) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
