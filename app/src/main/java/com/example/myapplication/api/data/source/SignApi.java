package com.example.myapplication.api.data.source;

import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SignApi {
    @POST("api/v1/volunteers/register")
    Call<VolunteerDTO> register(@Body VolunteerRegisterDTO volunteerRegisterDTO);

    @GET("api/v1/volunteers/login")
    Call<VolunteerDTO> login();

    @GET("api/v1/volunteer/check/email/{email}")
    Call<VolunteerDTO> isExist(@Path("email") String email);
}
