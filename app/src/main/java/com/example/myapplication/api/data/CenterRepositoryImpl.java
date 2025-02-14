package com.example.myapplication.api.data;

import androidx.annotation.NonNull;

import com.example.myapplication.api.data.dto.CenterDTO;
import com.example.myapplication.api.data.dto.VolunteerDTO;
import com.example.myapplication.api.data.network.RetrofitFactory;
import com.example.myapplication.api.data.source.CenterApi;
import com.example.myapplication.api.data.source.EventApi;
import com.example.myapplication.api.data.utils.CallToConsumer;
import com.example.myapplication.api.domain.CenterRepository;
import com.example.myapplication.api.domain.entity.Status;
import com.example.myapplication.api.domain.entity.center.CenterEntity;
import com.example.myapplication.api.domain.entity.volunteer.ItemVolunteerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CenterRepositoryImpl implements CenterRepository {
    private static CenterRepositoryImpl INSTANCE;
    private final CenterApi centerApi = RetrofitFactory.getInstance().getCenterApi();

    private CenterRepositoryImpl() {
    }

    public static synchronized CenterRepositoryImpl getInstance() {
        if (INSTANCE == null) INSTANCE = new CenterRepositoryImpl();
        return INSTANCE;
    }

    @Override
    public void getAllCenters(@NonNull Consumer<Status<List<CenterEntity>>> callback) {
        centerApi.getAllCenters().enqueue(new CallToConsumer<>(
                callback,
                centerDTOS -> {
                    if (centerDTOS == null) return null;

                    ArrayList<CenterEntity> result = new ArrayList<>(centerDTOS.size());

                    for (CenterDTO centerDTO : centerDTOS) {
                        if (centerDTO == null) continue;

                        final long id = centerDTO.id;
                        final String name = centerDTO.name;
                        final String description = centerDTO.description;
                        final String address = centerDTO.address;
                        final String linkLogo = centerDTO.linkLogo;
                        final Double latitude = centerDTO.latitude;
                        final Double longitude = centerDTO.longitude;
                        final String vkLink = centerDTO.vkLink;
                        final String linkSite = centerDTO.linkSite;
                        final List<Long> volunteers = centerDTO.volunteers;  // Список id всех волонтеров, которые работают в центре.
                        final List<Long> events = centerDTO.events;  // Список id всех событий, которые проводит центр

                        if (name != null && description != null && address != null && vkLink != null && volunteers != null && events != null &&
                                linkLogo != null && latitude != null && longitude != null && linkSite != null) {

                            result.add(new CenterEntity(id, name, description, address,
                                    linkLogo, latitude, longitude, vkLink, linkSite, volunteers, events));
                        }
                    }
                    return result;
                }
        ));
    }
}
