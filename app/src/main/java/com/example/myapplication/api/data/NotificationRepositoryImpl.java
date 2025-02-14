package com.example.myapplication.api.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.api.data.dto.NotificationDTO;
import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.network.RetrofitFactory;
import com.example.myapplication.api.data.source.NotificationApi;
import com.example.myapplication.api.data.source.SignApi;
import com.example.myapplication.api.data.utils.CallToConsumer;
import com.example.myapplication.api.domain.NotificationRepository;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationRepositoryImpl implements NotificationRepository {
    private static NotificationRepositoryImpl INSTANCE;
    private final NotificationApi notificationApi = RetrofitFactory.getInstance().getNotificationApi();

    private NotificationRepositoryImpl() {
    }

    public static synchronized NotificationRepositoryImpl getInstance() {
        if (INSTANCE == null) INSTANCE = new NotificationRepositoryImpl();
        return INSTANCE;
    }

    @Override
    public void getAllNotification(@NonNull Consumer<Status<List<NotificationEntity>>> callback) {
        notificationApi.getAll().enqueue(new CallToConsumer<>(
                callback,
                notificationDTOS -> {
                    if (notificationDTOS == null) return null;

                    ArrayList<NotificationEntity> result = new ArrayList<>(notificationDTOS.size());

                    for (NotificationDTO notificationDto : notificationDTOS) {
                        if (notificationDto == null) continue;

                        final long id = notificationDto.id;
                        final long toWhom = notificationDto.toWhom;  // id принимающего
                        final long fromWhom = notificationDto.fromWhom;  // id отправителя
                        final String message = notificationDto.message;
                        final String dateDispatch = notificationDto.dateDispatch;

                        if (message != null && dateDispatch != null) {
                            result.add(new NotificationEntity(id, toWhom, fromWhom, message, LocalDate.parse(dateDispatch)));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void getAllByRecipientId(long id, @NonNull Consumer<Status<List<NotificationEntity>>> callback) {
        notificationApi.getAllByRecipientId(id).enqueue(new CallToConsumer<>(
                callback,
                notificationDTOS -> {
                    if (notificationDTOS == null) return null;

                    ArrayList<NotificationEntity> result = new ArrayList<>(notificationDTOS.size());

                    for (NotificationDTO notificationDto : notificationDTOS) {
                        if (notificationDto == null) continue;

                        final long id_ = notificationDto.id;
                        final long toWhom = notificationDto.toWhom;  // id принимающего
                        final long fromWhom = notificationDto.fromWhom;  // id отправителя
                        final String message = notificationDto.message;
                        final String dateDispatch = notificationDto.dateDispatch;

                        if (message != null && dateDispatch != null) {
                            result.add(new NotificationEntity(id_, toWhom, fromWhom, message, LocalDate.parse(dateDispatch)));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void createNotification(@NonNull NotificationDTO notificationDTO, @NonNull Consumer<Status<NotificationEntity>> callback) {
        notificationApi.createNotification(notificationDTO).enqueue(new CallToConsumer<>(
                callback,
                notificationDto -> {
                    final long id = notificationDto.id;
                    final long toWhom = notificationDto.toWhom;  // id принимающего
                    final long fromWhom = notificationDto.fromWhom;  // id отправителя
                    final String message = notificationDto.message;
                    final String dateDispatch = notificationDto.dateDispatch;

                    if (message != null && dateDispatch != null) {
                        return new NotificationEntity(id, toWhom, fromWhom, message, LocalDate.parse(dateDispatch));
                    } else return null;
                }
        ));
    }
}
