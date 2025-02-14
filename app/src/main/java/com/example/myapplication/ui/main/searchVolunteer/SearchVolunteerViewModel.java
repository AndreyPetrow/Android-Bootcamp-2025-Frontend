package com.example.myapplication.ui.main.searchVolunteer;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.NotificationRepositoryImpl;
import com.example.myapplication.api.data.VolunteerRepositoryImpl;
import com.example.myapplication.api.data.dto.NotificationDTO;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.api.domain.useCases.notification.CreateNotificationUseCase;
import com.example.myapplication.api.domain.useCases.volunteer.GetFreeVolunteers;
import com.example.myapplication.api.domain.useCases.volunteer.GetNotFreeVolunteers;
import com.example.myapplication.api.domain.useCases.volunteer.GetVolunteerListUseCase;

import java.time.LocalDate;
import java.util.List;

public class SearchVolunteerViewModel extends ViewModel {
    private final MutableLiveData<StateVolunteer> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<StateVolunteer> stateLiveData = mutableStateLiveData;

    private final MutableLiveData<Boolean> mutableNotificationLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> notificationLiveData = mutableNotificationLiveData;

    private final GetFreeVolunteers getFreeVolunteers = new GetFreeVolunteers(
            VolunteerRepositoryImpl.getInstance()
    );
    private final GetVolunteerListUseCase getVolunteerListUseCase = new GetVolunteerListUseCase(
            VolunteerRepositoryImpl.getInstance()
    );
    private final GetNotFreeVolunteers getNotFreeVolunteers = new GetNotFreeVolunteers(
            VolunteerRepositoryImpl.getInstance()
    );

    private final CreateNotificationUseCase createNotificationUseCase = new CreateNotificationUseCase(
            NotificationRepositoryImpl.getInstance()
    );

    @Nullable
    public boolean isFree;

    public void changeTypeFilter(boolean isFree) {
        this.isFree = isFree;
        update();
    }

    public void update() {
        mutableStateLiveData.setValue(new StateVolunteer(null, null, true));

        // if (status.getStatusCode() == 200 && status.getErrors() == null && status.getValue() != null) {

        if (isFree)
            getFreeVolunteers.execute(status -> mutableStateLiveData.postValue(fromStatusVolunteer(status)));
        else
            getNotFreeVolunteers.execute(status -> mutableStateLiveData.postValue(fromStatusVolunteer(status)));
    }

    public void sendNotification(Long toWhom, Long fromWhom, String message, LocalDate dateDispatch) {
        createNotificationUseCase.execute(
                new NotificationDTO(0, toWhom, fromWhom, message, dateDispatch.toString()),
                status -> {
                    if ((status.getStatusCode() == 200 || status.getStatusCode() == 201) &&
                            status.getErrors() == null && status.getValue() != null) {
                        mutableNotificationLiveData.postValue(true);
                    } else mutableNotificationLiveData.postValue(false);
                }
        );
    }

    private StateVolunteer fromStatusVolunteer(Status<List<ItemVolunteerEntity>> status) {
        return new StateVolunteer(
                status.getErrors() != null ? status.getErrors().getLocalizedMessage() : null,
                status.getValue(),
                false
        );
    }


    public static class StateVolunteer {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final List<ItemVolunteerEntity> user;

        private final boolean isLoading;

        public StateVolunteer(@Nullable String errorMessage, @Nullable List<ItemVolunteerEntity> user, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.user = user;
            this.isLoading = isLoading;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public List<ItemVolunteerEntity> getVolunteer() {
            return user;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }
}
