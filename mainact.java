package com.example.toshiba.jsonmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import static com.example.toshiba.jsonmap.R.layout.activity_maps;

/**
 * @author saxman
 */
public class MainActivity extends FragmentActivity {
    private static final String LOG_TAG = "jsonmap";

    private static final String SERVICE_URL = "http://10.0.2.2:5000/";

    public GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_maps);
        addButtonClickListener();
        searchMarkerListener();

    }




    void addButtonClickListener()
    {
    Button btn =(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {
                map.clear();
                setUpMap();
            }
        });
    }



    void searchMarkerListener()
    {
        SearchView src=(SearchView)

        void changeMarkerPosition(String key, LatLng latLng) {
        markerHashMap.get(key).setPosition(latLng);

           }


    }




    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            map = mapFragment.getMap();
            if (map != null) {
                setUpMap();
               // new MarkerTask().execute();
            }
        }
    }

    private void setUpMap() {
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setScrollGesturesEnabled(true);
        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive cities", e);
                    return;
                }
            }
        }).start();
    }



    protected void retrieveAndAddCities() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {

                    createMarkersFromJson(json.toString());

                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

    HashMap<String, Marker> markerHashMap = new HashMap<>();






    void createMarkersFromJson(String json) throws JSONException {
// De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);



        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.

            JSONObject jsonObj = jsonArray.getJSONObject(i);

            markerHashMap.put("key" + i, (map.addMarker(new MarkerOptions()
                            .title(jsonObj.getString("network") + "\n" + jsonObj.getString("date"))
                            .snippet(jsonObj.getString("pollutant") + "=" + jsonObj.getString("numeric_val"))

                            .position(new LatLng(
                                    jsonObj.getDouble("x"),
                                    jsonObj.getDouble("y")))
                            .icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360))
                            ))));


            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoContents(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoWindow(Marker arg0) {

                    View v = getLayoutInflater().inflate(R.layout.customlayout, null);

                    TextView tTitle = (TextView) v.findViewById(R.id.title);

                    TextView tSnippet = (TextView) v.findViewById(R.id.snippet);

                    tTitle.setText(arg0.getTitle());

                    tSnippet.setText(arg0.getSnippet());

                    return v;

                }
            });
        }



    }



}


