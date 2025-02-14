package com.example.myapplication.ui.main.event;

import android.location.Location;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.EventRepositoryImpl;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.useCases.event.GetAllBySortedEvents;
import com.example.myapplication.api.domain.useCases.event.GetAllEventsUseCase;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class EventViewModel extends ViewModel {
    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<State> stateLiveData = mutableStateLiveData;

    private final GetAllEventsUseCase getAllEventsUseCase = new GetAllEventsUseCase(
            EventRepositoryImpl.getInstance()
    );
    private final GetAllBySortedEvents getAllBySortedEvents = new GetAllBySortedEvents(
            EventRepositoryImpl.getInstance()
    );

    @Nullable
    public Location lastLocation;

    public EventViewModel() {}

    public void update() {
        mutableStateLiveData.setValue(new State(null, null, true));

        if (lastLocation == null) {
            getAllEventsUseCase.execute(status -> mutableStateLiveData.postValue(fromStatus(status)));
        } else {
            getAllBySortedEvents.execute(lastLocation.getLatitude(), lastLocation.getLongitude(), status -> {
                if (status.getStatusCode() == 401) {
                    mutableStateLiveData.postValue(new State("401", null, true));
                    return;
                }
                mutableStateLiveData.postValue(fromStatus(status));
            });
        }
    }

    private State fromStatus(Status<List<EventEntity>> status) {
        return new State(
                status.getErrors() != null ? status.getErrors().getLocalizedMessage() : null,
                status.getValue(),
                false
        );
    }

    public static class State {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final List<EventEntity> items;

        private final boolean isLoading;

        public State(@Nullable String errorMessage, @Nullable List<EventEntity> items, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.items = items;
            this.isLoading = isLoading;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public List<EventEntity> getItems() {
            return items;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }
}
