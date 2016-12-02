package com.franmontiel.locationsample;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private TextView locationLog;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationLog = (TextView) findViewById(R.id.locationLog);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        startLocationUpdates();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API)
                .build();

    }

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

    private void onLocationPermissionGranted() {
        googleApiClient.connect();
    }

    @SuppressWarnings({"MissingPermission"})
    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, createLocationRequest(), new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        locationLog.setText("Location: " + location.getLatitude() + ", " + location.getLongitude() + "\n");
                    }
                });
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    protected void onStop() {
        if (googleApiClient.isConnected() || googleApiClient.isConnecting())
            googleApiClient.disconnect();
        super.onStop();
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
