package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class GetVolunteerByTelephone {
    private final VolunteerRepository volunteerRepository;

    public GetVolunteerByTelephone(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(@NonNull String telephone, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        volunteerRepository.getVolunteerByTelephone(telephone, callback);
    }
}
