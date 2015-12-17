package com.examle.faany.travels.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examle.faany.travels.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment
{

    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        //-------------------------rata CODE start----------------------
        mMapView = (MapView) v.findViewById(R.id.mapView);      //ye us Map ki Id hy , jo hum ny fragment_map layout may banaya hy...
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately
        try
        {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        googleMap = mMapView.getMap();
        //-------------------------rata CODE end----------------------


        LatLng Pak = new LatLng(33.6667, 73.1667);       //coordinates of pakistan...by default it is shown...pakistan
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Pak, 3));      //start may camera idhr jaaye....level of zoom...in increasing order...

        googleMap.setMyLocationEnabled(true);     //to enable zoom button to get my location

        googleMap.addMarker(new MarkerOptions().title("Pakistan").snippet("The Best Country").position(Pak)); // to add marker
        //map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.lolo)).anchor(0.0f, 1.0f).position(sydney)); // Anchors the marker on the bottom left

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);      //map ki type....

        //copy paste here...

        //----------------------------------------------------------

        return v;
    }






    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
