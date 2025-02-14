package com.example.myapplication.ui.fullEvent;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.EventRepositoryImpl;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.useCases.event.GetEventByIdUseCase;

public class FullEventViewModel extends ViewModel {
    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<State> stateLiveData = mutableStateLiveData;

    private final GetEventByIdUseCase getEventByIdUseCase = new GetEventByIdUseCase(
            EventRepositoryImpl.getInstance()
    );
    
    public Long eventId;
    
    public void changeEventId(Long eventId) {
        if (eventId == -1) throw new IllegalStateException("eventId не может быть null");
        this.eventId = eventId;
    }

    public void update() {
        mutableStateLiveData.setValue(new State(null, null, true));
        
        if (eventId == null) throw new IllegalStateException("eventId не может быть null!");
        getEventByIdUseCase.execute(eventId, status -> {
            mutableStateLiveData.postValue(fromStatus(status));
        });
    }

    private State fromStatus(Status<EventEntity> status) {
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
        private final EventEntity items;

        private final boolean isLoading;

        public State(@Nullable String errorMessage, @Nullable EventEntity items, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.items = items;
            this.isLoading = isLoading;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public EventEntity getItem() {
            return items;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }
}
