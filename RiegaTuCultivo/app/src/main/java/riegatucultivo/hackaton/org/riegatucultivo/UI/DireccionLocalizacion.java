package riegatucultivo.hackaton.org.riegatucultivo.UI;

import java.io.Serializable;

import riegatucultivo.hackaton.org.riegatucultivo.Modelo.GeoPunto;

/**
 * Created by Sebas on 06/08/2015.
 */
public class DireccionLocalizacion implements Serializable {

    private String provincia, localidad;
    private GeoPunto coordenada;
    private int cp, idLocalizacionBD;


    public DireccionLocalizacion(){

    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public GeoPunto getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(GeoPunto coordenada) {
        this.coordenada = coordenada;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    private String calle;

    public int getIdLocalizacionBD() {
        return idLocalizacionBD;
    }

    public void setIdLocalizacionBD(int idLocalizacionBD) {
        this.idLocalizacionBD = idLocalizacionBD;
    }

    public String getLocalidadProvincia(){
        if (  ! localidad.equalsIgnoreCase(provincia) ) return localidad + " ("+provincia+")";
        return localidad;
    }

    public boolean compararCon(DireccionLocalizacion direccion) {

        return getCp()==direccion.getCp() && getProvincia().equals(direccion.getProvincia()) && getLocalidad().equals(direccion.getLocalidad()) && coordenada.equals(direccion.getCoordenada()) ;
    }
}
