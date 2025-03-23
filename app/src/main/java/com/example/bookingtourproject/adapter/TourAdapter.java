package com.example.bookingtourproject.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.dao.TourDao;
import com.example.bookingtourproject.database.DbConnection;
import com.example.bookingtourproject.entity.Tour;
import com.example.bookingtourproject.entity.TourCategory;
import com.example.bookingtourproject.entity.TourReview;
import com.example.se1753demoapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {
    private List<Tour> tourList;
    private List<TourReview> tourReviews;
    private Map<Integer, String> categoryMap;
    private Context context;

    public TourAdapter(List<Tour> tourList, List<TourReview> tourReviews, List<TourCategory> categories, Context context) {
        this.tourList = (tourList != null) ? tourList : new ArrayList<>();
        this.tourReviews = (tourReviews != null) ? tourReviews : new ArrayList<>();
        this.context = context;

        categoryMap = new HashMap<>();
        if (categories != null) {
            for (TourCategory category : categories) {
                categoryMap.put(category.getTourCategoryId(), category.getTourCategoryName());
            }
        }
    }


    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        holder.txtTourName.setText(tour.getTourName());
        holder.txtTourDescription.setText(tour.getDescription());
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String formattedPrice = df.format(tour.getPrice());
        holder.txtTourPrice.setText("$ " + formattedPrice);
        holder.txtStartDate.setText("Ngày bắt đầu: " + tour.getStartDate());
        holder.txtEndDate.setText("Ngày kết thúc: " + tour.getEndDate());
        if (tourReviews != null && !tourReviews.isEmpty()) {
            TourReview tourReview = null;
            for (TourReview review : tourReviews) {
                if (review.getTourId() == tour.getTourId()) {
                    tourReview = review;
                    break;
                }
            }

            if (tourReview != null) {
                holder.ratingBar.setRating(tourReview.getRating());
            } else {
                holder.ratingBar.setRating(0);
            }
        } else {
            holder.ratingBar.setRating(0);
        }
        String imageName = tour.getImage();
        String resourceName = imageName != null ? imageName.replace(".png", "") : "";
        int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.imgTour.setImageResource(resourceId);
        } else {
            holder.imgTour.setImageResource(android.R.drawable.ic_menu_gallery); // Ảnh mặc định nếu không tìm thấy
        }
        holder.btnEditTour.setOnClickListener(v -> {
            Toast.makeText(context, "Edit " + tour.getTourName(), Toast.LENGTH_SHORT).show();
        });

        holder.btnDeleteTour.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá Tour này không?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        deleteTour(tour);
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        });

    }
    private void deleteTour(Tour tour) {
        new Thread(() -> {
            DbConnection db = DbConnection.getInstance(context);
            TourDao tourDao = db.tourDao();
            tourDao.delete(tour);

            ((Activity) context).runOnUiThread(() -> {
                tourList.remove(tour);
                notifyDataSetChanged();
            });
        }).start();
    }
    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        TextView txtTourName, txtTourDescription, txtTourPrice, txtStartDate, txtEndDate;
        RatingBar ratingBar;
        ImageButton btnEditTour, btnDeleteTour;
        ImageView imgTour;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTourName = itemView.findViewById(R.id.txtTourName);
            txtTourDescription = itemView.findViewById(R.id.txtTourDescription);
            txtTourPrice = itemView.findViewById(R.id.txtTourPrice);
            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imgTour = itemView.findViewById(R.id.imgTour);
            btnEditTour = itemView.findViewById(R.id.btnEditTour);
            btnDeleteTour = itemView.findViewById(R.id.btnDeleteTour);
        }
    }

    public void updateList(List<Tour> newList) {
        tourList.clear();
        tourList.addAll(newList);
        notifyDataSetChanged();
    }


}
