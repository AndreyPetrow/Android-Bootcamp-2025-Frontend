package com.example.myapplication.ui.profileEdit;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.databinding.FragmentEditVolunteerBinding;
import com.example.myapplication.utils.DownloadImage;
import com.example.myapplication.utils.TextChangedListener;
import com.example.myapplication.utils.Utils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.Calendar;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class EditVolunteerFragment extends Fragment {
    private FragmentEditVolunteerBinding binding;
    private EditProfileViewModel viewModel;
    private Window window;

    private Integer year;
    private Integer month;
    private Integer day;

    private long canterId;
    private String profileImageUrl;

    public EditVolunteerFragment() {
        super(R.layout.fragment_edit_volunteer);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentEditVolunteerBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        window = this.requireActivity().getWindow();


        binding.updateButton.setOnClickListener(view1 -> {
            Log.d("Test", "Я зашел сюда");
            String localeDate;
            if (year != null) {
                localeDate = LocalDate.of(year, month, day).toString();
            } else {
                localeDate = String.valueOf(binding.dateBirth.getText());
            }

            viewModel.changeVolunteer(
                    String.valueOf(binding.profileName.getText()),
                    String.valueOf(binding.profileSurname.getText()),
                    String.valueOf(binding.profilePatronymic.getText()),
                    String.valueOf(binding.profileAboutMe.getText()),
                    String.valueOf(binding.profileContactsTelephone.getText()),
                    String.valueOf(binding.profileContactsEmail.getText()),
                    localeDate, String.valueOf(binding.cityAutoCompleteTextView.getText()),
                    String.valueOf(binding.profileContactsTelegram.getText()),
                    String.valueOf(binding.profileContactsVk.getText()), canterId,
                    profileImageUrl, binding.medicalCheckBox.isChecked(),
                    binding.driverLicenseCheckBox.isChecked()
            );
            viewModel.update(getVolunteerId());
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        binding.dateBirthLayout.setEndIconOnClickListener(this::onClickListenerDateBirthLayout);
        binding.dateBirthLayout.setErrorIconOnClickListener(this::onClickListenerDateBirthLayout);

        setCitiesAdapter(view);
        binding.arrowBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());
        setMaskInTelephone(binding.profileContactsTelephone);

        binding.profileName.addTextChangedListener(new TextChangedListener<>(binding.profileName) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                standardListener(binding.profileNameLayout, s);
            }
        });

        binding.profileSurname.addTextChangedListener(new TextChangedListener<>(binding.profileSurname) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                standardListener(binding.profileSurnameLayout, s);
            }
        });

        binding.cityAutoCompleteTextView.addTextChangedListener(new TextChangedListener<>(binding.cityAutoCompleteTextView) {
            @Override
            public void onTextChanged(AutoCompleteTextView target, Editable s) {
                standardListener(binding.cityLayout, s);
            }
        });

        binding.profileContactsEmail.addTextChangedListener(new TextChangedListener<>(binding.profileContactsEmail) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                listenerEmailEditText(s);
            }
        });
        binding.profileContactsTelephone.addTextChangedListener(new TextChangedListener<>(binding.profileContactsTelephone) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                listenerTelephoneEditText(s);
            }
        });

        subscribe();
        viewModel.getVolunteer(getVolunteerId());
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

            binding.allContent.setVisibility(Utils.visibleOrGone(isSuccess));

            if (isSuccess) addContent(state.getVolunteer());
        });

        viewModel.updateLiveData.observe(getViewLifecycleOwner(), state -> Snackbar.make(requireView(), state, Snackbar.LENGTH_LONG).show());
    }

    private void addContent(VolunteerEntity volunteer) {
        canterId = volunteer.getCenter();
        profileImageUrl = volunteer.getProfileImageUrl();
        if (volunteer.getProfileImageUrl() != null)
            downloadImage(binding.profileImage, volunteer.getProfileImageUrl());
        binding.profileName.setText(volunteer.getName());
        binding.profileSurname.setText(volunteer.getSurname());

        if (volunteer.getPatronymic() == null) binding.profilePatronymic.setText("");
        else binding.profilePatronymic.setText(volunteer.getPatronymic());

        if (volunteer.getAboutMe() == null) binding.profileAboutMe.setText("");
        else binding.profileAboutMe.setText(volunteer.getAboutMe());

        binding.dateBirth.setText(volunteer.getBirthday());
        binding.cityAutoCompleteTextView.setText(volunteer.getCity());

        binding.medicalCheckBox.setChecked(volunteer.isMedicalBook());
        binding.driverLicenseCheckBox.setChecked(volunteer.isDriverLicense());

        binding.profileContactsEmail.setText(volunteer.getEmail());
        binding.profileContactsTelephone.setText(volunteer.getTelephone());

        if (volunteer.getTelegramLink() == null) binding.profileContactsTelegram.setText("");
        else binding.profileContactsTelegram.setText(volunteer.getTelegramLink());

        if (volunteer.getVkLink() == null) binding.profileContactsVk.setText("");
        else binding.profileContactsVk.setText(volunteer.getVkLink());
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


    public void standardListener(TextInputLayout textInputLayout, Editable s) {
        if (s.toString().isEmpty()) textInputLayout.setError("Обязательное поле");
        else textInputLayout.setErrorEnabled(false);
    }

    private void listenerEmailEditText(Editable s) {
        if (s.toString().isEmpty()) {
            binding.profileContactsEmailLayout.setError("Обязательное поле");
        } else if (!isEmailValid(s.toString())) {
            binding.profileContactsEmailLayout.setError("Неверный формат");
        } else {
            binding.profileContactsEmailLayout.setErrorEnabled(false);
        }
    }

    private void listenerTelephoneEditText(Editable s) {
        if (s.toString().isEmpty()) {
            binding.profileContactsTelephoneLayout.setError("Обязательное поле");
        } else if (!isTelephoneValid(s.toString())) {
            binding.profileContactsTelephoneLayout.setError("Неверный формат");
        } else {
            binding.profileContactsTelephoneLayout.setErrorEnabled(false);
        }
    }

    private void onClickListenerDateBirthLayout(View view) {
        final Calendar c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, year, month, day) -> setDateBirthText(year, month, day, "."), year, month, day);
        datePickerDialog.show();
    }

    @SuppressLint("DefaultLocale")  // Это, чтобы IDEA не ругалась
    private void setDateBirthText(int year, int month, int day, String separator) {
        if (month < 9) {
            binding.dateBirth.setText(String.format("%d" + separator + "0%d" + separator + "%d", day, month + 1, year));
        } else {
            binding.dateBirth.setText(String.format("%d" + separator + "%d" + separator + "%d", day, month + 1, year));
        }

        binding.dateBirthLayout.setErrorEnabled(false);
    }

    private void setCitiesAdapter(View view) {
        String[] cities = getResources().getStringArray((R.array.cities));
        binding.cityAutoCompleteTextView.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.dropdown_item, cities));
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isTelephoneValid(String telephone) {
        return Patterns.PHONE.matcher(telephone).matches();
    }

    private void setMaskInTelephone(TextInputEditText telephone) {
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(telephone);
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

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}