package com.example.myapplication.ui.main.notification;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.databinding.ItemMessageBinding;
import com.example.myapplication.databinding.ItemVolunteerBinding;
import com.example.myapplication.ui.main.searchVolunteer.VolunteerRecycleViewAdapter;
import com.example.myapplication.utils.DownloadImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationRecycleViewAdapter extends RecyclerView.Adapter<NotificationRecycleViewAdapter.ViewHolder> {
    private List<NotificationEntity> notifications = new ArrayList<>();
    private final Consumer<Long> listener;

    public NotificationRecycleViewAdapter(Consumer<Long> listener) {
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageBinding binding;
        private final Consumer<Long> listener;

        public ViewHolder(ItemMessageBinding binding, Consumer<Long> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(NotificationEntity notification) {
            binding.message.setText(notification.getMessage());
            binding.senderId.setText(stringIdVolunteer(notification));
            binding.dateDispatch.setText(notification.getDateDispatch().toString());
        }

        private String stringIdVolunteer(NotificationEntity notification) {
            return "ID: " + notification.getFromWhom();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationRecycleViewAdapter.ViewHolder(
                ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void updateNotification(List<NotificationEntity> updateNotification) {
        notifications.clear();
        notifications.addAll(updateNotification);
        notifyDataSetChanged();
    }
}
