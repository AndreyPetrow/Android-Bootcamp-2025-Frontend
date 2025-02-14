package com.example.myapplication.api.domain;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.NotificationDTO;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.List;
import java.util.function.Consumer;

public interface NotificationRepository {
    void getAllNotification(@NonNull Consumer<Status<List<NotificationEntity>>> callback);
    void getAllByRecipientId(long id, @NonNull Consumer<Status<List<NotificationEntity>>> callback);

    void createNotification(@NonNull NotificationDTO notificationDTO, @NonNull Consumer<Status<NotificationEntity>> callback);
}
