package com.example.inclass12;

import androidx.fragment.app.FragmentActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<mapPoint> list = new ArrayList<mapPoint>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int padding = 40;
        String json = loadJSONFromAsset(MapsActivity.this);
//        Log.d("demo",json);
        JSONObject obj = null;
        JSONArray points = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            points = obj.getJSONArray("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i =0;i<points.length();i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = points.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mapPoint locate = new mapPoint();
            try {
                locate.lat = jsonObject.getString("latitude");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                locate.longi = jsonObject.getString("longitude");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(locate);
        }

        Log.d("demo",list.toString());

        PolylineOptions polylineOptions = new PolylineOptions();
        for(int i=0;i<list.size();i++){
            double lat = Double.parseDouble(list.get(i).getLat());
            double longi = Double.parseDouble(list.get(i).getLongi());
            polylineOptions.add(new LatLng(lat,longi));
            mMap.addPolyline(polylineOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,longi)));
            mMap.setMinZoomPreference(10f);
        }
    }
//
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("trip.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }
}
