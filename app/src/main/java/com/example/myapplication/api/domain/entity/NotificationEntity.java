package com.example.myapplication.api.domain.entity;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class NotificationEntity {
    @NonNull private final long id;

    @NonNull private final long toWhom;  // id принимающего

    @NonNull private final long fromWhom;  // id отправителя

    @NonNull private final String message;

    @NonNull private final LocalDate dateDispatch;

    public NotificationEntity(long id, long toWhom, long fromWhom, @NonNull String message, @NonNull LocalDate dateDispatch) {
        this.id = id;
        this.toWhom = toWhom;
        this.fromWhom = fromWhom;
        this.message = message;
        this.dateDispatch = dateDispatch;
    }

    public long getId() {
        return id;
    }

    public long getToWhom() {
        return toWhom;
    }

    public long getFromWhom() {
        return fromWhom;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public LocalDate getDateDispatch() {
        return dateDispatch;
    }
}
