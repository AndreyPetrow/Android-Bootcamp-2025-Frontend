package com.example.myapplication.api.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class VolunteerDTO {
    @Nullable
    @SerializedName("id")
    public long id;

    @Nullable
    @SerializedName("name")
    public String name;

    @Nullable
    @SerializedName("surname")
    public String surname;

    @Nullable
    @SerializedName("patronymic")
    public String patronymic;

    @Nullable
    @SerializedName("aboutMe")
    public String aboutMe;

    @Nullable
    @SerializedName("telephone")
    public String telephone;

    @Nullable
    @SerializedName("email")
    public String email;

    @Nullable
    @SerializedName("birthday")
    public String birthday;

    @Nullable
    @SerializedName("city")
    public String city;

    @Nullable
    @SerializedName("telegramLink")
    public String telegramLink;

    @Nullable
    @SerializedName("vkLink")
    public String vkLink;

    @Nullable
    @SerializedName("role")
    public String role;  // строка либо user, либо admin

    @Nullable
    @SerializedName("center")
    public Long center;  // id центра, в котором зарегистрирован

    @Nullable
    @SerializedName("centerName")
    public String centerName;

    @Nullable
    @SerializedName("centerImageUrl")
    public String centerImageUrl;

    @Nullable
    @SerializedName("profileImageUrl")
    public String profileImageUrl;

    @Nullable
    @SerializedName("medicalBook")
    public boolean medicalBook;

    @Nullable
    @SerializedName("driverLicense")
    public boolean driverLicense;

    public VolunteerDTO(long id, @Nullable String name, @Nullable String surname, @Nullable String patronymic, @Nullable String aboutMe, @Nullable String telephone, @Nullable String email, @Nullable String birthday, @Nullable String city, @Nullable String telegramLink, @Nullable String vkLink, @Nullable String role, @Nullable Long center, @Nullable String centerName, @Nullable String centerImageUrl, @Nullable String profileImageUrl, boolean medicalBook, boolean driverLicense) {
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
}
