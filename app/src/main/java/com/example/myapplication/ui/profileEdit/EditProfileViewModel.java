package com.example.myapplication.ui.profileEdit;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.EventRepositoryImpl;
import com.example.myapplication.api.data.VolunteerRepositoryImpl;
import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.api.domain.useCases.volunteer.GetVolunteerByIdUseCase;
import com.example.myapplication.api.domain.useCases.volunteer.UpdateVolunteerUseCase;
import com.example.myapplication.core.RoleConstants;
import com.example.myapplication.ui.main.account.AccountViewModel;

public class EditProfileViewModel extends ViewModel {
    private final MutableLiveData<StateVolunteer> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<StateVolunteer> stateLiveData = mutableStateLiveData;

    private final MutableLiveData<String> mutableUpdateLiveData = new MutableLiveData<>();
    public final LiveData<String> updateLiveData = mutableUpdateLiveData;

    private final UpdateVolunteerUseCase updateVolunteerUseCase = new UpdateVolunteerUseCase(
            VolunteerRepositoryImpl.getInstance()
    );
    private final GetVolunteerByIdUseCase getVolunteerByIdUseCase = new GetVolunteerByIdUseCase(
            VolunteerRepositoryImpl.getInstance()
    );

    private VolunteerDTO volunteer;

    public void changeVolunteer(
            String name, String surname, String patronymic, String aboutMe, String telephone,
            String email, String birthday, String city, String telegramLink, String vkLink,
            long centerId, String profileImageUrl, boolean medicalBook, boolean driverLicense
    ) {
        volunteer = new VolunteerDTO(0, name, surname, patronymic, aboutMe, telephone, email,
                birthday, city, telegramLink, vkLink, RoleConstants.ROLE_USER, centerId, null,
                null, profileImageUrl, medicalBook, driverLicense);
    }

    public void getVolunteer(long id) {
        mutableStateLiveData.setValue(new StateVolunteer(null, null, true));
        getVolunteerByIdUseCase.execute(id, (status) -> {
            Log.d("Test", "(AccountViewModel.load) " + status.getErrors());
            mutableStateLiveData.postValue(new StateVolunteer(
                    status.getErrors() != null ? status.getErrors().getLocalizedMessage() : null,
                    status.getValue(),
                    false
            ));
        });
    }

    public void update(long id) {
        updateVolunteerUseCase.execute(id, volunteer, status -> {
            if (status.getStatusCode() == 200 || status.getStatusCode() == 201) mutableUpdateLiveData.postValue("Данные обновлены!");
            else mutableUpdateLiveData.postValue("Произошла ошибка :(");
        });
    }

    public static class StateVolunteer {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final VolunteerEntity user;

        private final boolean isLoading;

        public StateVolunteer(@Nullable String errorMessage, @Nullable VolunteerEntity user, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.user = user;
            this.isLoading = isLoading;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public VolunteerEntity getVolunteer() {
            return user;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }
}
