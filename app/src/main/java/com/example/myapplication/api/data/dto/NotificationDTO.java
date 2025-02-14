package com.example.myapplication.api.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class NotificationDTO {
    @Nullable
    @SerializedName("id")
    public long id;

    @Nullable
    @SerializedName("toWhom")
    public long toWhom;  // id принимающего

    @Nullable
    @SerializedName("fromWhom")
    public long fromWhom;  // id отправителя

    @Nullable
    @SerializedName("message")
    public String message;

    @Nullable
    @SerializedName("dateDispatch")
    public String dateDispatch;

    public NotificationDTO(int id, Long toWhom, Long fromWhom, String message, String dateDispatch) {
        this.id = id;
        this.toWhom = toWhom;
        this.fromWhom = fromWhom;
        this.message = message;
        this.dateDispatch = dateDispatch;
    }
}
