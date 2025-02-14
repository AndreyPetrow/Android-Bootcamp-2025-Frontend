package com.example.myapplication.api.domain.useCases.notification;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.NotificationDTO;
import com.example.myapplication.api.domain.NotificationRepository;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.List;
import java.util.function.Consumer;

public class GetAllByRecipientIdUseCase {
    private final NotificationRepository notificationRepository;

    public GetAllByRecipientIdUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void execute(long id, Consumer<Status<List<NotificationEntity>>> callback) {
        notificationRepository.getAllByRecipientId(id, callback);
    }
}
