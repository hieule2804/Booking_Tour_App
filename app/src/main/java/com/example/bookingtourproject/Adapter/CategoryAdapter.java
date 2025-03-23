package com.example.bookingtourproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<TourCategory> tourCategories;
    OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(TourCategory category);
    }

    public CategoryAdapter(ArrayList<TourCategory> tourCategories, OnCategoryClickListener listener) {
        this.tourCategories = tourCategories;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infale = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(infale);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TourCategory category = tourCategories.get(position);
        holder.categoryName.setText(category.getTourCategoryName());

        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(category.getCategoryImage(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);

        // Xử lý khi click
        holder.mainLayout.setOnClickListener(v -> {
            listener.onCategoryClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return tourCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

