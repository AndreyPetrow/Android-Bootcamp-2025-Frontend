package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class GetVolunteerByEmailUseCase {
    private final VolunteerRepository volunteerRepository;

    public GetVolunteerByEmailUseCase(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(@NonNull String email, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        volunteerRepository.getVolunteerByEmail(email, callback);
    }
}
