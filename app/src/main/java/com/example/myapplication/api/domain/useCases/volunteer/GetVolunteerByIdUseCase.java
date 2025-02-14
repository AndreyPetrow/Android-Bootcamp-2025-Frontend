package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class GetVolunteerByIdUseCase {
    private final VolunteerRepository volunteerRepository;

    public GetVolunteerByIdUseCase(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(long id, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        volunteerRepository.getVolunteerById(id, callback);
    }
}
