package com.example.myapplication.ui.main.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.example.myapplication.databinding.FragmentMapBinding;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment {
    private MapViewModel viewModel;
    private Window window;
    private FragmentMapBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentMapBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        window = this.requireActivity().getWindow();

        subscribe();
        viewModel.load();
    }

    private void subscribe() {
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            boolean isSuccess = !state.isLoading()
                    && state.getErrorMessage() == null
                    && state.getCenters() != null;

            if (state.getErrorMessage() != null) {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(true));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.error_banner_background));
            } else {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.gray));
            }

            if (isSuccess) {
                SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
                fragment.getMapAsync(new MapService(this.requireContext(), state.getCenters(), this::listener));
            }
        });
        viewModel.patchLiveData.observe(getViewLifecycleOwner(), state -> {
            if (state.getErrorMessage() != null) {
                Toast.makeText(requireActivity(), "Вам не удалось присоединиться :(", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireActivity(), "Вы присоединились!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void listener(Long centerId) {
        viewModel.patchVolunteer(getVolunteerId(), centerId);
    }


    private Long getVolunteerId() {
        SharedPreferences settings = this.requireView().getContext().getSharedPreferences(
                SettingConstants.PREFS_FILE, Context.MODE_PRIVATE
        );
        long volunteerId = settings.getLong(SettingConstants.PREF_ID, SettingConstants.ERROR_ID);
        if (volunteerId == SettingConstants.ERROR_ID)
            throw new IllegalStateException("ID is null!");
        else return volunteerId;
    }
    // mapView = view.findViewById(R.id.mapView);
    // https://yandex.ru/dev/mapkit/doc/ru/android/generated/getting_started
}