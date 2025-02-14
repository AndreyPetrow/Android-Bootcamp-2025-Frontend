package com.example.myapplication.api.data.source;

import com.example.myapplication.api.data.dto.NotificationDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationApi {

    @GET("api/v1/notifications")
    Call<List<NotificationDTO>> getAll();

    @GET("api/v1/notifications/{id}")
    Call<NotificationDTO> getById(@Path("id") long id);

    @GET("api/v1/notifications/sender/{id}")
    Call<List<NotificationDTO>> getAllSenderId(@Path("id") long id);

    @GET("api/v1/notifications/recipient/{id}")
    Call<List<NotificationDTO>> getAllByRecipientId(@Path("id") long id);

    @GET("api/v1/notifications/volunteer/{id}")
    Call<List<NotificationDTO>> getAllByVolunteerId(@Path("id") long id);

    @POST("api/v1/notifications")
    Call<NotificationDTO> createNotification(@Body NotificationDTO notificationDTO);

    @PUT("api/v1/notifications/{id}")
    Call<NotificationDTO> updateNotification(@Path("id") long id, @Body NotificationDTO notificationDTO);

    @DELETE("api/v1/notifications/{id}")
    Call<Void> deleteNotification(@Path("id") long id);
}
