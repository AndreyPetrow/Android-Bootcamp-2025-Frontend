package com.example.myapplication.ui.main.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.view.Window;

import com.example.myapplication.R;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.databinding.FragmentNotificationBinding;
import com.example.myapplication.utils.Utils;


public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;
    private NotificationViewModel viewModel;
    private NotificationRecycleViewAdapter adapter;
    private Window window;

    public NotificationFragment() {
        super(R.layout.fragment_notification);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentNotificationBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        adapter = new NotificationRecycleViewAdapter(this::deleteNotificationListener);
        binding.notificationRecycleView.setAdapter(adapter);
        binding.refresh.setOnRefreshListener(() -> viewModel.update(getVolunteerId()));
        window = this.requireActivity().getWindow();

        subscribe();
        viewModel.update(getVolunteerId());
    }

    private void subscribe() {
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), stateNotification -> {
            boolean isSuccess = !stateNotification.isLoading()
                    && stateNotification.getErrorMessage() == null
                    && stateNotification.getNotification() != null;

            binding.refresh.setEnabled(!stateNotification.isLoading());
            if (!stateNotification.isLoading()) binding.refresh.setRefreshing(false);
            binding.notificationRecycleView.setVisibility(Utils.visibleOrGone(isSuccess));
            binding.notificationLoading.setVisibility(Utils.visibleOrGone(stateNotification.isLoading()));

            if (stateNotification.getErrorMessage() != null) {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(true));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.error_banner_background));
            } else {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.gray));
            }

            if (stateNotification.getErrorMessage() == null && stateNotification.getNotification() != null) {
                adapter.updateNotification(stateNotification.getNotification());
            }
        });
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

    private void deleteNotificationListener(long notificationId) {
        // todo: add delete notification
    }
}