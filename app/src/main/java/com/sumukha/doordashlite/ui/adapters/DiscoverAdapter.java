package com.sumukha.doordashlite.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sumukha.doordashlite.R;
import com.sumukha.doordashlite.data.models.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private List<Restaurant> restaurants;

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (restaurants == null || position >= restaurants.size()) {
            holder.root.setVisibility(View.GONE);
            return;
        }

        Restaurant restaurant = restaurants.get(position);
        if (restaurant == null) {
            holder.root.setVisibility(View.GONE);
            return;
        }

        holder.root.setVisibility(View.VISIBLE);

        if (restaurant.name == null) {
            holder.nameView.setVisibility(View.GONE);
        } else {
            holder.nameView.setText(restaurant.name);
            holder.nameView.setVisibility(View.VISIBLE);
        }

        if (restaurant.description == null) {
            holder.cuisineView.setVisibility(View.GONE);
        } else {
            holder.cuisineView.setText(restaurant.description);
            holder.cuisineView.setVisibility(View.VISIBLE);
        }

        if (restaurant.status == null) {
            holder.timeAwayView.setVisibility(View.GONE);
        } else {
            holder.timeAwayView.setText(restaurant.status);
            holder.timeAwayView.setVisibility(View.VISIBLE);
        }

        if (restaurant.coverImgUrl == null) {
            holder.thumbnailView.setVisibility(View.GONE);
        } else {
            Glide.with(holder.root.getContext())
                    .load(restaurant.coverImgUrl)
                    .into(holder.thumbnailView);
        }
    }

    @Override
    public int getItemCount() {
        if (restaurants == null) {
            return 0;
        }
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @BindView(R.id.text_restaurant_name)
        TextView nameView;

        @BindView(R.id.text_restaurant_cuisine)
        TextView cuisineView;

        @BindView(R.id.text_time_away)
        TextView timeAwayView;

        @BindView(R.id.image_restaurant_thumbnail)
        ImageView thumbnailView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;

            ButterKnife.bind(this, itemView);
        }
    }

}
