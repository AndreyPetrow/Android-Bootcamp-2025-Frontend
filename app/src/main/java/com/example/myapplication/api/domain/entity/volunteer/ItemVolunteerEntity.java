package com.example.myapplication.api.domain.entity.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ItemVolunteerEntity {

    @NonNull private final long id;
    @NonNull private final String name;
    @NonNull private final String surname;
    @Nullable private final String patronymic;
    @NonNull private final String telephone;
    @NonNull private final String email;

    @NonNull private final String city;

    @NonNull private final String role;  // строка либо user, либо admin
    @Nullable private final Long center;  // id центра, в котором зарегистрирован
    @Nullable private final String centerName;

    @Nullable private final String profileImageUrl;
    @NonNull private final boolean medicalBook;
    @NonNull private final boolean driverLicense;

    public ItemVolunteerEntity(long id, @NonNull String name, @NonNull String surname, @Nullable String patronymic, @NonNull String telephone, @NonNull String email, @NonNull String city, @NonNull String role, @Nullable Long center, @Nullable String centerName, @Nullable String profileImageUrl, boolean medicalBook, boolean driverLicense) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.telephone = telephone;
        this.email = email;
        this.city = city;
        this.role = role;
        this.center = center;
        this.centerName = centerName;
        this.profileImageUrl = profileImageUrl;
        this.medicalBook = medicalBook;
        this.driverLicense = driverLicense;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getSurname() {
        return surname;
    }

    @Nullable
    public String getPatronymic() {
        return patronymic;
    }

    @NonNull
    public String getTelephone() {
        return telephone;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    @NonNull
    public String getRole() {
        return role;
    }

    @Nullable
    public Long getCenter() {
        return center;
    }

    @Nullable
    public String getCenterName() {
        return centerName;
    }

    @Nullable
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public boolean isMedicalBook() {
        return medicalBook;
    }

    public boolean isDriverLicense() {
        return driverLicense;
    }
}
