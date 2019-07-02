package com.example.mapsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ViewSwitcher;

public class Preferencias extends Activity implements View.OnClickListener{

    Boolean centrar  = false;
    Switch spCentrar;
    Boolean brujula = false;
    Switch spBrujula;
    Boolean zoom = false;
    Spinner sRadio, sColorL, sColorF;
    Switch spZoom;
    int radio, colorL, colorF, idRadio, idColorL, idColorF;
    SharedPreferences sharedPreferences;

    Button btnConf, btnGuardar;

    final String [] tamanios= new String[]{"50", "100", "150", "200", "250", "300"};
    final String [] colores= new String[]{"Rojo", "Azul", "Verde"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        btnConf = findViewById(R.id.btnConfiguracion);
        btnGuardar= findViewById(R.id.btnGuardar);
        spCentrar= findViewById(R.id.switchCentrar);
        spBrujula= findViewById(R.id.switchBrujula);
        spZoom= findViewById(R.id.switchZoom);
        sRadio= findViewById(R.id.columTamanio);
        sColorF= findViewById(R.id.columFondo);
        sColorL= findViewById(R.id.columLinea);

        //llenar con el sharedPreference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ArrayAdapter<String> adaptadorTamanios = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, tamanios);

        ArrayAdapter<String> adaptadorColores =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, colores);

        sRadio.setAdapter(adaptadorTamanios);
        sColorL.setAdapter(adaptadorColores);
        sColorF.setAdapter(adaptadorColores);

        cargarPreferencias();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Context context= getApplicationContext();
                Intent main = new Intent(Preferencias.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //veriicar el checd
                if(spBrujula.isChecked()){
                    brujula=true;
                }
                if(spCentrar.isChecked()){
                    centrar=true;
                }
                if(spZoom.isChecked()){
                    zoom=true;
                }
                radio=Integer.parseInt(sRadio.getSelectedItem().toString());
                idRadio = sRadio.getSelectedItemPosition();
                colorL=asignarColor(sColorL.getSelectedItemPosition());
                idColorL = sColorL.getSelectedItemPosition();
                colorF=asignarColor(sColorF.getSelectedItemPosition());
                idColorF = sColorF.getSelectedItemPosition();
                guardarPreferencias(brujula,centrar,zoom,radio,colorL, colorF, idRadio, idColorL , idColorF);
                startActivity(main);
            }
        });

    }

    @Override
    public void onClick(View v){
        Context context= getApplicationContext();
        Intent main = new Intent(Preferencias.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
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

    public void guardarPreferencias(boolean bruj, boolean centro, boolean zoo, int rango, int color, int  color2, int idradio, int idcolorL , int idcolorF){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("brujula",bruj);
        editor.putBoolean("centrar",centro);
        editor.putBoolean("zoom",zoo);
        editor.putInt("radio",rango);
        editor.putInt("colorL",color);
        editor.putInt("colorF",color2);
        editor.putInt("idRadio",idradio);
        editor.putInt("idColorL",idcolorL);
        editor.putInt("idColorF",idcolorF);
        editor.commit();
    }

    public void cargarPreferencias (){
        boolean central = sharedPreferences.getBoolean("centrar", true);
        boolean bruj = sharedPreferences.getBoolean("brujula", true);
        boolean zoo = sharedPreferences.getBoolean("zoom", true);
        int rango = sharedPreferences.getInt("idRadio", 0);
        int color = sharedPreferences.getInt("idColorL",0);
        int color2 = sharedPreferences.getInt("idColorF",0);
        spCentrar.setChecked(central);
        spBrujula.setChecked(bruj);
        spZoom.setChecked(zoo);
        sRadio.setSelection(rango);
        sColorL.setSelection(color);
        sColorF.setSelection(color2);
    }

}
