package com.examle.faany.travels.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.examle.faany.travels.COMUNICATOR;
import com.examle.faany.travels.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment
{

    COMUNICATOR comm;

    //-----for map fragment-----
    MapView mMapView;
    private GoogleMap googleMap;


    //----for reverse geo coder----
    String address = "";
    String city = "";

    FragmentManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        manager = getFragmentManager();

        comm = new COMUNICATOR();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        //-------------------------MAP rata CODE start----------------------
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
        //-------------------------MAP rata CODE end----------------------


        //------------------------------MAP cutomization start----------------------------
        LatLng Pak = new LatLng(33.6667, 73.1667);       //coordinates of pakistan...by default it is shown...pakistan
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Pak, 4));      //start may camera idhr jaaye....level of zoom...in increasing order...

        googleMap.setMyLocationEnabled(true);     //to enable zoom button to get my location

        googleMap.addMarker(new MarkerOptions().title("Pakistan").snippet("The Best Country").position(Pak)); // to add marker
        //map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.lolo)).anchor(0.0f, 1.0f).position(sydney)); // Anchors the marker on the bottom left

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);      //map ki type....
        //------------------------------MAP cutomization end----------------------------


        //------start---------to get latitiude and longitude of curent location-------------------------------------

        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener()
        {

            @Override
            public void onMyLocationChange (Location location)
            {
                LatLng loc = new LatLng (location.getLatitude(), location.getLongitude());

                //---------------now , convert that lat and lang to address---------by using inverse geoCoder.....

                Geocoder geoCoder = new Geocoder(getActivity());
                List<Address> matches = null;
                try
                {
                    matches = geoCoder.getFromLocation(loc.latitude, loc.longitude, 1);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
                Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                //Address has the complete adress , now convert it into strings

                address = bestMatch.getAddressLine(0);
                city = bestMatch.getLocality();
                //String state = bestMatch.getAdminArea();
                //String country = bestMatch.getCountryName();
                //String knownName = bestMatch.getFeatureName();
                Log.d("fany", loc.latitude + " , " + loc.longitude + " , " + address + " , " + city);


                String completeAddress = address + " " + city;
                comm.MY_LOC = completeAddress;

            }
        };
        googleMap.setOnMyLocationChangeListener(myLocationChangeListener);

        //-------end--------to get latitiude and longitude of curent location-------------------------------------



        return v;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);

        ImageButton ib = (ImageButton) getView().findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener()
                              {
                                  public void onClick(View v)
                                  {
                                      FragmentTransaction transaction = manager.beginTransaction();
                                      MapFragment f1 = (MapFragment) manager.findFragmentByTag("M_F");      //to remove the MAP fragment...
                                      if (f1 != null)
                                      {
                                          transaction.remove(f1);
                                      }

                                      GeneralFragment GF = new GeneralFragment();
                                      transaction.add(R.id.myMainLayOut, GF, "G_F");        //add General Fragment which has ADRESS & TAbs Fragments in it...
                                      //transaction.addToBackStack(null);
                                      transaction.commit();


                                  }
                              }
        );
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
