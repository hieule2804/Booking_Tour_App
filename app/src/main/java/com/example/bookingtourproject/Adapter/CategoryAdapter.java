package com.example.bookingtourproject.adapter;

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

    public CategoryAdapter(ArrayList<TourCategory> tourCategories) {
        this.tourCategories = tourCategories;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infale = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(infale);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(tourCategories.get(position).getTourCategoryName());
        String picUrl = "";
        switch (position){
            case 0:{
                picUrl = "ic_japan";
                break;
            }case 1:{
                picUrl = "ic_korea";
                break;
            }case 2:{
                picUrl = "ic_vietnam";
                break;
            }case 3:{
                picUrl = "ic_china";
                break;
            }
        }
        int drawableReourceId = holder.itemView.getResources()
                .getIdentifier(picUrl,"drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {
        return tourCategories.size(); // Sửa lại đây
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view trong ViewHolder
            categoryName = itemView.findViewById(R.id.categoryName); // Sửa R.id.category_name theo đúng ID trong layout
            categoryPic = itemView.findViewById(R.id.categoryPic); // Sửa R.id.category_pic theo đúng ID trong layout
            mainLayout = itemView.findViewById(R.id.mainLayout); // Sửa R.id.main_layout theo đúng ID trong layout
        }
    }
}
