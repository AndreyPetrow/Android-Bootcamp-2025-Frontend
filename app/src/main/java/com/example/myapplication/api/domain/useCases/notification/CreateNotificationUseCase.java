package com.example.myapplication.api.domain.useCases.notification;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.NotificationDTO;
import com.example.myapplication.api.domain.NotificationRepository;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.function.Consumer;

public class CreateNotificationUseCase {
    private final NotificationRepository notificationRepository;

    public CreateNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void execute(@NonNull NotificationDTO notificationDTO, Consumer<Status<NotificationEntity>> callback) {
        notificationRepository.createNotification(notificationDTO, callback);
    }
}
