package com.example.myapplication.api.domain.useCases.event;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.EventRepository;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.List;
import java.util.function.Consumer;

public class GetAllBySortedEvents {
    private final EventRepository eventRepository;

    public GetAllBySortedEvents(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(double latitude, double longitude, @NonNull Consumer<Status<List<EventEntity>>> callback) {
        eventRepository.getAllBySortedEvents(latitude, longitude, callback);
    }
}
