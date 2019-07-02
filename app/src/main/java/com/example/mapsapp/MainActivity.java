package com.example.mapsapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    final String[] tiposmapa=
            new String[]{"Normal","Satelite","Hibrido","Terreno"};
    private Spinner cmbTiposMapa;

    private SharedPreferences preferencias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_singlechoice, tiposmapa);

        //borrar
        obtenerColores();

        cmbTiposMapa = (Spinner)findViewById(R.id.tipoMapa);
        cmbTiposMapa .setAdapter(adaptador);

        Button btnPreferencias=findViewById(R.id.btnConfiguracion);
        btnPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Preferencias.class);
                startActivity(intent);
            }
        });

        cmbTiposMapa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(18);
        LatLng ues = new LatLng(13.970546,-89.574738);
        mMap.addMarker(new MarkerOptions().position(ues).title("Universidad").draggable(true));
        CameraPosition moverues = new CameraPosition.Builder()
                .target(ues)
                .zoom(17)
                .tilt(45)
                .bearing(90)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(moverues));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MainActivity.this,"Coordenadas: \n"+
                        "LAtitud: " + latLng.latitude + "\n" +
                        "Longitud:" + latLng.longitude,Toast.LENGTH_LONG).show();;
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(MainActivity.this,"Coordenadas: \n"+
                        "LAtitud: " + latLng.latitude + "\n" +
                        "Longitud:" + latLng.longitude,Toast.LENGTH_LONG).show();
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(latLng);
                circleOptions.radius(200);
                circleOptions.strokeColor(Color.BLACK);
                circleOptions.fillColor(Color.BLUE);
                circleOptions.strokeWidth(1);
                mMap.addCircle(circleOptions);

            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(MainActivity.this,
                        "Coordenadas: \n"+
                                "LAtitud: " +
                                marker.getPosition().latitude + "\n" +
                                "Longitud:" +
                                marker.getPosition().longitude,Toast.LENGTH_LONG).show();
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(preferencias.getBoolean("zoom", true));
        mMap.getUiSettings().setCompassEnabled(preferencias.getBoolean("brujula", true));
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    public void obtenerColores(){
        int radio = preferencias.getInt("radio", 50);
        int colorL = preferencias.getInt("colorL", Color.RED);
        int colorF = preferencias.getInt("colorF", Color.RED);

        Log.d("STATE", radio+"");
    }
}
