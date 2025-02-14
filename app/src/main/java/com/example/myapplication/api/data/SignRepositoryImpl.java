package com.example.myapplication.api.data;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.VolunteerRegisterDTO;
import com.example.myapplication.api.data.network.RetrofitFactory;
import com.example.myapplication.api.data.source.SignApi;
import com.example.myapplication.api.data.utils.CallToConsumer;
import com.example.myapplication.api.domain.SignRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.function.Consumer;

public class SignRepositoryImpl implements SignRepository {
    private static SignRepositoryImpl INSTANCE;
    private final SignApi signApi = RetrofitFactory.getInstance().getSignApi();
    private final CredentialsDataSource credentialsDataSource = CredentialsDataSource.getInstance();

    private SignRepositoryImpl() {
    }

    public static synchronized SignRepositoryImpl getInstance() {
        if (INSTANCE == null) INSTANCE = new SignRepositoryImpl();
        return INSTANCE;
    }

    @Override
    public void createVolunteer(@NonNull VolunteerRegisterDTO volunteerRegisterDTO, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        signApi.register(volunteerRegisterDTO).enqueue(new CallToConsumer<>(
                callback,
                volunteerDTO -> {
                    if (volunteerDTO == null) return null;


                    final long id_ = volunteerDTO.id;
                    final String name = volunteerDTO.name;
                    final String surname = volunteerDTO.surname;
                    final String patronymic = volunteerDTO.patronymic;
                    final String aboutMe = volunteerDTO.aboutMe;
                    final String telephone = volunteerDTO.telephone;
                    final String email_ = volunteerDTO.email;
                    final String birthday = volunteerDTO.birthday;
                    final String city = volunteerDTO.city;
                    final String telegramLink = volunteerDTO.telegramLink;
                    final String vkLink = volunteerDTO.vkLink;
                    final String role = volunteerDTO.role;  // строка либо user, либо admin
                    final Long center = volunteerDTO.center;  // id центра, в котором зарегистрирован
                    final String centerName = volunteerDTO.centerName;
                    final String centerImageUr = volunteerDTO.centerImageUrl;
                    final String profileImageUrl = volunteerDTO.profileImageUrl;
                    final boolean medicalBook = volunteerDTO.medicalBook;
                    final boolean driverLicense = volunteerDTO.driverLicense;

                    if (name != null && surname != null && telephone != null &&
                            email_ != null && birthday != null && city != null && role != null) {

                        return new VolunteerEntity(id_, name, surname, patronymic, aboutMe,
                                telephone, email_, birthday, city, telegramLink, vkLink, role,
                                center, centerName, centerImageUr, profileImageUrl, medicalBook, driverLicense);
                    } else return null;
                })
        );
    }

    @Override
    public void login(@NonNull String email, @NonNull String password, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        credentialsDataSource.updateLogin(email, password);
        signApi.login().enqueue(new CallToConsumer<>(callback, volunteerDTO -> {
            if (volunteerDTO == null) return null;

            final long id_ = volunteerDTO.id;
            final String name = volunteerDTO.name;
            final String surname = volunteerDTO.surname;
            final String patronymic = volunteerDTO.patronymic;
            final String aboutMe = volunteerDTO.aboutMe;
            final String telephone = volunteerDTO.telephone;
            final String email_ = volunteerDTO.email;
            final String birthday = volunteerDTO.birthday;
            final String city = volunteerDTO.city;
            final String telegramLink = volunteerDTO.telegramLink;
            final String vkLink = volunteerDTO.vkLink;
            final String role = volunteerDTO.role;  // строка либо user, либо admin
            final Long center = volunteerDTO.center;  // id центра, в котором зарегистрирован
            final String centerName = volunteerDTO.centerName;
            final String centerImageUr = volunteerDTO.centerImageUrl;
            final String profileImageUrl = volunteerDTO.profileImageUrl;
            final boolean medicalBook = volunteerDTO.medicalBook;
            final boolean driverLicense = volunteerDTO.driverLicense;

            if (name != null && surname != null && telephone != null &&
                    email_ != null && birthday != null && city != null && role != null) {

                return new VolunteerEntity(id_, name, surname, patronymic, aboutMe,
                        telephone, email_, birthday, city, telegramLink, vkLink, role,
                        center, centerName, centerImageUr, profileImageUrl, medicalBook, driverLicense);
            } else return null;
        }));
    }

    @Override
    public void logout() {
        credentialsDataSource.logout();
    }
}
