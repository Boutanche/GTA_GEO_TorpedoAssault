package com.example.gta_geo_torpedoassault.activities;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.utils.PermissionUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Classe qui permet de localiser l'utilisateur sur une carte.
 * @see com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
 * @see com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
 * @see com.google.android.gms.maps.OnMapReadyCallback
 * @see androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
 * @see com.google.android.gms.maps.SupportMapFragment
 * @see com.google.android.gms.maps.GoogleMap
 * @see com.google.android.gms.maps.model.Marker
 */
public class LocateActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{

    /**
     * Code de requête pour l'accès à la localisation.
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Carte Google.
     */
    private GoogleMap mMap;

    /**
     * Vrai si la permission a été refusée.
     */
    private boolean permissionDenied = false;

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton "Ma position".
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this, "Cliquez sur le bouton \"Ma position\" pour afficher votre position.", Toast.LENGTH_LONG).show();
    }

    /**
     * Méthode de test de la permission d'accès à la localisation.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15.0f);
        mMap.setMaxZoomPreference(20.0f);

        // Activer le bouton "Ma position".
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    /**
     * Active la couche "Ma position" si la permission a été accordée.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // Vérifier si la permission a été accordée.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }

        // Demander la permission.
        PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton "Ma position".
     */
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, getString(R.string.mylocation_button_clicked), Toast.LENGTH_SHORT).show();
        // Renvoie false pour que le comportement par défaut se produise
        // (la caméra se place sur la position de l'utilisateur).
        return false;
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur sa position.
     */
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, getString(R.string.current_location) + location, Toast.LENGTH_LONG).show();
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton "Ma position".
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Activer la couche "Ma position" si la permission a été accordée.
            enableMyLocation();
        } else {
            // Permission refusée.
            // Afficher un message d'erreur.
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission refusée.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Affiche un message d'erreur si la permission a été refusée.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
