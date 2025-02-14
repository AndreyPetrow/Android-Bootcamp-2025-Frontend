package com.example.myapplication.api.domain.useCases.sign;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.SignRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class LoginVolunteerUseCase {
    private final SignRepository signRepository;

    public LoginVolunteerUseCase(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    public void execute(@NonNull String email, @NonNull String password, Consumer<Status<VolunteerEntity>> callback) {
        signRepository.login(email, password, volunteerEntityStatus -> {
           if (volunteerEntityStatus.getStatusCode() != 200) signRepository.logout();
           callback.accept(volunteerEntityStatus);
        });
    }
}
