package com.example.bookingtourproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingtourproject.entity.Tour;
import com.example.se1753demoapplication.R;

import java.util.ArrayList;

public class ListTourAdapter extends RecyclerView.Adapter<ListTourAdapter.ViewHolder> {
    ArrayList<Tour> tour;

    public ListTourAdapter(ArrayList<Tour> tour) {
        this.tour = tour;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infale = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(infale);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tilte.setText(tour.get(position).getTourName());
        holder.fee.setText(String.valueOf(tour.get(position).getPrice()));

        int drawableReourceId = holder.itemView.getResources()
                .getIdentifier(tour.get(position).getImage(),"drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.imgtour);
    }

    @Override
    public int getItemCount() {
        return tour.size(); // Sửa lại đây
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tilte, fee;
        ImageView imgtour;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view trong ViewHolder
            tilte = itemView.findViewById(R.id.title); // Sửa R.id.category_name theo đúng ID trong layout
            imgtour = itemView.findViewById(R.id.imgtour); // Sửa R.id.category_pic theo đúng ID trong layout
            fee = itemView.findViewById(R.id.fee);
        }
    }
}
