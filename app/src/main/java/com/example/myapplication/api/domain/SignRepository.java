package com.example.myapplication.api.domain;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public interface SignRepository {
    void createVolunteer(@NonNull VolunteerRegisterDTO volunteerRegisterDTO, @NonNull Consumer<Status<VolunteerEntity>> callback);
    void login(@NonNull String email, @NonNull String password, @NonNull Consumer<Status<VolunteerEntity>> callback);
    void logout();
}
