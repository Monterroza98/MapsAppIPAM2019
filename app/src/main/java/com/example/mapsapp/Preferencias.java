package com.example.mapsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

public class Preferencias extends Activity implements View.OnClickListener{

    Boolean centrar=false;
    Switch spCentrar;
    Boolean brujula=false;
    Switch spBrujula;
    Boolean zoom=false;
    Spinner sRadio, sColorL, sColorF;
    Switch spZoom;
    int radio, colorL, colorF;

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

        ArrayAdapter<String> adaptadorTamanios = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, tamanios);

        ArrayAdapter<String> adaptadorColores =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, colores);

        sRadio.setAdapter(adaptadorTamanios);
        sColorL.setAdapter(adaptadorColores);
        sColorF.setAdapter(adaptadorColores);

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
        colorL=asignarColor(sColorL.getSelectedItemPosition());
        colorF=asignarColor(sColorF.getSelectedItemPosition());
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

}
