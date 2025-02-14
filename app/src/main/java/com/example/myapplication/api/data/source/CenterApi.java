package com.example.myapplication.api.data.source;

import com.example.myapplication.api.data.dto.CenterDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CenterApi {
    @GET("api/v1/centers")
    Call<List<CenterDTO>> getAllCenters();

    @GET("api/v1/centers/{id}")
    Call<CenterDTO> getCenterById(@Path("id") long id);

    @GET("api/v1/centers/sorted/distance")
    Call<List<CenterDTO>> getSortedCenters(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @POST("api/v1/centers")
    Call<CenterDTO> createCenter(@Body CenterDTO centerDTO);

    @PUT("api/v1/centers/{id}")
    Call<CenterDTO> updateCenter(@Path("id") long id, @Body CenterDTO centerDTO);

    @DELETE("api/v1/centers/{id}")
    Call<Void> deleteVolunteer(@Path("id") long id);
}
