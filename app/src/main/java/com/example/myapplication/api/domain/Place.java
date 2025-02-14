package com.example.myapplication.api.domain;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    private final long id;
    private final String name;
    private final String address;

    private final String information;

    private final String pathToImage;
    private final LatLng latLng; //координаты

    public Place(long id, String name, String address, String information, String pathToImage, LatLng latLng) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.information = information;
        this.pathToImage = pathToImage;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return information;
    }

    public String getPathToImage() {
        return pathToImage;
    }
}
