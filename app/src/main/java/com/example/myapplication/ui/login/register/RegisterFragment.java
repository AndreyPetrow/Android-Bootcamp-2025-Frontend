package com.example.myapplication.ui.login.register;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.databinding.FragmentRegisterBinding;
import com.example.myapplication.ui.login.login.LoginFragment;
import com.example.myapplication.ui.login.login.LoginViewModel;
import com.example.myapplication.ui.main.MainActivity;
import com.example.myapplication.utils.TextChangedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.Calendar;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    private int year;
    private int month;
    private int day;

    private boolean errorFlag;

    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentRegisterBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.dateBirthLayout.setEndIconOnClickListener(this::onClickListenerDateBirthLayout);
        binding.dateBirthLayout.setErrorIconOnClickListener(this::onClickListenerDateBirthLayout);

        setCitiesAdapter(view);
        setMaskInTelephone();

        binding.name.addTextChangedListener(new TextChangedListener<>(binding.name) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                standardListener(binding.nameLayout, s);
            }
        });

        binding.surname.addTextChangedListener(new TextChangedListener<>(binding.surname) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                standardListener(binding.surnameLayout, s);
            }
        });

        binding.cityAutoCompleteTextView.addTextChangedListener(new TextChangedListener<>(binding.cityAutoCompleteTextView) {
            @Override
            public void onTextChanged(AutoCompleteTextView target, Editable s) {
                standardListener(binding.cityLayout, s);
            }
        });

        binding.email.addTextChangedListener(new TextChangedListener<>(binding.email) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                listenerEmailEditText(s);
            }
        });
        binding.telephone.addTextChangedListener(new TextChangedListener<>(binding.telephone) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                listenerTelephoneEditText(s);
            }
        });

        binding.password.addTextChangedListener(new TextChangedListener<>(binding.password) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                standardListener(binding.passwordLayout, s);
            }
        });
        binding.passwordConfirm.addTextChangedListener(new TextChangedListener<>(binding.passwordConfirm) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                standardListener(binding.passwordConfirmLayout, s);
            }
        });

        binding.registerButton.setOnClickListener(this::registerButtonListener);
        binding.loginText.setOnClickListener(this::onClickListenerLoginText);

        subscribe();
    }

    private void subscribe() {
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show());
        viewModel.createLiveData.observe(getViewLifecycleOwner(), volunteer -> viewModel.login());
        viewModel.openLiveData.observe(getViewLifecycleOwner(), volunteer -> {
            SharedPreferences settings = requireView().getContext().getSharedPreferences(
                    SettingConstants.PREFS_FILE, Context.MODE_PRIVATE
            );
            settings.edit().putLong(SettingConstants.PREF_ID, volunteer.getId()).apply();
            settings.edit().putString(SettingConstants.PREF_ROLE, volunteer.getRole()).apply();

            if (volunteer.getCenterName() != null)
                settings.edit().putString(SettingConstants.PREF_CENTER_NAME, volunteer.getCenterName()).apply();

            startActivity(new Intent(getActivity(), MainActivity.class));
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @SuppressLint("DefaultLocale")
    private void registerButtonListener(View view) {
        errorFlag = false;

        if (String.valueOf(binding.name.getText()).isEmpty()) emptyErrorWidget(binding.nameLayout);
        if (String.valueOf(binding.surname.getText()).isEmpty())
            emptyErrorWidget(binding.surnameLayout);
        if (String.valueOf(binding.dateBirth.getText()).isEmpty())
            emptyErrorWidget(binding.dateBirthLayout);
        if (String.valueOf(binding.cityAutoCompleteTextView.getText()).isEmpty())
            emptyErrorWidget(binding.cityLayout);
        if (String.valueOf(binding.email.getText()).isEmpty())
            emptyErrorWidget(binding.emailLayout);
        if (String.valueOf(binding.telephone.getText()).isEmpty())
            emptyErrorWidget(binding.telephoneLayout);
        if (String.valueOf(binding.password.getText()).isEmpty())
            emptyErrorWidget(binding.passwordLayout);
        if (String.valueOf(binding.passwordConfirm.getText()).isEmpty())
            emptyErrorWidget(binding.passwordConfirmLayout);

        if (errorFlag) return;
        if (!String.valueOf(binding.password.getText()).equals(String.valueOf(binding.passwordConfirm.getText()))) {
            Snackbar.make(view, "Пароли не совпадают!", Snackbar.LENGTH_LONG).show();
            return;
        }

        viewModel.changeVolunteer(
                String.valueOf(binding.name.getText()),
                String.valueOf(binding.surname.getText()),
                String.valueOf(binding.telephone.getText()),
                String.valueOf(binding.email.getText()),
                String.valueOf(binding.password.getText()),
                LocalDate.of(year, month, day).toString(),
                String.valueOf(binding.cityAutoCompleteTextView.getText())
        );
        viewModel.createVolunteer();
    }

    public void emptyErrorWidget(TextInputLayout textInputLayout) {
        textInputLayout.setError("Обязательное поле");
        errorFlag = true;
    }

    public void standardListener(TextInputLayout textInputLayout, Editable s) {
        if (s.toString().isEmpty()) textInputLayout.setError("Обязательное поле");
        else textInputLayout.setErrorEnabled(false);
    }

    private void listenerEmailEditText(Editable s) {
        if (s.toString().isEmpty()) {
            binding.emailLayout.setError("Обязательное поле");
        } else if (!isEmailValid(s.toString())) {
            binding.emailLayout.setError("Неверный формат");
        } else {
            binding.emailLayout.setErrorEnabled(false);
        }
    }

    private void listenerTelephoneEditText(Editable s) {
        if (s.toString().isEmpty()) {
            binding.telephoneLayout.setError("Обязательное поле");
        } else if (!isTelephoneValid(s.toString())) {
            binding.telephoneLayout.setError("Неверный формат");
        } else {
            binding.telephoneLayout.setErrorEnabled(false);
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

    private void onClickListenerLoginText(View view) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, LoginFragment.class, null)
                .commit();
    }

    private void setMaskInTelephone() {
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(binding.telephone);
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isTelephoneValid(String telephone) {
        return Patterns.PHONE.matcher(telephone).matches();
    }
}