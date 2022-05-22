package com.example.tawfekh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalFragment extends Fragment implements OnMapReadyCallback {
        private GoogleMap nMap;
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            nMap = googleMap;
            LatLng principal = new LatLng(14.661582317936375, -17.434769503367733);
            nMap.addMarker(new MarkerOptions().position(principal).title("Hopital principal").snippet("contact : 338395050"));
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(principal,14));
            LatLng dantec = new LatLng(14.657580584867864, -17.43564668987485);
            nMap.addMarker(new MarkerOptions().position(dantec).title("Hopital aristide le dantec").snippet("contact : 338235896"));
            LatLng fann = new LatLng(14.694919225716129, -17.4642210187099);
            nMap.addMarker(new MarkerOptions().position(fann).title("Centre hosppitalier universitaire de FANN").snippet("contact : 338691818"));
            LatLng clemar = new LatLng(14.737046831798988, -17.43851836103795);
            nMap.addMarker(new MarkerOptions().position(clemar).title("Clinique des maristes").snippet("contact : 338320989 site web : cliniquelesmaristes.com"));
            LatLng gaspar = new LatLng(14.70131511933072, -17.458344243846014);
            nMap.addMarker(new MarkerOptions().position(gaspar).title("Centre de santé Gaspard KAMARA").snippet("contact : 338641242"));
            nMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    if(marker.getTitle().equals("principal")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338395050"));
                    }
                    if(marker.getTitle().equals("dantec")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338235896"));
                    }
                    if(marker.getTitle().equals("fann")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338691818"));
                    }
                    if(marker.getTitle().equals("clemar")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338320989"));
                    }
                    return false;
                }
            });
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }
}