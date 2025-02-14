package com.example.myapplication.ui.login.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.SignRepositoryImpl;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.api.domain.useCases.sign.CreateVolunteerUseCase;
import com.example.myapplication.api.domain.useCases.sign.LoginVolunteerUseCase;
import com.example.myapplication.core.RoleConstants;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<String> mutableErrorLiveData = new MutableLiveData<>();
    public final LiveData<String> errorLiveData = mutableErrorLiveData;

    private final MutableLiveData<VolunteerEntity> mutableCreateLiveData = new MutableLiveData<>();
    public final LiveData<VolunteerEntity> createLiveData = mutableCreateLiveData;

    private final MutableLiveData<VolunteerEntity> mutableOpenLiveData = new MutableLiveData<>();
    public final LiveData<VolunteerEntity> openLiveData = mutableOpenLiveData;

    private final CreateVolunteerUseCase createVolunteerUseCase = new CreateVolunteerUseCase(
            SignRepositoryImpl.getInstance()
    );
    private final LoginVolunteerUseCase loginVolunteerUseCase = new LoginVolunteerUseCase(
            SignRepositoryImpl.getInstance()
    );

    private VolunteerRegisterDTO volunteer;

    public void changeVolunteer(String name, String surname, String telephone, String email, String password, String birthday, String city) {
        volunteer = new VolunteerRegisterDTO(0, name, surname, null, null, telephone, email, password, birthday,
                city, null, null, RoleConstants.ROLE_USER, null, null, null, null, false, false);
    }
    
    public void createVolunteer() {
        createVolunteerUseCase.execute(volunteer, status -> {
            if ((status.getStatusCode() == 200 || status.getStatusCode() == 201) && status.getValue() != null && status.getErrors() == null) {
                mutableCreateLiveData.postValue(status.getValue());
            } else if (status.getStatusCode() == 409) {
                mutableErrorLiveData.postValue("Пользователь с такими данными уже существует :(");
            } else {
                mutableErrorLiveData.postValue(status.getStatusCode() + " " + status.getValue() + " " + status.getErrors());
            }
        });
    }

    public void login() {
        if (volunteer == null || volunteer.email == null || volunteer.password == null)
            throw new IllegalStateException("volunteer не может быть null!");

        loginVolunteerUseCase.execute(volunteer.email, volunteer.password, status -> {
            if (status.getStatusCode() == 200 && status.getErrors() == null && status.getValue() != null) {
                mutableOpenLiveData.postValue(status.getValue());
            } else if (status.getStatusCode() == 401) mutableErrorLiveData.postValue("Данные не верны. Попробуйте ещё разок :(");
            else {
                mutableErrorLiveData.postValue("Вы не подключены к интернету :(");
            }
        });
    }
}
