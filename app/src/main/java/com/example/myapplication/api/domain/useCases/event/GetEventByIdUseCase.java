package com.example.myapplication.api.domain.useCases.event;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.EventRepository;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.function.Consumer;

public class GetEventByIdUseCase {
    private final EventRepository eventRepository;

    public GetEventByIdUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(long id, @NonNull Consumer<Status<EventEntity>> callback) {
        eventRepository.getById(id, callback);
    }
}
