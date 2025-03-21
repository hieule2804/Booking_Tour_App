package com.example.bookingtourproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingtourproject.entity.Tour;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ListHistoryViewHolder> {
private List<Tour> tourList;
private List<Tour> listBackup;
    public ListHistoryAdapter(List<Tour> tourList) {
        this.tourList = tourList;
        listBackup = tourList;
    }
//search
    public List<Tour> getBackup(){
        return listBackup;
    }
    public void filterList(List<Tour> filteredList){
        tourList = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ListHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHistoryViewHolder holder, int position) {
Tour tour = tourList.get(position);
if(tour == null){return;}
holder.tourName.setText(tour.getTourName());
holder.tourPricer.setText(String.valueOf(tour.getPrice()));
holder.tourDes.setText(tour.getDescription());
holder.tourDate.setText(tour.getStartDate());
holder.tourImage.setImageResource(R.drawable.logo);
    }


    @Override
    public int getItemCount() {
        return tourList != null ? tourList.size() : 0;
    }

    public static class ListHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tourName,tourPricer,tourDes,tourDate;
        private ImageView tourImage;
        public ListHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tourName = itemView.findViewById(com.example.se1753demoapplication.R.id.tourName);
            tourPricer = itemView.findViewById(R.id.tourPrice);
            tourDes = itemView.findViewById(R.id.tourDes);
            tourDate = itemView.findViewById(R.id.tourDate);
            tourImage = itemView.findViewById(R.id.imgHistoryView);
        }
    }
}
