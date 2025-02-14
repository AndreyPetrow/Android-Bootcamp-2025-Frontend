package com.example.myapplication.api.data;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.EventDTO;
import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.network.RetrofitFactory;
import com.example.myapplication.api.data.source.VolunteerApi;
import com.example.myapplication.api.data.utils.CallToConsumer;
import com.example.myapplication.api.domain.VolunteerRepository;
import com.example.myapplication.api.domain.entity.EventEntity;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;
import com.example.myapplication.api.domain.entity.volunteer.VolunteerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VolunteerRepositoryImpl implements VolunteerRepository {
    private static VolunteerRepositoryImpl INSTANCE;
    private final VolunteerApi volunteerApi = RetrofitFactory.getInstance().getVolunteerApi();

    private VolunteerRepositoryImpl() {
    }

    public static synchronized VolunteerRepositoryImpl getInstance() {
        if (INSTANCE == null) INSTANCE = new VolunteerRepositoryImpl();
        return INSTANCE;
    }

    @Override
    public void getAllVolunteers(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback) {
        volunteerApi.getAll().enqueue(new CallToConsumer<>(
                callback,
                volunteerDTOS -> {
                    if (volunteerDTOS == null) return null;

                    ArrayList<ItemVolunteerEntity> result = new ArrayList<>(volunteerDTOS.size());

                    for (VolunteerDTO volunteerDTO : volunteerDTOS) {
                        if (volunteerDTO == null) continue;

                        final long id_ = volunteerDTO.id;
                        final String name = volunteerDTO.name;
                        final String surname = volunteerDTO.surname;
                        final String patronymic = volunteerDTO.patronymic;
                        final String telephone = volunteerDTO.telephone;
                        final String email = volunteerDTO.email;
                        final String birthday = volunteerDTO.birthday;
                        final String city = volunteerDTO.city;
                        final String role = volunteerDTO.role;  // строка либо user, либо admin
                        final Long center = volunteerDTO.center;  // id центра, в котором зарегистрирован
                        final String centerName = volunteerDTO.centerName;
                        final String profileImageUrl = volunteerDTO.profileImageUrl;
                        final boolean medicalBook = volunteerDTO.medicalBook;
                        final boolean driverLicense = volunteerDTO.driverLicense;

                        if (name != null && surname != null && telephone != null &&
                                email != null && birthday != null && city != null && role != null) {

                            result.add(new ItemVolunteerEntity(id_, name, surname, patronymic,
                                    telephone, email, city, role,
                                    center, centerName, profileImageUrl, medicalBook, driverLicense));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void getVolunteerById(long id, @NonNull Consumer<Status<VolunteerEntity>> callback) {
        volunteerApi.getVolunteerById(id).enqueue(new CallToConsumer<>(
                callback,
                volunteerDTO -> {

                    final long id_ = volunteerDTO.id;
                    final String name = volunteerDTO.name;
                    final String surname = volunteerDTO.surname;
                    final String patronymic = volunteerDTO.patronymic;
                    final String aboutMe = volunteerDTO.aboutMe;
                    final String telephone = volunteerDTO.telephone;
                    final String email = volunteerDTO.email;
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
                            email != null && birthday != null && city != null && role != null) {

                        return new VolunteerEntity(id_, name, surname, patronymic, aboutMe,
                                telephone, email, birthday, city, telegramLink, vkLink, role,
                                center, centerName, centerImageUr, profileImageUrl, medicalBook, driverLicense);
                    } else return null;
                }
        ));
    }

    @Override
    public void getVolunteerByEmail(@NonNull String email, @NonNull Consumer<Status<VolunteerEntity>> callback) {

    }

    @Override
    public void getVolunteerByTelephone(@NonNull String telephone, @NonNull Consumer<Status<VolunteerEntity>> callback) {

    }

    @Override
    public void getFreeVolunteers(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback) {
        volunteerApi.getFreeVolunteers().enqueue(new CallToConsumer<>(
                callback,
                volunteerDTOS -> {
                    if (volunteerDTOS == null) return null;

                    ArrayList<ItemVolunteerEntity> result = new ArrayList<>(volunteerDTOS.size());

                    for (VolunteerDTO volunteerDTO : volunteerDTOS) {
                        if (volunteerDTO == null) continue;

                        final long id_ = volunteerDTO.id;
                        final String name = volunteerDTO.name;
                        final String surname = volunteerDTO.surname;
                        final String patronymic = volunteerDTO.patronymic;
                        final String telephone = volunteerDTO.telephone;
                        final String email = volunteerDTO.email;
                        final String birthday = volunteerDTO.birthday;
                        final String city = volunteerDTO.city;
                        final String role = volunteerDTO.role;  // строка либо user, либо admin
                        final Long center = volunteerDTO.center;  // id центра, в котором зарегистрирован
                        final String centerName = volunteerDTO.centerName;
                        final String profileImageUrl = volunteerDTO.profileImageUrl;
                        final boolean medicalBook = volunteerDTO.medicalBook;
                        final boolean driverLicense = volunteerDTO.driverLicense;

                        if (name != null && surname != null && telephone != null &&
                                email != null && birthday != null && city != null && role != null) {

                            result.add(new ItemVolunteerEntity(id_, name, surname, patronymic,
                                    telephone, email, city, role,
                                    center, centerName, profileImageUrl, medicalBook, driverLicense));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void getNotFreeVolunteers(@NonNull Consumer<Status<List<ItemVolunteerEntity>>> callback) {
        volunteerApi.getNotFreeVolunteers().enqueue(new CallToConsumer<>(
                callback,
                volunteerDTOS -> {
                    if (volunteerDTOS == null) return null;

                    ArrayList<ItemVolunteerEntity> result = new ArrayList<>(volunteerDTOS.size());

                    for (VolunteerDTO volunteerDTO : volunteerDTOS) {
                        if (volunteerDTO == null) continue;

                        final long id_ = volunteerDTO.id;
                        final String name = volunteerDTO.name;
                        final String surname = volunteerDTO.surname;
                        final String patronymic = volunteerDTO.patronymic;
                        final String telephone = volunteerDTO.telephone;
                        final String email = volunteerDTO.email;
                        final String birthday = volunteerDTO.birthday;
                        final String city = volunteerDTO.city;
                        final String role = volunteerDTO.role;  // строка либо user, либо admin
                        final Long center = volunteerDTO.center;  // id центра, в котором зарегистрирован
                        final String centerName = volunteerDTO.centerName;
                        final String profileImageUrl = volunteerDTO.profileImageUrl;
                        final boolean medicalBook = volunteerDTO.medicalBook;
                        final boolean driverLicense = volunteerDTO.driverLicense;

                        if (name != null && surname != null && telephone != null &&
                                email != null && birthday != null && city != null && role != null) {

                            result.add(new ItemVolunteerEntity(id_, name, surname, patronymic,
                                    telephone, email, city, role,
                                    center, centerName, profileImageUrl, medicalBook, driverLicense));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void updateVolunteer(long id, @NonNull VolunteerDTO volunteerDTO, @NonNull Consumer<Status<Void>> callback) {
        volunteerApi.updateVolunteer(id, volunteerDTO).enqueue(new CallToConsumer<>(callback, unused -> null));
    }

    @Override
    public void deleteVolunteer(long id, @NonNull Consumer<Status<VolunteerEntity>> callback) {

    }

    @Override
    public void patchVolunteer(long id, long centerId, @NonNull Consumer<Status<Void>> callback) {
        volunteerApi.patchVolunteer(id, centerId).enqueue(new CallToConsumer<>(callback, unused -> null));
    }
}
