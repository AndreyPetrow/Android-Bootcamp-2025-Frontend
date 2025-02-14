package com.example.myapplication.ui.main.searchVolunteer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.core.BundleConstants;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.databinding.FragmentVolunteerSearchBinding;
import com.example.myapplication.ui.fullEvent.FullEventFragment;
import com.example.myapplication.ui.main.account.AccountFragment;
import com.example.myapplication.utils.TextChangedListener;
import com.example.myapplication.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SearchVolunteerFragment extends Fragment {
    private FragmentVolunteerSearchBinding binding;
    private SearchVolunteerViewModel viewModel;
    private VolunteerRecycleViewAdapter adapter;
    private Window window;
    @Nullable private List<ItemVolunteerEntity> volunteers;


    public SearchVolunteerFragment() {
        super(R.layout.fragment_volunteer_search);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentVolunteerSearchBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(SearchVolunteerViewModel.class);
        window = this.requireActivity().getWindow();
        binding.refresh.setOnRefreshListener(() -> viewModel.update());

        adapter = new VolunteerRecycleViewAdapter(this::pushNotificationListener, this::openFullProfile);
        binding.volunteerRecyclerView.setAdapter(adapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Свободные"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Занятые"));

        binding.search.addTextChangedListener(new TextChangedListener<>(binding.search) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                search(s.toString());
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewModel.changeTypeFilter(tab.getPosition() == 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        subscribe();
        viewModel.changeTypeFilter(true);
    }

    private void search(String s) {
        if (volunteers == null) return;

        List<ItemVolunteerEntity> filteredVolunteers = new ArrayList<>();
        String searchText = s.toLowerCase();

        for (ItemVolunteerEntity volunteer : volunteers) {
            if (fullnameVolunteer(volunteer).toLowerCase().contains(searchText)) filteredVolunteers.add(volunteer);
            else if (volunteer.getCity().toLowerCase().contains(searchText)) filteredVolunteers.add(volunteer);
            else if (volunteer.getCenterName() != null) {
                if (volunteer.getCenterName().toLowerCase().contains(searchText)) filteredVolunteers.add(volunteer);
            }
        }

        if (filteredVolunteers.isEmpty()) binding.notFoundIssueView.setVisibility(Utils.visibleOrGone(true));
        else binding.notFoundIssueView.setVisibility(Utils.visibleOrGone(false));
        adapter.updateVolunteers(filteredVolunteers);
    }

    private void subscribe() {
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            boolean isSuccess = !state.isLoading()
                    && state.getErrorMessage() == null
                    && state.getVolunteer() != null;

            binding.refresh.setEnabled(!state.isLoading());
            if (!state.isLoading()) binding.refresh.setRefreshing(false);
            binding.userListLoading.setVisibility(Utils.visibleOrGone(state.isLoading()));

            binding.volunteerRecyclerView.setVisibility(Utils.visibleOrGone(isSuccess));

            binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));

            if (state.getErrorMessage() != null) {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(true));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.error_banner_background));
            } else {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.gray));
            }

            if (isSuccess) {
                volunteers = state.getVolunteer();
                search(String.valueOf(binding.search.getText()));
            }
        });

        viewModel.notificationLiveData.observe(getViewLifecycleOwner(), state -> {
            if (state) Snackbar.make(requireView(), "Приглашение отправлено!", Snackbar.LENGTH_LONG).show();
            else Snackbar.make(requireView(), "Произошла ошибка! Сообщение не отправлено!", Snackbar.LENGTH_LONG).show();
        });
    }

    private void openFullProfile(long volunteerId) {
        this.requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, AccountFragment.class, getBundle(volunteerId))
                .addToBackStack("")
                .commit();
    }

    private Bundle getBundle(Long volunteerId) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleConstants.VOLUNTEER_ID, volunteerId);
        return bundle;
    }

    private void pushNotificationListener(Long volunteerId) {
        String message = "Вас пригласили присоединится к волонтерскому центру \"" + getCenterName() + "\"!";
        viewModel.sendNotification(volunteerId, getVolunteerId(), message, LocalDate.now());
    }

    private String fullnameVolunteer(ItemVolunteerEntity volunteer) {
        if (volunteer.getPatronymic() == null)
            return volunteer.getName() + " " + volunteer.getSurname();
        else
            return volunteer.getName() + " " + volunteer.getSurname() + " " + volunteer.getPatronymic();
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

    private String getCenterName() {
        SharedPreferences settings = this.requireView().getContext().getSharedPreferences(
                SettingConstants.PREFS_FILE, Context.MODE_PRIVATE
        );
        String centerName = settings.getString(SettingConstants.PREF_CENTER_NAME, SettingConstants.ERROR_CENTER_NAME);
        if (centerName.equals(SettingConstants.ERROR_CENTER_NAME))
            throw new IllegalStateException("Center name is null!");
        else return centerName;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}