package com.example.myapplication.api.domain.useCases.center;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.CenterRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.center.CenterEntity;

import java.util.List;
import java.util.function.Consumer;

public class GetAllCentersUseCase {
    private final CenterRepository centerRepository;

    public GetAllCentersUseCase(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public void execute(@NonNull Consumer<Status<List<CenterEntity>>> callback) {
        centerRepository.getAllCenters(callback);
    }
}
