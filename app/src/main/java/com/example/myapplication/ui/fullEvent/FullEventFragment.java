package com.example.myapplication.ui.fullEvent;

import static java.util.Collections.min;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.core.BundleConstants;
import com.example.myapplication.databinding.FragmentFullEventBinding;
import com.example.myapplication.utils.DownloadImage;
import com.example.myapplication.utils.Utils;
import com.google.android.material.imageview.ShapeableImageView;


public class FullEventFragment extends Fragment {
    private FragmentFullEventBinding binding;
    private FullEventViewModel viewModel;
    private Window window;

    public FullEventFragment() {
        super(R.layout.fragment_full_event);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ОСТОРОЖНО ДАЛЬШЕ ГОВНОКОД!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        binding = FragmentFullEventBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(FullEventViewModel.class);
        window = this.requireActivity().getWindow();

        if (getArguments() == null)
            throw new IllegalStateException("В FullEventFragment должен передаваться Bundle");

        binding.refresh.setOnRefreshListener(() -> {
            viewModel.changeEventId(getArguments().getLong(BundleConstants.EVENT_ID, -1));
            viewModel.update();
        });

        binding.arrowBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        subscribe();
        viewModel.changeEventId(getArguments().getLong(BundleConstants.EVENT_ID, -1));
        viewModel.update();
    }

    private void subscribe() {

        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            boolean isSuccess = !state.isLoading()
                    && state.getErrorMessage() == null
                    && state.getItem() != null;

            binding.refresh.setEnabled(!state.isLoading());
            if (!state.isLoading()) binding.refresh.setRefreshing(false);
            binding.fullEventLoading.setVisibility(Utils.visibleOrGone(state.isLoading()));

            binding.mainLayout.setVisibility(Utils.visibleOrGone(isSuccess));

            if (state.getErrorMessage() != null) {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(true));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.error_banner_background));
            } else {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.gray));
            }

            if (isSuccess) addContent(state.getItem());
        });
    }

    private void addContent(EventEntity event) {
        downloadImage(binding.eventImage, event.getImageLink());
        binding.fullEventName.setText(event.getName());
        binding.fullEventDescription.setText(event.getDescription());
        binding.fullEventPlace.setText(event.getAddress());

        binding.fullEventOrganizer.setText(event.getCenterName());

        if (getArguments() == null)
            throw new IllegalStateException("В FullEventFragment должен передаваться Bundle");
        double[] coordinates = getArguments().getDoubleArray(BundleConstants.EVENT_COORDINATES);
        if (coordinates == null)
            throw new IllegalStateException("В FullEventFragment должен передаваться Bundle");

        if (coordinates[0] == -1 || coordinates[1] == -1)
            binding.fullEventDistance.setText("Неизвестно");
        else {
            float[] results = new float[5];
            Location.distanceBetween(
                    event.getLatitude(),
                    event.getLongitude(),
                    coordinates[0],
                    coordinates[1],
                    results
            );

            binding.fullEventDistance.setText(Utils.convertDistance(results[0]));
        }
    }

    private void downloadImage(ShapeableImageView shapeableImageView, String urlImage) {
        Log.d("Test", "(MainFragment) Начинаем скачивать изображение с URl: " + urlImage);
        new DownloadImage(shapeableImageView).execute(urlImage);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}