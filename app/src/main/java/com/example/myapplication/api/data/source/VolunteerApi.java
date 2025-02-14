package com.example.myapplication.api.data.source;

import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface VolunteerApi {
    @GET("api/v1/volunteers")
    Call<List<VolunteerDTO>> getAll();

    @GET("api/v1/volunteers/{id}")
    Call<VolunteerDTO> getVolunteerById(@Path("id") long id);

    @GET("api/v1/volunteers/email/{email}")
    Call<VolunteerDTO> getVolunteerByEmail(@Path("email") String email);

    @GET("api/v1/volunteers/telephone/{telephone}")
    Call<VolunteerDTO> getVolunteerByTelephone(@Path("telephone") String telephone);

    @GET("api/v1/volunteers/free")
    Call<List<VolunteerDTO>> getFreeVolunteers();

    @GET("api/v1/volunteers/not/free")
    Call<List<VolunteerDTO>> getNotFreeVolunteers();

    @PUT("api/v1/volunteers/{id}")
    Call<VolunteerDTO> updateVolunteer(@Path("id") long id, @Body VolunteerDTO volunteerDTO);

    @DELETE("api/v1/volunteers/{id}")
    Call<Void> deleteVolunteer(@Path("id") long id);

    @PATCH("api/v1/volunteers/{id}/{centerId}")
    Call<Void> patchVolunteer(@Path("id") long id, @Path("centerId") long centerId);
}
