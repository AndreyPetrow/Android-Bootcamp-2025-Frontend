package com.example.myapplication.api.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EventDTO {
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
    @SerializedName("imageLink")
    public String imageLink;

    @Nullable
    @SerializedName("address")
    public String address;

    @Nullable
    @SerializedName("latitude")
    public Double latitude;

    @Nullable
    @SerializedName("longitude")
    public Double longitude;

    @Nullable
    @SerializedName("center")
    public long center;

    @Nullable
    @SerializedName("centerImageLink")
    public String centerImageLink;

    @Nullable
    @SerializedName("centerName")
    public String centerName;
}
