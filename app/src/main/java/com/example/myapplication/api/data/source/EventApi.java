package com.example.myapplication.api.data.source;

import com.example.myapplication.api.data.dto.EventDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventApi {
    @GET("api/v1/events")
    Call<List<EventDTO>> getAllEvent();

    @GET("api/v1/events/{id}")
    Call<EventDTO> getById(@Path("id") long id);

    @GET("api/v1/events/sorted/distance")
    Call<List<EventDTO>> getAllSortedEvents(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @POST
    Call<EventDTO> createEvent(@Body EventDTO eventDTO);

    @PUT("api/v1/events/{id}")
    Call<EventDTO> updateEvent(@Path("id") long id, @Body EventDTO eventDTO);

    @DELETE("api/v1/events/{id}")
    Call<Void> deleteRepository(@Path("id") long id);
}
