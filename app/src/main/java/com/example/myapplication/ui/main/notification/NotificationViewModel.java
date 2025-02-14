package com.example.myapplication.ui.main.notification;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.NotificationRepositoryImpl;
import com.example.myapplication.api.domain.entity.NotificationEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.useCases.notification.GetAllByRecipientIdUseCase;
import com.example.myapplication.ui.main.event.EventViewModel;

import java.util.List;

public class NotificationViewModel extends ViewModel {
    private final MutableLiveData<StateNotification> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<StateNotification> stateLiveData = mutableStateLiveData;

    public GetAllByRecipientIdUseCase getAllByRecipientIdUseCase = new GetAllByRecipientIdUseCase(
            NotificationRepositoryImpl.getInstance()
    );

    public void update(long id) {
        mutableStateLiveData.postValue(new StateNotification(null, null, true));
        getAllByRecipientIdUseCase.execute(id, listStatus -> mutableStateLiveData.postValue(fromStatus(listStatus)));
    }

    private StateNotification fromStatus(Status<List<NotificationEntity>> status) {
        return new StateNotification(
                status.getErrors() != null ? status.getErrors().getLocalizedMessage() : null,
                status.getValue(),
                false
        );
    }

    public static class StateNotification {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final List<NotificationEntity> notifications;

        private final boolean isLoading;

        public StateNotification(@Nullable String errorMessage, @Nullable List<NotificationEntity> notifications, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.notifications = notifications;
            this.isLoading = isLoading;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public List<NotificationEntity> getNotification() {
            return notifications;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }
}
