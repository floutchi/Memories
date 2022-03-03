package org.helmo.memories.view.activities;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.helmo.memories.R;
import org.helmo.memories.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng currentPos;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void confirmButtonOnClick(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("picked_point", currentPos);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(latLng -> {
            marker.remove();
            currentPos = new LatLng(latLng.latitude, latLng.longitude);
            marker = mMap.addMarker(new MarkerOptions().position(currentPos).title("Votre position"));
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        while(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, location -> {
            currentPos = new LatLng(location.getLatitude(), location.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(currentPos).title("Votre position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 16.0f));
        }, null);
    }
}