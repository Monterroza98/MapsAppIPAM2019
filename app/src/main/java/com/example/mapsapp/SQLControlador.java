package com.example.mapsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    public void insertarDatosCirculo(int radio, int colorL, int colorF, double longitud, double latitud) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.CIRCULO_RADIO, radio);
        cv.put(DBHelper.CIRCULO_COLORL, colorL);
        cv.put(DBHelper.CIRCULO_COLORF, colorF);
        cv.put(DBHelper.CIRCULO_LONG, longitud);
        cv.put(DBHelper.CIRCULO_LAT, latitud);
        database.insert(DBHelper.TABLE_CIRCULOS , null, cv);
    }

    public void insertarDatosMarcador(String nombre, double longitud, double latitud) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.MARCADOR_NOMBRE, nombre);
        cv.put(DBHelper.MARCADOR_LONG, longitud);
        cv.put(DBHelper.MARCADOR_LAT, latitud);
        database.insert(DBHelper.TABLE_MARCADORES , null, cv);
    }

    public Cursor leerDatosCirculo() {
        String[] todasLasColumnas = new String[] {
                DBHelper.CIRCULO_ID,
                DBHelper.CIRCULO_RADIO,
                DBHelper.CIRCULO_COLORL,
                DBHelper.CIRCULO_COLORF,
                DBHelper.CIRCULO_LONG,
                DBHelper.CIRCULO_LAT,
        };
        Cursor c = database.query(DBHelper.TABLE_CIRCULOS, todasLasColumnas, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor leerDatosMarcador() {
        String[] todasLasColumnas = new String[] {
                DBHelper.MARCADOR_ID,
                DBHelper.MARCADOR_NOMBRE,
                DBHelper.MARCADOR_LONG,
                DBHelper.MARCADOR_LAT,
        };
        Cursor c = database.query(DBHelper.TABLE_MARCADORES, todasLasColumnas, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


}
