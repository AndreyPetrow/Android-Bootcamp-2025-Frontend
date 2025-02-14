package com.example.myapplication.ui.main.account;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.VolunteerRepositoryImpl;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.api.domain.useCases.volunteer.GetVolunteerByEmailUseCase;
import com.example.myapplication.api.domain.useCases.volunteer.GetVolunteerByIdUseCase;
import com.example.myapplication.api.domain.useCases.volunteer.GetVolunteerListUseCase;

public class AccountViewModel extends ViewModel {
    private final MutableLiveData<StateVolunteer> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<StateVolunteer> stateLiveData = mutableStateLiveData;

    private final GetVolunteerByIdUseCase getVolunteerByIdUseCase = new GetVolunteerByIdUseCase(
            VolunteerRepositoryImpl.getInstance()
    );

    public void load(long id) {
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
