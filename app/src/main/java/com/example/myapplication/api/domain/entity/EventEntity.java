package com.example.myapplication.api.domain.entity;

import androidx.annotation.NonNull;

public class EventEntity {
    @NonNull private final long id;
    @NonNull private final String name;
    @NonNull private final String description;
    @NonNull private final String imageLink;
    @NonNull private final String address;
    @NonNull private final Double latitude;
    @NonNull private final Double longitude;
    @NonNull private final long center;
    @NonNull private final String centerImageLink;
    @NonNull private final String centerName;

    public EventEntity(long id, @NonNull String name, @NonNull String description, @NonNull String imageLink, @NonNull String address, @NonNull Double latitude, @NonNull Double longitude, long center, @NonNull String centerImageLink, @NonNull String centerName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.center = center;
        this.centerImageLink = centerImageLink;
        this.centerName = centerName;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getImageLink() {
        return imageLink;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    @NonNull
    public Double getLongitude() {
        return longitude;
    }

    public long getCenter() {
        return center;
    }

    @NonNull
    public String getCenterImageLink() {
        return centerImageLink;
    }

    @NonNull
    public String getCenterName() {
        return centerName;
    }
}
