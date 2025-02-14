package com.example.myapplication.ui.login.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.view.View;

import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.ui.login.register.RegisterFragment;
import com.example.myapplication.ui.main.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.core.SettingConstants;
import com.example.myapplication.utils.TextChangedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;


    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentLoginBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.email.addTextChangedListener(new TextChangedListener<>(binding.email) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                listenerEmailEditText( s);
            }
        });

        binding.password.addTextChangedListener(new TextChangedListener<>(binding.password) {
            @Override
            public void onTextChanged(TextInputEditText target, Editable s) {
                listenerPasswordEditText(s);
            }
        });

        binding.loginButton.setOnClickListener(this::onClickListenerLoginButton);
        binding.singupText.setOnClickListener(this::onClickListenerSingupText);

        subscribe();
    }

    private void subscribe() {
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            binding.loginButton.setEnabled(true);
            Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show();
        });
        viewModel.openLiveData.observe(getViewLifecycleOwner(), volunteer -> {
            binding.loginButton.setEnabled(true);

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

    private void listenerEmailEditText(Editable s) {
        if (s.toString().isEmpty()) {
            binding.emailLayout.setError("Обязательное поле");
        } else if (!isEmailValid(s.toString())) {
            binding.emailLayout.setError("Неверный формат");
        } else {
            binding.emailLayout.setErrorEnabled(false);
        }
    }

    private void listenerPasswordEditText(Editable s) {
        if (s.toString().isEmpty()) {
            binding.passwordLayout.setError("Обязательное поле");
        } else {
            binding.passwordLayout.setErrorEnabled(false);
        }
    }

    private void onClickListenerLoginButton(View view) {
        binding.loginButton.setEnabled(false);

        viewModel.changeEmail(String.valueOf(binding.email.getText()));
        viewModel.changePassword(String.valueOf(binding.password.getText()));
        viewModel.confirm();
    }

    private void onClickListenerSingupText(View view) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, RegisterFragment.class, null)
                .commit();
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}