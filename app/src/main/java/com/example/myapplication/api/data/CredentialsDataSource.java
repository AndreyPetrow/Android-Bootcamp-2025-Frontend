package com.example.myapplication.api.data;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.network.RetrofitFactory;
import com.example.myapplication.api.data.source.SignApi;

import org.jetbrains.annotations.Nullable;

import okhttp3.Credentials;

public class CredentialsDataSource {
    private static CredentialsDataSource INSTANCE;
    private final SignApi signApi = RetrofitFactory.getInstance().getSignApi();

    private CredentialsDataSource() {
    }

    public static synchronized CredentialsDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new CredentialsDataSource();
        return INSTANCE;
    }

    @Nullable private String authData = null;

    public @Nullable String getAuthData() {
        return authData;
    }

    public void updateLogin(@NonNull String email, @Nullable String password) {
        authData = Credentials.basic(email, password);
    }

    public void logout() {
        authData = null;
    }
}
