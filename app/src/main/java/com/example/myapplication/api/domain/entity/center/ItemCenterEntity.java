package com.example.myapplication.api.domain.entity.center;

import androidx.annotation.NonNull;

public class ItemCenterEntity {
    @NonNull private final long id;
    @NonNull private final String name;
    @NonNull private final String description;
    @NonNull private final String address;
    @NonNull private final String linkLogo;
    @NonNull private final Double latitude;
    @NonNull private final Double longitude;

    public ItemCenterEntity(long id, @NonNull String name, @NonNull String description, @NonNull String address, @NonNull String linkLogo, @NonNull Double latitude, @NonNull Double longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.linkLogo = linkLogo;
        this.latitude = latitude;
        this.longitude = longitude;
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
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getLinkLogo() {
        return linkLogo;
    }

    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    @NonNull
    public Double getLongitude() {
        return longitude;
    }
}
