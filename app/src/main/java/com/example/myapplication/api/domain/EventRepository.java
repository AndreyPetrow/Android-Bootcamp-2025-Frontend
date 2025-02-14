package com.example.myapplication.api.domain;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.EventDTO;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.List;
import java.util.function.Consumer;

public interface EventRepository {
    void getAllEvents(@NonNull Consumer<Status<List<EventEntity>>> callback);
    void getById(long id, @NonNull Consumer<Status<EventEntity>> callback);
    void getAllBySortedEvents(double latitude, double longitude, @NonNull Consumer<Status<List<EventEntity>>> callback);

    void createEvent(@NonNull EventDTO eventDTO, @NonNull Consumer<Status<EventEntity>> callback);
    void updateEvent(long id, @NonNull EventDTO eventDTO, @NonNull Consumer<Status<EventEntity>> callback);
    void deleteEvent(long id, @NonNull Consumer<Status<EventEntity>> callback);
}
