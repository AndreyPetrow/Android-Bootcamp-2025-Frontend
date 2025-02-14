package com.example.myapplication.ui.main.event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.view.View;
import android.view.Window;

import com.example.myapplication.core.BundleConstants;
import com.example.myapplication.ui.fullEvent.FullEventFragment;
import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.databinding.FragmentEventBinding;

import com.example.myapplication.ui.login.AuthActivity;
import com.example.myapplication.utils.TextChangedListener;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class EventFragment extends Fragment {
    private FragmentEventBinding binding;
    private EventViewModel eventViewModel;
    @Nullable private Location lastLocation;
    private FusedLocationProviderClient fusedLocationClient;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 100;
    @Nullable private List<EventEntity> eventEntityList;
    private EventRecycleViewAdapter adapter;
    private Window window;


    public EventFragment() {
        super(R.layout.fragment_event);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentEventBinding.bind(view);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        window = this.requireActivity().getWindow();

        binding.refresh.setOnRefreshListener((this::checkPermission));
        adapter = new EventRecycleViewAdapter(this::openFullEvent);
        binding.eventsRecyclerView.setAdapter(adapter);
        binding.search.addTextChangedListener(new TextChangedListener<>(binding.search) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                search(s.toString());
            }
        });

        subscribe();
        checkPermission();
    }

    private void search(String s) {
        if (eventEntityList == null) return;

        List<EventEntity> eventEntities = new ArrayList<>();
        String searchText = s.toLowerCase();

        for (EventEntity eventEntity : eventEntityList) {
            if (eventEntity.getName().toLowerCase().contains(searchText)) eventEntities.add(eventEntity);
            else if (eventEntity.getAddress().toLowerCase().contains(searchText)) eventEntities.add(eventEntity);
            else if (eventEntity.getCenterName().toLowerCase().contains(searchText)) eventEntities.add(eventEntity);
        }

        if (eventEntities.isEmpty()) binding.notFoundIssueView.setVisibility(Utils.visibleOrGone(true));
        else binding.notFoundIssueView.setVisibility(Utils.visibleOrGone(false));
        adapter.updateData(eventEntities, lastLocation);
    }

    private void subscribe() {
        eventViewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            if (Objects.equals(state.getErrorMessage(), "401")) {
                startActivity(new Intent(requireActivity(), AuthActivity.class));
                requireActivity().finish();
                return;
            }

            boolean isSuccess = !state.isLoading()
                    && state.getErrorMessage() == null
                    && state.getItems() != null;
            binding.refresh.setEnabled(!state.isLoading());
            if (!state.isLoading()) binding.refresh.setRefreshing(false);
            binding.eventsRecyclerView.setVisibility(Utils.visibleOrGone(isSuccess));
            binding.eventsLoading.setVisibility(Utils.visibleOrGone(state.isLoading()));
            binding.searchLayout.setVisibility(Utils.visibleOrGone(isSuccess));
            binding.search.setVisibility(Utils.visibleOrGone(isSuccess));

            binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));

            if (state.getErrorMessage() != null) {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(true));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.error_banner_background));
            } else {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.gray));
            }

            if (isSuccess) {
                eventEntityList = state.getItems();
                search(String.valueOf(binding.search.getText()));
            }
        });
    }

    private void openFullEvent(long eventId) {
        this.requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, FullEventFragment.class, getBundle(eventId))
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this.requireActivity(), location -> {
            lastLocation = location;
            eventViewModel.lastLocation = location;
            eventViewModel.update();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) getLastLocation();
            else eventViewModel.update();
        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        } else getLastLocation();
    }

    private Bundle getBundle(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleConstants.EVENT_ID, id);

        if (lastLocation == null) bundle.putDoubleArray(BundleConstants.EVENT_COORDINATES, new double[] {-1, -1});
        else bundle.putDoubleArray(BundleConstants.EVENT_COORDINATES, new double[] {lastLocation.getLatitude(), lastLocation.getLongitude()});
        return bundle;
    }
}