package com.example.myapplication.api.domain.entity.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class VolunteerEntity {
    @NonNull private final long id;
    @NonNull private final String name;
    @NonNull private final String surname;
    @Nullable private final String patronymic;
    @Nullable private final String aboutMe;
    @NonNull private final String telephone;
    @NonNull private final String email;
    @NonNull private final String birthday;
    @NonNull private final String city;
    @Nullable private final String telegramLink;
    @Nullable private final String vkLink;

    @NonNull private final String role;  // строка либо user, либо admin

    @Nullable private final Long center;  // id центра, в котором зарегистрирован
    @Nullable private final String centerName;
    @Nullable private final String centerImageUrl;

    @Nullable private final String profileImageUrl;
    @Nullable private final boolean medicalBook;
    @Nullable private final boolean driverLicense;

    public VolunteerEntity(long id, @NonNull String name, @NonNull String surname, @Nullable String patronymic, @Nullable String aboutMe, @NonNull String telephone, @NonNull String email, @NonNull String birthday, @NonNull String city, @Nullable String telegramLink, @Nullable String vkLink, @NonNull String role, @Nullable Long center, @Nullable String centerName, @Nullable String centerImageUrl, @Nullable String profileImageUrl, boolean medicalBook, boolean driverLicense) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.aboutMe = aboutMe;
        this.telephone = telephone;
        this.email = email;
        this.birthday = birthday;
        this.city = city;
        this.telegramLink = telegramLink;
        this.vkLink = vkLink;
        this.role = role;
        this.center = center;
        this.centerName = centerName;
        this.centerImageUrl = centerImageUrl;
        this.profileImageUrl = profileImageUrl;
        this.medicalBook = medicalBook;
        this.driverLicense = driverLicense;
    }

    @NonNull
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

    @Nullable
    public String getAboutMe() {
        return aboutMe;
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
    public String getBirthday() {
        return birthday;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    @Nullable
    public String getTelegramLink() {
        return telegramLink;
    }

    @Nullable
    public String getVkLink() {
        return vkLink;
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
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public boolean isMedicalBook() {
        return medicalBook;
    }

    public boolean isDriverLicense() {
        return driverLicense;
    }

    @Nullable
    public String getCenterName() {
        return centerName;
    }

    @Nullable
    public String getCenterImageUrl() {
        return centerImageUrl;
    }
}
