package com.example.myapplication.api.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CenterDTO {
    @Nullable
    @SerializedName("id")
    public long id;

    @Nullable
    @SerializedName("name")
    public String name;

    @Nullable
    @SerializedName("description")
    public String description;

    @Nullable
    @SerializedName("address")
    public String address;

    @Nullable
    @SerializedName("linkLogo")
    public String linkLogo;

    @Nullable
    @SerializedName("latitude")
    public Double latitude;

    @Nullable
    @SerializedName("longitude")
    public Double longitude;

    @Nullable
    @SerializedName("vkLink")
    public String vkLink;

    @Nullable
    @SerializedName("linkSite")
    public String linkSite;

    @Nullable
    @SerializedName("volunteers")
    public List<Long> volunteers;  // Список id всех волонтеров, которые работают в центре.

    @Nullable
    @SerializedName("events")
    public List<Long> events;  // Список id всех событий, которые проводит центр
}
