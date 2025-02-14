package com.example.myapplication.ui.main.account;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.core.BundleConstants;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.example.myapplication.ui.fullEvent.FullEventFragment;
import com.example.myapplication.ui.login.AuthActivity;
import com.example.myapplication.ui.profileEdit.EditVolunteerFragment;
import com.example.myapplication.utils.DownloadImage;
import com.example.myapplication.utils.Utils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;
    private Window window;
    private Long volunteerId;

    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAccountBinding.bind(view);

        if (getArguments() != null) {
            volunteerId = getArguments().getLong(BundleConstants.VOLUNTEER_ID, -1);
            binding.arrowBack.setVisibility(Utils.visibleOrGone(true));
            binding.arrowBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());
            binding.editProfile.setVisibility(Utils.visibleOrGone(false));
            binding.exitProfile.setVisibility(Utils.visibleOrGone(false));
        }

        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        window = this.requireActivity().getWindow();
        binding.refresh.setOnRefreshListener(() -> viewModel.load(getVolunteerId()));
        binding.editProfile.setOnClickListener((view_) -> editProfileClickListener());
        binding.profileID.setOnLongClickListener(view2 -> {
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", binding.profileID.getText().toString().substring(4));
            clipboard.setPrimaryClip(clip);
            Toast toast = Toast.makeText(requireActivity().getApplicationContext(), "Id скопирован", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        });
        binding.exitProfile.setOnClickListener(view1 -> {
            SharedPreferences settings = requireView().getContext().getSharedPreferences(
                    SettingConstants.PREFS_FILE, Context.MODE_PRIVATE
            );
            settings.edit().clear().apply();

            startActivity(new Intent(requireActivity(), AuthActivity.class));
            requireActivity().finish();
        });

        setMaskInTelephone(binding.profileContactsTelephone);
        subscribe();

        viewModel.load(getVolunteerId());
    }

    private void subscribe() {
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            boolean isSuccess = !state.isLoading()
                    && state.getErrorMessage() == null
                    && state.getVolunteer() != null;

            if (state.getErrorMessage() != null) {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(true));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.error_banner_background));
            } else {
                binding.connectionIssueView.setVisibility(Utils.visibleOrGone(false));
                window.setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.gray));
            }
            binding.profileLoading.setVisibility(Utils.visibleOrGone(state.isLoading()));
            binding.refresh.setEnabled(!state.isLoading());
            if (!state.isLoading()) binding.refresh.setRefreshing(false);

            binding.allContentProfile.setVisibility(Utils.visibleOrGone(isSuccess));

            if (isSuccess) addContent(state.getVolunteer());
        });
    }

    private void addContent(VolunteerEntity volunteer) {
        if (volunteer.getProfileImageUrl() != null)
            downloadImage(binding.profileImage, volunteer.getProfileImageUrl());
        binding.profileName.setText(fullnameVolunteer(volunteer));
        binding.profileID.setText(stringIdVolunteer(volunteer));
        binding.profileAboutMe.setText(volunteer.getAboutMe());
        binding.profileContactsEmail.setText(volunteer.getEmail());
        binding.profileContactsTelephone.setText(volunteer.getTelephone());
        binding.profileContactsTelegram.setText(volunteer.getTelegramLink());
        binding.profileContactsVk.setText(volunteer.getVkLink());

        if (volunteer.getCenter() == null) {
            binding.volunteerCenterNone.setVisibility(Utils.visibleOrGone(true));
            binding.volunteerCenterNotNone.setVisibility(Utils.visibleOrGone(false));
        } else {
            binding.volunteerCenterNone.setVisibility(Utils.visibleOrGone(false));
            binding.volunteerCenterNotNone.setVisibility(Utils.visibleOrGone(true));

            binding.volunteerCenter.setText(volunteer.getCenterName());
            downloadImage(binding.volunteerCenterImage, volunteer.getCenterImageUrl());
        }

        binding.profileCity.setText(volunteer.getCity());
        binding.profileDateBirth.setText(volunteer.getBirthday());
    }

    private void editProfileClickListener() {
        this.requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, EditVolunteerFragment.class, null)
                .addToBackStack("")
                .commit();
    }

    private String fullnameVolunteer(VolunteerEntity volunteer) {
        if (volunteer.getPatronymic() == null)
            return volunteer.getName() + " " + volunteer.getSurname();
        else
            return volunteer.getName() + " " + volunteer.getSurname() + " " + volunteer.getPatronymic();
    }

    private String stringIdVolunteer(VolunteerEntity volunteer) {
        return "ID: " + volunteer.getId();
    }

    private void downloadImage(ShapeableImageView shapeableImageView, String urlImage) {
        Log.d("Test", "(MainFragment) Начинаем скачивать изображение с URl: " + urlImage);
        new DownloadImage(shapeableImageView).execute(urlImage);
    }

    private void setMaskInTelephone(TextInputEditText telephone) {
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(telephone);
    }

    private Long getVolunteerId() {
        if (volunteerId != null) return volunteerId;

        SharedPreferences settings = this.requireView().getContext().getSharedPreferences(
                SettingConstants.PREFS_FILE, Context.MODE_PRIVATE
        );
        long volunteerId = settings.getLong(SettingConstants.PREF_ID, SettingConstants.ERROR_ID);
        if (volunteerId == SettingConstants.ERROR_ID)
            throw new IllegalStateException("ID is null!");
        else return volunteerId;
    }


    private Bundle getBundle() {
        return new Bundle();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}