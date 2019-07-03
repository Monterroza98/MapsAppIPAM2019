package com.example.mapsapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.SimpleCursorAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    SQLControlador dbconeccion;

    final String[] tiposmapa=
            new String[]{"Normal","Satelite","Hibrido","Terreno"};
    private Spinner cmbTiposMapa;

    private SharedPreferences preferencias;

    private List<Circulo> listaC=new ArrayList<>();
    private  List<Marcador> listaM=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_singlechoice, tiposmapa);


        //borrar
        //obtenerColores();


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
        llenarCirculos();
        llenarMarcadores();
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
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador"));
                dbconeccion.insertarDatosMarcador("Marcador", latLng.longitude, latLng.latitude);
                Log.d("s","Marcador\nnombre= marcador"+"\nLat= "+latLng.latitude+
                        "\nLog= "+latLng.longitude);
                Toast.makeText(MainActivity.this,"Coordenadas: \n"+
                        "Latitud: " + latLng.latitude + "\n" +
                        "Longitud:" + latLng.longitude,Toast.LENGTH_LONG).show();;

            }
        });

        final int radio = preferencias.getInt("radio", 50);
        final int colorL = preferencias.getInt("colorL", Color.RED);
        final int colorF = preferencias.getInt("colorF", Color.RED);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(MainActivity.this,"Coordenadas: \n"+
                        "Latitud: " + latLng.latitude + "\n" +
                        "Longitud:" + latLng.longitude,Toast.LENGTH_LONG).show();
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(latLng);
                circleOptions.radius(radio);
                circleOptions.strokeColor(colorL);
                circleOptions.fillColor(colorF);
                circleOptions.strokeWidth(5);
                mMap.addCircle(circleOptions);
                dbconeccion.insertarDatosCirculo(radio, colorL
                        , colorF, latLng.longitude
                        , latLng.latitude);
                Log.d("State", "se ha insertado\nradio "+radio
                +"\ncolorl "+colorL+ "\ncolorf "+ colorF+ "\nlong "+latLng.longitude
                +"\nlat "+latLng.latitude);
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
                                "Latitud: " +
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

    public void llenarCirculos(){
        Cursor cursor = dbconeccion.leerDatosCirculo();

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int radio= cursor.getInt(1);
                int colorL = Integer.parseInt(cursor.getString(2));
                int colorF = Integer.parseInt(cursor.getString(3));
                double longitud = cursor.getDouble(4);
                double latitud = cursor.getDouble(5);
                CircleOptions circleOption= new CircleOptions();
                LatLng l = new LatLng(latitud, longitud);
                Log.d("s", "circulo\nradio= "+radio+"\ncolorl= "+colorL+"\ncolorf= "+colorF+
                        "\nlatitud= "+latitud+"\nlongitud= "+longitud);
                LatLng ues = new LatLng(13.970546,-89.574738);
                circleOption.center(l);
                circleOption.radius(radio);
                circleOption.strokeColor(asignarColor(colorL));
                circleOption.fillColor(asignarColor(colorF));
                circleOption.strokeWidth(5);
                mMap.addCircle(circleOption);
            } while(cursor.moveToNext());
        }else{
            Log.d("Status", "vacio");
        }
    }

    public void llenarMarcadores(){
        Cursor cursor = dbconeccion.leerDatosMarcador();

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre= cursor.getString(1);
                double longitud = cursor.getDouble(2);
                double latitud = cursor.getDouble(3);
                LatLng l= new LatLng(latitud, longitud);
                mMap.addMarker(new MarkerOptions().position(l).title("Marcador").draggable(true));
                Log.d("s", "Marcador\ntitulo= "+nombre+"\nLong= "+longitud+"\nLat= "+latitud);
            } while(cursor.moveToNext());
        }else{
            Log.d("Status", "vacio");
        }
    }

    public int asignarColor(int color){
        int colorS=0;
        switch (color){
            case 0:
                colorS= Color.RED;
                break;
            case 1:
                colorS= Color.BLUE;
                break;
            case 2:
                colorS= Color.GREEN;
                break;
        }
        return colorS;
    }
}
