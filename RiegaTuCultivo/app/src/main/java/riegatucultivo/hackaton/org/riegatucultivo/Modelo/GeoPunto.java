package riegatucultivo.hackaton.org.riegatucultivo.Modelo;

import java.io.Serializable;

/**
 * Created by Sebas on 15/05/2015.
 */
public class GeoPunto implements Serializable{
    private double longitud, latitud;

    public GeoPunto(double latitudN, double longitudN){
        this.longitud = longitudN;
        this.latitud = latitudN;
    }

    public double distancia(GeoPunto punto){
        double km = 111.302;
        double degtorad = 0.01745329;
        double radtodeg = 57.29577951;
        double dlong = (punto.longitud - this.longitud);

        double dvalue = (Math.sin(punto.getLatitud() * degtorad) * Math.sin(latitud*degtorad)) + (Math.cos(punto.getLatitud()*degtorad) * Math.cos(latitud * degtorad) * Math.cos(dlong * degtorad));
        double dd = Math.acos(dvalue) * radtodeg;
        return (double)  Math.round((dd*km)*100)/100; // distancia en Km
    }

    @Override
    public String toString(){
        String cadena ="Longitud: "+longitud + ", Latitud: "+latitud ;
        return cadena;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLongitud(double longitudN) {
        this.longitud = longitudN;
    }


    public double getLongitud() {
        return longitud;
    }

    public void setLatitud(double latitudN) {
        this.latitud = latitudN;
    }

    @Override
    public boolean equals(Object coordenada){
        return coordenada !=null && getLatitud() == ( (GeoPunto) coordenada).getLatitud() && getLongitud() == ( (GeoPunto) coordenada).getLongitud();
    }
}
