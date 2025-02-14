package com.example.myapplication.api.domain.useCases.volunteer;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class UpdateVolunteerUseCase {
    private final VolunteerRepository volunteerRepository;

    public UpdateVolunteerUseCase(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void execute(long id, @NonNull VolunteerDTO volunteerDTO, @NonNull Consumer<Status<Void>> callback) {
        volunteerRepository.updateVolunteer(id, volunteerDTO, callback);
    }
}
