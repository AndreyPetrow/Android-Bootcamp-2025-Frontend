package com.example.myapplication.ui.main.searchVolunteer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.databinding.ItemVolunteerBinding;
import com.example.myapplication.utils.DownloadImage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VolunteerRecycleViewAdapter extends RecyclerView.Adapter<VolunteerRecycleViewAdapter.ViewHolder> {
    private List<ItemVolunteerEntity> volunteers = new ArrayList<>();
    private final Consumer<Long> listener;
    private List<Long> volunteersId = new ArrayList<>();
    @NonNull
    private final Consumer<Long> onItemClick;

    public VolunteerRecycleViewAdapter(@NonNull Consumer<Long> listener, @NonNull Consumer<Long> onItemClick) {
        this.listener = listener;
        this.onItemClick = onItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemVolunteerBinding binding;
        private final Consumer<Long> listener;
        private Long volunteerId;

        public ViewHolder(ItemVolunteerBinding binding, Consumer<Long> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(ItemVolunteerEntity volunteer, final int position) {
            volunteerId = volunteer.getId();

            binding.volunteerName.setText(fullnameVolunteer(volunteer));
            binding.volunteerStatus.setText(statusVolunteer(volunteer));
            if (volunteer.getProfileImageUrl() != null) {
                new DownloadImage(binding.profileImage).execute(volunteer.getProfileImageUrl());
            } else binding.profileImage.setImageResource(R.color.green);

            if (volunteersId.contains(volunteerId)) binding.pushNotification.setImageResource(R.drawable.check_circle);
            else binding.pushNotification.setImageResource(R.drawable.add_circle);

            binding.pushNotification.setOnClickListener(view -> {
                if (!volunteersId.contains(volunteerId)) {
                    binding.pushNotification.setImageResource(R.drawable.check_circle);
                    listener.accept(volunteer.getId());
                    volunteersId.add(volunteerId);
                }
            });
            binding.getRoot().setOnClickListener(v -> onItemClick.accept(volunteer.getId()));
        }

        private String fullnameVolunteer(ItemVolunteerEntity volunteer) {
            if (volunteer.getPatronymic() == null)
                return volunteer.getName() + " " + volunteer.getSurname();
            else
                return volunteer.getName() + " " + volunteer.getSurname() + " " + volunteer.getPatronymic();
        }

        private String statusVolunteer(ItemVolunteerEntity volunteer) {
            if (volunteer.getCenter() == null) return volunteer.getCity();
            else return volunteer.getCenterName();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemVolunteerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Test", "0. Обновление информации о волонтерах");

        holder.bind(volunteers.get(position), position);
    }

    @Override
    public int getItemCount() {
        return volunteers.size();
    }

    public void updateVolunteers(List<ItemVolunteerEntity> updateVolunteers) {
        volunteers.clear();
        volunteers.addAll(updateVolunteers);
        Log.d("Test", "Обновление информации о волонтерах " + getItemCount());
        notifyDataSetChanged();
    }
}
