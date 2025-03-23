package com.example.bookingtourproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.UserDetailActivity;
import com.example.bookingtourproject.entity.User;
import com.example.se1753demoapplication.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private Context context;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(int userId);
    }

    public UserAdapter(Context context, List<User> userList, OnUserClickListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.tvUserName.setText(user.getFullName());

        String image = user.getImage();
        if (image != null && !image.isEmpty()) {
            int resourceId = context.getResources().getIdentifier(image.replace(".png", ""), "drawable", context.getPackageName());
            if (resourceId != 0) {
                holder.imgUserAvatar.setImageResource(resourceId);
            } else {
                holder.imgUserAvatar.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } else {
            holder.imgUserAvatar.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Xử lý sự kiện nhấn vào toàn bộ item để xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUserAvatar, imgArrow;
        TextView tvUserName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUserAvatar = itemView.findViewById(R.id.imgUserAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgArrow = itemView.findViewById(R.id.imgArrow);
        }
    }
}