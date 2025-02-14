package com.example.myapplication.ui.login.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.SignRepositoryImpl;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.api.domain.useCases.sign.LoginVolunteerUseCase;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> mutableErrorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = mutableErrorLiveData;

    private final MutableLiveData<VolunteerEntity> mutableOpenLiveData = new MutableLiveData<>();
    public final LiveData<VolunteerEntity> openLiveData = mutableOpenLiveData;

    private final LoginVolunteerUseCase loginVolunteerUseCase = new LoginVolunteerUseCase(
            SignRepositoryImpl.getInstance()
    );

    @Nullable
    private String email;
    @Nullable
    private String password;

    public void changeEmail(@NonNull String email) {
        this.email = email;
    }

    public void changePassword(@NonNull String password) {
        this.password = password;
    }

    public void confirm() {
        final String currentEmail = email;
        final String currentPassword = password;

        if (currentEmail == null || currentEmail.isEmpty()) {
            mutableErrorLiveData.postValue("Пароль пустой!");
            return;
        }
        if (currentPassword == null || currentPassword.isEmpty()) {
            mutableErrorLiveData.postValue("email пустой!");
            return;
        }
        loginVolunteerUseCase.execute(currentEmail, currentPassword, status -> {
            if (status.getStatusCode() == 200 && status.getErrors() == null && status.getValue() != null) {
                mutableOpenLiveData.postValue(status.getValue());
            } else if (status.getStatusCode() == 401) mutableErrorLiveData.postValue("Данные не верны. Попробуйте ещё разок :(");
            else mutableErrorLiveData.postValue("Вы не подключены к интернету :(");
        });
    }

}
