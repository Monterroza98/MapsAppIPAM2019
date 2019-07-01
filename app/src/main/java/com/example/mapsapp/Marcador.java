package com.example.mapsapp;

public class Marcador {

    private String titulo;
    private Double latitud;
    private Double longitud;

    public Marcador (String titulo, Double latitud, Double longitud){
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
