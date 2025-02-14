package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class DeleteVolunteer {
    private final VolunteerRepository volunteerRepository;

    public DeleteVolunteer(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(long id, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        volunteerRepository.deleteVolunteer(id, callback);
    }
}
