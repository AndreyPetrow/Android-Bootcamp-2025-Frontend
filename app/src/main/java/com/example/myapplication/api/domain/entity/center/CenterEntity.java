package com.example.myapplication.api.domain.entity.center;

import androidx.annotation.NonNull;

import java.util.List;

public class CenterEntity {
    @NonNull private final long id;
    @NonNull private final String name;
    @NonNull private final String description;
    @NonNull private final String address;
    @NonNull private final String linkLogo;
    @NonNull private final Double latitude;
    @NonNull private final Double longitude;
    @NonNull private final String vkLink;
    @NonNull private final String linkSite;
    @NonNull private final List<Long> volunteers;  // Список id всех волонтеров, которые работают в центре.
    @NonNull private final List<Long> events;  // Список id всех событий, которые проводит центр

    public CenterEntity(long id, @NonNull String name, @NonNull String description, @NonNull String address, @NonNull String linkLogo, @NonNull Double latitude, @NonNull Double longitude, @NonNull String vkLink, @NonNull String linkSite, @NonNull List<Long> volunteers, @NonNull List<Long> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.linkLogo = linkLogo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vkLink = vkLink;
        this.linkSite = linkSite;
        this.volunteers = volunteers;
        this.events = events;
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

    @NonNull
    public String getVkLink() {
        return vkLink;
    }

    @NonNull
    public String getLinkSite() {
        return linkSite;
    }

    @NonNull
    public List<Long> getVolunteers() {
        return volunteers;
    }

    @NonNull
    public List<Long> getEvents() {
        return events;
    }
}
