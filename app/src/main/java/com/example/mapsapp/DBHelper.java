package com.example.mapsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

        // Información de las tablas
        public static final String TABLE_CIRCULOS = "CIRCULOS";
        public static final String CIRCULO_ID= "_id";
        public static final String CIRCULO_RADIO = "radio";
        public static final String CIRCULO_COLORL = "colorL";
        public static final String CIRCULO_COLORF = "colorf";
        public static final String CIRCULO_LONG = "longitud";
        public static final String CIRCULO_LAT = "latitud";

    public static final String TABLE_MARCADORES = "MARCADORES";
    public static final String MARCADOR_ID= "_id";
    public static final String MARCADOR_NOMBRE= "nombre";
    public static final String MARCADOR_LONG = "longitud";
    public static final String MARCADOR_LAT = "latitud";


        // información del a base de datos
        static final String DB_NAME = "DBMAPS";
        static final int DB_VERSION = 5;

        // Información de la base de datos
        private static final String CREATE_TABLE_CIRCULOS = "create table "
                + TABLE_CIRCULOS + "(" + CIRCULO_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CIRCULO_RADIO + " INTEGER NOT NULL, "
                + CIRCULO_COLORL+ " INTEGER NOT NULL, "
                + CIRCULO_COLORF+ " INTEGER NOT NULL, "
                + CIRCULO_LONG+ " DOUBLE NOT NULL, "
                + CIRCULO_LAT+ " DOUBLE NOT NULL);";

        private static final String CREATE_TABLE_MARCADORES = "create table "
                + TABLE_MARCADORES + "(" + MARCADOR_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MARCADOR_NOMBRE + " TEXT NOT NULL, "
                + MARCADOR_LONG+ " DOUBLE NOT NULL, "
                + MARCADOR_LAT+ " DOUBLE NOT NULL);";

        public DBHelper(Context context) {
            super(context, DB_NAME, null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CIRCULOS);
            db.execSQL(CREATE_TABLE_MARCADORES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CIRCULOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARCADORES);

            onCreate(db);
        }
}
