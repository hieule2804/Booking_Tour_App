package com.example.bookingtourproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.entity.Tour;
import com.example.se1753demoapplication.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Tour> tourList;

    public HistoryAdapter(List<Tour> tourList) {
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        if (tour == null) {
            return;
        }
        holder.tourName.setText(tour.getTourName());
        holder.tourPrice.setText(String.valueOf(tour.getPrice()));
        holder.tourDes.setText(tour.getDescription());
        holder.Date.setText(tour.getEndDate());
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    // Make sure this is a static class
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tourName, tourPrice, tourDes, Date;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tourName = itemView.findViewById(R.id.tourName);
            tourPrice = itemView.findViewById(R.id.tourPrice);
            tourDes = itemView.findViewById(R.id.tourDes);
            Date = itemView.findViewById(R.id.tourDate);
        }
    }
}

