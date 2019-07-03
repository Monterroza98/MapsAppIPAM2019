package com.example.mapsapp;

public class Marcador {

    private String titulo;
    private double latitud;
    private double longitud;


    public Marcador (String titulo, double latitud, double longitud){
        this.titulo = titulo;
        this.latitud= latitud;
        this.longitud = longitud;
    }

    public Marcador(){}


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
