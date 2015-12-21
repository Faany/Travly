package com.examle.faany.travels.Fragments;

import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Toast;


import com.examle.faany.travels.COMUNICATOR;
import com.examle.faany.travels.R;
import com.examle.faany.travels.Utils;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by farha on 18-Dec-15.
 */
public class AddressFragment extends Fragment implements OnItemClickListener
{

    COMUNICATOR comm;
    Utils U;

    android.support.v4.app.FragmentManager manager;

    //-------------------start------for AutoComplete--------------
    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyAZIjFUay5Lu4KI5aG6YQ9e9xFHAiwsnt4";        //put ur own key...which has "that API"  enabled

    //-------------------end------for AutoComplete--------------



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_address , container , false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        comm = new COMUNICATOR();
        U = new Utils();

        manager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final AutoCompleteTextView autoCompView1 = (AutoCompleteTextView) getView().findViewById(R.id.autoCompleteTextView);
        final AutoCompleteTextView autoCompView2 = (AutoCompleteTextView) getView().findViewById(R.id.autoCompleteTextView1);

        autoCompView1.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.custom_listview_autocomplete));
        autoCompView1.setOnItemClickListener(this);

        autoCompView2.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.custom_listview_autocomplete));
        autoCompView2.setOnItemClickListener(this);


        autoCompView1.setText(comm.MY_LOC);
        autoCompView2.setHint(comm.MY_POINTER);
        Log.d("fany", "in Address frag loc and ppointer is  " + comm.MY_LOC + "," + comm.MY_POINTER + "." );

        //---------------------------------start-------------------------GEO coding---------------------


        ImageButton calculate = (ImageButton) getActivity().findViewById(R.id.imageButton2);
        calculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String Slocation = autoCompView1.getText().toString();
                String Elocation = autoCompView2.getText().toString();

                if  (Elocation.isEmpty()  ||  Slocation.isEmpty())
                {
                    Toast.makeText(getActivity(), "Plzz...enter Address", Toast.LENGTH_LONG).show();
                }
                else
                {


                    Toast.makeText(getActivity(), "WAIT...(calculating)", Toast.LENGTH_LONG).show();

                    Geocoder gc = new Geocoder(getActivity());     //make object

                    try {
                        //////////////////////////////
                        List<Address> list = gc.getFromLocationName(Slocation, 1);  //"1" shows the number of string returns....becuz us naam sy related kafi places ho skti hain

                        Address add = list.get(0);      //sirf phli place  hi loo

                        String locality = add.getAddressLine(0);        //locality k andr  map pr jo orignal place hy  , wo andr rakhi hy list.get(0);   sy ly kr

                        double lat = add.getLatitude();
                        double lng = add.getLongitude();

                        LatLng start = new LatLng(lat, lng);


                        Log.d("Fany", "......................" + locality);
                        ////////////////////////////////////
                        List<Address> Elist = gc.getFromLocationName(Elocation, 1);  //"1" shows the number of string returns....becuz us naam sy related kafi places ho skti hain

                        Address Eadd = Elist.get(0);      //sirf phli place  hi loo

                        String Elocality = Eadd.getAddressLine(0);       //locality k andr  map pr jo orignal place hy  , wo andr rakhi hy list.get(0);   sy ly kr


                        double Elat = Eadd.getLatitude();
                        double Elng = Eadd.getLongitude();

                        LatLng end = new LatLng(Elat, Elng);

                        Log.d("Fany", "......................" + Elocality);
                        ////////////////////////////////////

                        double dist = U.CalculationByDistance(start, end);
                        comm.DISTANCE = dist;

                        comm.MY_POINTER = Elocation;

                        //--------------------
                        FragmentTransaction transaction = manager.beginTransaction();

                        GeneralFragment GF = new GeneralFragment();
                        transaction.replace(R.id.myMainLayOut, GF, "G_F");        //add General Fragment which has ADRESS & TAbs Fragments in it...
                        //transaction.addToBackStack(null);
                        transaction.commit();

                        ///--------------------



                        ///-----
                        Log.d("Fany", "_________________________________________ distance is " + dist);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

        //---------------------------------end-------------------------GEO coding---------------------

    }








    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }


    public static ArrayList<String> autocomplete(String input)
    {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:pak");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }



    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable
    {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter()
        {
            Filter filter = new Filter()
            {
                @Override
                protected FilterResults performFiltering(CharSequence constraint)
                {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null)
                    {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results)
                {
                    if (results != null && results.count > 0)
                    {
                        notifyDataSetChanged();
                    } else
                    {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }



}
