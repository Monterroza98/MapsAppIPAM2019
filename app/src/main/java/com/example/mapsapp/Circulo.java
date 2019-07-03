package com.example.mapsapp;

public class Circulo {
    private int radio, colorL, colorF;
    private double longitud, latitud;

    public Circulo(int radio, int colorL, int colorF, double longitud, double latitud){
        this.radio=radio;
        this.colorL=colorL;
        this.colorF=colorF;
        this.longitud=longitud;
        this.latitud=latitud;
    }

    public Circulo(){}

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public int getColorL() {
        return colorL;
    }

    public void setColorL(int colorL) {
        this.colorL = colorL;
    }

    public int getColorF() {
        return colorF;
    }

    public void setColorF(int colorF) {
        this.colorF = colorF;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
