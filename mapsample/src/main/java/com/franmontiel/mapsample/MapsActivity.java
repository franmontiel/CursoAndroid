package com.franmontiel.mapsample;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static final LatLng INGENIA_LATLNG = new LatLng(36.7396609, -4.5567992);
    private static final List<LatLng> MARKERS_LATLNG = Arrays.asList(
            new LatLng[]{
                    new LatLng(36.8, -4.58),
                    new LatLng(36.9, -4.44),
                    new LatLng(37.5, -4.14),
                    new LatLng(36.5, -4.04),
                    new LatLng(36.6, -4.34),
            });

    private List<Marker> addedMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initMap();
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (map != null) {
            switch (item.getItemId()) {
                case R.id.addMarkers:
                    addMarkers();
                    return true;
                case R.id.goToIngenia:
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(INGENIA_LATLNG, 15));
                    return true;
                case R.id.satellite:
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMarkers() {
        if (addedMarkers.isEmpty()) {

            // Añadir markers
            int i = 0;
            for (LatLng latLng : MARKERS_LATLNG) {
                addMarker(latLng, "Marker " + i);
                i++;
            }

            // Ajustar camara para poder mostrar todos los markers añadidos
            LatLngBounds.Builder builder = LatLngBounds.builder();
            for (LatLng latLng : MARKERS_LATLNG)
                builder.include(latLng);

            map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));

        } else {
            Iterator<Marker> it = addedMarkers.iterator();
            while (it.hasNext()) {
                Marker marker = it.next();
                marker.remove();
                it.remove();
            }
        }
    }

    private void addMarker(LatLng latLng, String title) {
        addedMarkers.add(map.addMarker(new MarkerOptions().position(latLng).title(title)));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        final Marker ingenia = map.addMarker(new MarkerOptions().position(INGENIA_LATLNG).title("Ingenía"));

        /* LISTENERS */
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(ingenia)) {
                    Toast.makeText(getApplicationContext(), "Estamos aquí", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentNavigate = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr="
                                    + marker.getPosition().latitude + "," + marker.getPosition().longitude));
                    intentNavigate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        startActivity(intentNavigate);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "No hay app disponible", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            }
        });

        /* LOCALIZACIÓN EN EL MAPA */
        if (PermissionHelper.hasAllPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION))
            map.setMyLocationEnabled(true);

        /* CONTROLES */
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }


    // Permissions
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onStart() {
        super.onStart();
        if (!PermissionHelper.hasAllPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionHelper.requestPermissions(this, REQUEST_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            onLocationPermissionGranted();
        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void onLocationPermissionGranted() {
        if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (PermissionHelper.areAllRequestedPermissionsGranted(grantResults)) {
                onLocationPermissionGranted();
            } else {
                if (!PermissionHelper.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Se ha denegado el permiso y se ha marcado "No preguntar más"
                }
            }
        }
    }
}
