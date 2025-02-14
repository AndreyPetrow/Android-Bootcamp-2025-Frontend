package com.example.myapplication.api.domain.useCases.notification;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.NotificationRepository;
import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;

import java.util.List;
import java.util.function.Consumer;

public class GetAllNotificationUseCase {
    private final NotificationRepository notificationRepository;

    public GetAllNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void execute(@NonNull Consumer<Status<List<NotificationEntity>>> callback) {
        notificationRepository.getAllNotification(callback);
    }
}
