package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class PatchCenterIdVolunteerUseCase {
    private final VolunteerRepository volunteerRepository;

    public PatchCenterIdVolunteerUseCase(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(long id, long centerId, @NonNull Consumer<Status<Void>> callback) {
        volunteerRepository.patchVolunteer(id, centerId, callback);
    }
}
