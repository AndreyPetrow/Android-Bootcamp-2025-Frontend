package com.example.myapplication.api.domain.useCases.sign;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.domain.SignRepository;
import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class CreateVolunteerUseCase {
    private final SignRepository signRepository;

    public CreateVolunteerUseCase(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    public void execute(@NonNull VolunteerRegisterDTO volunteerRegisterDTO, Consumer<Status<VolunteerEntity>> callback) {
        signRepository.createVolunteer(volunteerRegisterDTO, callback);
    }
}
