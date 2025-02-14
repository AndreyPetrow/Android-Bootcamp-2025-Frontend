package com.example.myapplication.ui.main.event;

import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.databinding.ItemEventBinding;
import com.example.myapplication.utils.DownloadImage;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventRecycleViewAdapter extends RecyclerView.Adapter<EventRecycleViewAdapter.ViewHolder> {
    @NonNull
    private final Consumer<Long> onItemClick;

    private final List<EventEntity> eventEntityList = new ArrayList<>();

    private LocationServices fusedLocationClient;
    @Nullable private Location location;

    public EventRecycleViewAdapter(@NonNull Consumer<Long> onItemClick) {
        this.onItemClick = onItemClick;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventBinding binding;

        public ViewHolder(@NonNull ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventEntity item) {
            binding.eventCardTitle.setText(item.getName());
            binding.eventCardAddress.setText(item.getAddress());

            if (location == null) binding.eventCardDistance.setText("Неизвестно");
            else {
                float[] results = new float[5];
                Location.distanceBetween(
                        item.getLatitude(),
                        item.getLongitude(),
                        location.getLatitude(),
                        location.getLongitude(),
                        results
                );

                binding.eventCardDistance.setText(Utils.convertDistance(results[0]));
            }

            downloadImage(binding.eventCardImage, item.getImageLink());
            downloadImage(binding.eventCardImageCenter, item.getCenterImageLink());

            binding.eventCardCenterName.setText(item.getCenterName());

            binding.getRoot().setOnClickListener(v -> onItemClick.accept(item.getId()));
        }

        private void downloadImage(ShapeableImageView shapeableImageView, String urlImage) {
            Log.d("Test", "(MainFragment) Начинаем скачивать изображение с URl: " + urlImage);
            new DownloadImage(shapeableImageView).execute(urlImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Test", "0. Обновление информации о событии " + position);

        holder.bind(eventEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventEntityList.size();
    }

    public void updateData(List<EventEntity> newData, @Nullable Location userLocation) {
        eventEntityList.clear();
        eventEntityList.addAll(newData);
        location = userLocation;
        notifyDataSetChanged();
    }
}
