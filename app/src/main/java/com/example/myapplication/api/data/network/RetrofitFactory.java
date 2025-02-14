package com.example.myapplication.api.data.network;

import com.example.myapplication.api.data.CredentialsDataSource;
import com.example.myapplication.api.data.source.CenterApi;
import com.example.myapplication.api.data.source.NotificationApi;
import com.example.myapplication.api.data.source.SignApi;
import com.example.myapplication.api.data.source.VolunteerApi;
import com.example.myapplication.core.UrlConstants;
import com.example.myapplication.api.data.source.EventApi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static RetrofitFactory INSTANCE;

    private RetrofitFactory() {
    }

    public static synchronized RetrofitFactory getInstance() {
        if (INSTANCE == null) INSTANCE = new RetrofitFactory();
        return INSTANCE;
    }

    private final OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                String authData = CredentialsDataSource.getInstance().getAuthData();

                if (authData == null) return chain.proceed(chain.request());
                else {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", authData)
                            .build();
                    return chain.proceed(request);
                }
            });

    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(UrlConstants.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public VolunteerApi getVolunteerApi() {
        return retrofit.create(VolunteerApi.class);
    }

    public EventApi getEventApi() {
        return retrofit.create(EventApi.class);
    }

    public SignApi getSignApi() {
        return retrofit.create(SignApi.class);
    }

    public NotificationApi getNotificationApi() {
        return retrofit.create(NotificationApi.class);
    }

    public CenterApi getCenterApi() {
        return retrofit.create(CenterApi.class);
    }
}
