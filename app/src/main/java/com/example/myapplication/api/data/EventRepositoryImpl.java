package com.example.myapplication.api.data;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.EventDTO;
import com.example.myapplication.api.data.network.RetrofitFactory;
import com.example.myapplication.api.data.source.EventApi;
import com.example.myapplication.api.data.utils.CallToConsumer;
import com.example.myapplication.api.domain.EventRepository;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventRepositoryImpl implements EventRepository {
    private static EventRepositoryImpl INSTANCE;
    private final EventApi eventApi = RetrofitFactory.getInstance().getEventApi();

    private EventRepositoryImpl() {}

    public static synchronized EventRepositoryImpl getInstance() {
        if (INSTANCE == null) INSTANCE = new EventRepositoryImpl();
        return INSTANCE;
    }

    @Override
    public void getAllEvents(@NonNull Consumer<Status<List<EventEntity>>> callback) {
        eventApi.getAllEvent().enqueue(new CallToConsumer<>(
                callback,
                eventDTOS -> {
                    if (eventDTOS == null) return null;

                    ArrayList<EventEntity> result = new ArrayList<>(eventDTOS.size());

                    for (EventDTO eventDTO : eventDTOS) {
                        if (eventDTO == null) continue;

                        final long id = eventDTO.id;
                        final String name = eventDTO.name;
                        final String description = eventDTO.description;
                        final String imageLink = eventDTO.imageLink;
                        final String address = eventDTO.address;
                        final Double latitude = eventDTO.latitude;
                        final Double longitude = eventDTO.longitude;
                        final long center = eventDTO.center;
                        final String centerImageLink = eventDTO.centerImageLink;
                        final String centerName = eventDTO.centerName;

                        if (name != null && description != null && imageLink != null && address != null && latitude != null && longitude != null && centerImageLink != null && centerName != null) {
                            result.add(new EventEntity(id, name, description, imageLink, address, latitude, longitude, center, centerImageLink, centerName));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void getById(long id, @NonNull Consumer<Status<EventEntity>> callback) {
        eventApi.getById(id).enqueue(new CallToConsumer<>(
                callback,
                eventDTO -> {
                    if (eventDTO == null) return null;

                    final String name = eventDTO.name;
                    final String description = eventDTO.description;
                    final String imageLink = eventDTO.imageLink;
                    final String address = eventDTO.address;
                    final Double latitude = eventDTO.latitude;
                    final Double longitude = eventDTO.longitude;
                    final long center = eventDTO.center;
                    final String centerImageLink = eventDTO.centerImageLink;
                    final String centerName = eventDTO.centerName;

                    if (name != null && description != null && imageLink != null && address != null && latitude != null && longitude != null && centerImageLink != null && centerName != null) {
                        return new EventEntity(id, name, description, imageLink, address, latitude, longitude, center, centerImageLink, centerName);
                    }
                    else return null;
                }
        ));
    }

    @Override
    public void getAllBySortedEvents(double latitude, double longitude, @NonNull Consumer<Status<List<EventEntity>>> callback) {
        eventApi.getAllSortedEvents(latitude, longitude).enqueue(new CallToConsumer<>(
                callback,
                eventDTOS -> {
                    if (eventDTOS == null) return null;
                    ArrayList<EventEntity> result = new ArrayList<>(eventDTOS.size());

                    for (EventDTO eventDTO : eventDTOS) {
                        final long id = eventDTO.id;
                        final String name = eventDTO.name;
                        final String description = eventDTO.description;
                        final String imageLink = eventDTO.imageLink;
                        final String address = eventDTO.address;
                        final Double latitude_ = eventDTO.latitude;
                        final Double longitude_ = eventDTO.longitude;
                        final long center = eventDTO.center;
                        final String centerImageLink = eventDTO.centerImageLink;
                        final String centerName = eventDTO.centerName;

                        if (name != null && description != null && imageLink != null && address != null && latitude_ != null && longitude_ != null && centerImageLink != null && centerName != null) {
                            result.add(new EventEntity(id, name, description, imageLink, address, latitude_, longitude_, center, centerImageLink, centerName));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void createEvent(@NonNull EventDTO eventDTO, @NonNull Consumer<Status<EventEntity>> callback) {

    }

    @Override
    public void updateEvent(long id, @NonNull EventDTO eventDTO, @NonNull Consumer<Status<EventEntity>> callback) {

    }

    @Override
    public void deleteEvent(long id, @NonNull Consumer<Status<EventEntity>> callback) {

    }
}
