package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;

import java.util.List;
import java.util.function.Consumer;

public class GetFreeVolunteers {
    private final VolunteerRepository volunteerRepository;

    public GetFreeVolunteers(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback) {
        volunteerRepository.getFreeVolunteers(callback);
    }
}
