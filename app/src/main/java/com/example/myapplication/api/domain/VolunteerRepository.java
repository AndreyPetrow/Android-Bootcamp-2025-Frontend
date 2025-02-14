package com.example.myapplication.api.domain;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.List;
import java.util.function.Consumer;

public interface VolunteerRepository {
    void getAllVolunteers(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback);
    void getVolunteerById(long id, @NonNull Consumer<Status<VolunteerEntity>> callback);
    void getVolunteerByEmail(@NonNull String email, @NonNull Consumer<Status<VolunteerEntity>> callback);
    void getVolunteerByTelephone(@NonNull String telephone, @NonNull Consumer<Status<VolunteerEntity>> callback);
    void getFreeVolunteers(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback);
    void getNotFreeVolunteers(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback);

    void updateVolunteer(long id, @NonNull VolunteerDTO volunteerDTO, @NonNull Consumer<Status<Void>> callback);
    void deleteVolunteer(long id, @NonNull Consumer<Status<VolunteerEntity>> callback);

    void patchVolunteer(long id, long centerId, @NonNull Consumer<Status<Void>> callback);
}
