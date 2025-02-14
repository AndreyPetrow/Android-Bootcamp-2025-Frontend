package com.example.myapplication.ui.main.map;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.api.domain.entity.center.CenterEntity;
import com.example.myapplication.utils.DownloadImage;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.function.Consumer;

public class MapService implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    private final Context context;
    private List<CenterEntity> centers;
    private Consumer<Long> listener;

    public MapService(Context context, List<CenterEntity> centers, Consumer<Long> listener1) {
        this.context = context;
        this.centers = centers;
        this.listener = listener1;
    }


    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // todo: Обработка нажатия на карту
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        CenterEntity centerEntity = null;

        for (CenterEntity center : centers) {
            if (center.getName().equals(marker.getTitle())) {
                centerEntity = center;
            }
        }

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.bottom_sheet_dialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

        ImageView imageView = dialog.getWindow().findViewById(R.id.centerLogo);
        TextView centerName = dialog.getWindow().findViewById(R.id.centerName);
        TextInputEditText centerDescription = dialog.getWindow().findViewById(R.id.centerDescription);
        TextInputEditText addressText = dialog.getWindow().findViewById(R.id.centerAddress);
        TextInputEditText centerContactsVKLayout = dialog.getWindow().findViewById(R.id.centerContactsVK);
        TextInputEditText centerContactsSite = dialog.getWindow().findViewById(R.id.centerContactsSite);
        MaterialButton button = dialog.getWindow().findViewById(R.id.joinButton);

        if (centerEntity == null) return false;

        new DownloadImage(imageView).execute(centerEntity.getLinkLogo());
        centerName.setText(centerEntity.getName());
        centerDescription.setText(centerEntity.getDescription());
        addressText.setText(centerEntity.getAddress());
        centerContactsVKLayout.setText(centerEntity.getVkLink());
        centerContactsSite.setText(centerEntity.getLinkSite());
        CenterEntity finalCenterEntity = centerEntity;
        button.setOnClickListener(view -> listener.accept(finalCenterEntity.getId()));

        return false;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMarkerClickListener(this);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.32, 48.39), 15.0f));

        for (CenterEntity center : centers) {
            googleMap.addMarker(new MarkerOptions().title(center.getName()).position(new LatLng(center.getLatitude(), center.getLongitude())));
        }
    }
}
