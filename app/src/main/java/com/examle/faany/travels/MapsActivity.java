//#cccc00  #ffffcc




package com.examle.faany.travels;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback , AdapterView.OnItemClickListener
{

    //----------------------------------for reverse geo coder---------------------------------------
    String address = "";
    String city = "";
    //----------------------------------------------------------------------------------------------

    //--------------------------------------for drawer----------------------------------------------
    private MyAdapter myAdapter;
    //private String[] planets;

    private DrawerLayout drawerLayout;
    private ListView listView1;
    private ActionBarDrawerToggle drawerListener;       //ye btata k kab drawer open howa or kab close howa...
    //----------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        //--------------------------------------------map starts--------------------------------------------------

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);   // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment.getMapAsync(this);  //to place a map in our APP


        ImageButton b = (ImageButton) findViewById(R.id.imageButton);
        b.setOnClickListener(new View.OnClickListener()
             {
                 public void onClick(View v)
                 {
                        Toast.makeText(MapsActivity.this , "comming soon..."  , Toast.LENGTH_SHORT).show();
                 }
             }
        );


        //--------------------------------------------map end--------------------------------------------------


        //---------------------------------------------drawer Starts-------------------------------------------------



        //planets = getResources().getStringArray(R.array.planets);       //getting data from strings...

        listView1 = (ListView)findViewById(R.id.drawerList1);       //for listview...
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);      //for drawer...
        drawerListener = new ActionBarDrawerToggle(this  ,  drawerLayout  , R.string.drawer_open  ,  R.string.drawer_close )      //context....object of DrawerLayout....icon or image use to display on the button....string indicatiing drawer is open.....string indicatiing drawer is close
        {

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                //nothing to do....
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("Travly");      //display it on item bar...
            }
        };
        drawerLayout.setDrawerListener(drawerListener);     //it will tell k drawerLayout k sath DrawerListener ko joor day , drawerlaout k open close krny sy yay yay kaam ho
        getSupportActionBar().setHomeButtonEnabled(true);       //(kisi bhi type k)Button ko hum Action bar may rakhn chaa rhy hain...
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //back Button show kr do...


        //------FANY----------------custom adapter bnaya ta k marzi ka text size or image rakh skhy...but sab k liye same image ho gi...is liye MyAdapter bnaya ta k hr aik k liye  alag Icon bna sky...
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this , R.layout.custom_list_view  , R.id.textView , planets);
        //listView1.setAdapter(adapter);
        //----------------------just show the deaflut TextView with deaflut black color---
        //listView1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, planets));     //context.....how a single row will appear...data source..
        //----------------------Drawer ki hr aik Entry k liye  alag Icon bna sky------
        myAdapter = new MyAdapter(this);
        listView1.setAdapter(myAdapter);
        //-----------------------------------------------------------------------

        listView1.setOnItemClickListener(this);     //now handle the button click of drawer...

        //---------------------------------------------drawer end-------------------------------------------------

    }




    //....to show the DRAWER ICON(horizontal lines) on ActionBAR......jab ACTIVITY ban chuki ho , us k bad ye Function call hota hy...normally it is not mentioned in LIFEcycle
    @Override
    public void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();     //ye Action bar pr bton(horizontal lines) lgaa dy ga...asal may jab Activity ban jaye gi na , ye BAr ko  Synchronize kry ga oor us pr button lgaa dy da
    }


    //hum chaty k Bar k button click pr Drawer open ho...
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerListener.onOptionsItemSelected(item))     //by default , ye MENU pr kaam krta....let it design to work with DRAWER
        {
            return true;
        }
        return super.onOptionsItemSelected(item);       //else meny wala hi clickj chaly....jo nhi bnaya
    }


    //for landsacpe & portarait orientation......just copy paste it
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast.makeText(this, "...comming soon...", Toast.LENGTH_SHORT).show();
        selectItem(position);       //hum chatay k jo item select krien , wo hi TITILE BAR pr dispaly ho...
    }


    //FUNCTION of...jo item select krien , wo hi TITILE BAR pr dispaly ho...
    public void selectItem(int position)
    {
        listView1.setItemChecked(position, true);   //us item ko lock kr liya...I Dont know y ???
        //getSupportActionBar().setTitle(planets[position]);      //display that item on BAR
    }








    @Override
    public void onMapReady(GoogleMap map)
    {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */

        LatLng Pak = new LatLng(33.6667, 73.1667);       //coordinates of pakistan...by default it is shown...pakistan
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Pak, 3));      //start may camera idhr jaaye....level of zoom...in increasing order...

        map.setMyLocationEnabled(true);     //to enable zoom button to get my location

        map.addMarker(new MarkerOptions().title("Pakistan").snippet("The Best Country").position(Pak)); // to add marker
        //map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.lolo)).anchor(0.0f, 1.0f).position(sydney)); // Anchors the marker on the bottom left

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);      //map ki type

    }
}














//MyAdapter bnaya ta k Drawer ki hr aik Entry k liye  alag Icon bna sky...agr ye muskhl lag rha to upar wala sirf custom adapter (FANY) use kr ly
class MyAdapter extends BaseAdapter
{

    private Context inflaterContext;        //neechay use kiya hy for @@@@@@
    String [] drawerLabels ;
    int [] drawableImages = {R.drawable.home , R.drawable.info , R.drawable.mail , R.drawable.star};

    public MyAdapter (Context context)      //default constructr....
    {
        inflaterContext = context;
        drawerLabels = context.getResources().getStringArray(R.array.drawer_labels);
    }

    @Override
    public int getCount()       //drawerLabels ki length show kry ga...
    {
        return drawerLabels.length;
    }

    @Override
    public Object getItem(int position)     //return particular text at given postion...
    {
        return drawerLabels[position];
    }

    @Override
    public long getItemId(int position)     //is ki zarorat nhi...
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)       //...postion of that row (jis ki display hony ki bari aayi).......Well, it is the single child element of the ListView that android is going to use to inflate your custom view layout (ya, the one you defined in xml) means k aik single row , jo display hony ja rhi hay......... parent ka view kiya hy Listview! ya gridView     //call each time , when single row is drwan...
    {

        View customListView = null;
        if (convertView == null)        //means k phly null tha , oor hum pahli dafa view bnaa rhy hain...
        {
            LayoutInflater inflater = (LayoutInflater) inflaterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customListView = inflater.inflate(R.layout.custom_list_view , parent , false);        //.....kis layout.xml   pr ye sab apply kr  rhy ho....Root is set to parent....(always put false) i dont know Y ?...
        }
        else
        {
            customListView = convertView;       //agr pahli dafa nhi hy to , purana view (convertView) hi lag jaye
        }

        //ab view ban chuka hy...ab bs textView or Image View hi set kr...

        TextView title = (TextView) customListView.findViewById(R.id.textView);
        ImageView image = (ImageView) customListView.findViewById(R.id.imageView);

        title.setText(drawerLabels[position]);
        image.setImageResource(drawableImages[position]);

        return customListView;
    }
}


