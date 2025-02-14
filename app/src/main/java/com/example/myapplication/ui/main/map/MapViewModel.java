package com.example.myapplication.ui.main.map;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.data.CenterRepositoryImpl;
import com.example.myapplication.api.data.VolunteerRepositoryImpl;
import com.example.myapplication.api.domain.entity.center.CenterEntity;
import com.example.myapplication.api.domain.useCases.center.GetAllCentersUseCase;
import com.example.myapplication.api.domain.useCases.volunteer.PatchCenterIdVolunteerUseCase;

import java.util.List;

public class MapViewModel extends ViewModel {
    private final MutableLiveData<StateCenters> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<StateCenters> stateLiveData = mutableStateLiveData;

    private final MutableLiveData<StatePatch> mutablePatchLiveData = new MutableLiveData<>();
    public final LiveData<StatePatch> patchLiveData = mutablePatchLiveData;

    private final GetAllCentersUseCase getAllCentersUseCase = new GetAllCentersUseCase(
            CenterRepositoryImpl.getInstance()
    );
    private final PatchCenterIdVolunteerUseCase patchCenterIdVolunteerUseCase = new PatchCenterIdVolunteerUseCase(
            VolunteerRepositoryImpl.getInstance()
    );

    public void load() {
        mutableStateLiveData.setValue(new StateCenters(null, null, true));
        getAllCentersUseCase.execute(status -> mutableStateLiveData.postValue(new StateCenters(
                status.getErrors() != null ? status.getErrors().getLocalizedMessage() : null,
                status.getValue(),
                false
        )));
    }

    public void patchVolunteer(long id, long centerId) {
        patchCenterIdVolunteerUseCase.execute(id, centerId, voidStatus -> {
            mutablePatchLiveData.postValue(
                    new StatePatch(
                            voidStatus.getErrors() != null ? voidStatus.getErrors().getLocalizedMessage() : null,
                            voidStatus.getStatusCode()
                    )
            );
        });
    }

    public static class StateCenters {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final List<CenterEntity> centerEntities;

        private final boolean isLoading;

        public StateCenters(@Nullable String errorMessage, @Nullable List<CenterEntity> centerEntities, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.centerEntities = centerEntities;
            this.isLoading = isLoading;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public List<CenterEntity> getCenters() {
            return centerEntities;
        }

        public boolean isLoading() {
            return isLoading;
        }
    }

    public static class StatePatch {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final int status;


        public StatePatch(@Nullable String errorMessage, int status) {
            this.errorMessage = errorMessage;
            this.status = status;
        }

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public int getCenters() {
            return status;
        }

    }
}
