package com.example.myapplication.api.domain;

import androidx.annotation.NonNull;

import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.center.CenterEntity;

import java.util.List;
import java.util.function.Consumer;

public interface CenterRepository {
    void getAllCenters(@NonNull Consumer<Status<List<CenterEntity>>> callback);
}
