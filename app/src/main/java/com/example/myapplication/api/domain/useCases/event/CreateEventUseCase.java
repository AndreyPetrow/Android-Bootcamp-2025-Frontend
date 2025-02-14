package com.example.myapplication.api.domain.useCases.event;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.EventDTO;
import com.example.myapplication.api.domain.EventRepository;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.function.Consumer;

public class CreateEventUseCase {
    private final EventRepository eventRepository;

    public CreateEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(@NonNull EventDTO eventDTO, @NonNull Consumer<Status<EventEntity>> callback) {
        eventRepository.createEvent(eventDTO, callback);
    }
}
