package riegatucultivo.hackaton.org.riegatucultivo.Modelo;

import java.io.Serializable;

import riegatucultivo.hackaton.org.riegatucultivo.R;

/**
 * Created by Sebas on 03/10/2015.
 */
public class Sector implements Serializable {

    private String nombreSector, tipoSector, tipoAgua;

    private int idSector;

    public String getNombreSector() {
        return nombreSector;
    }

    public void setNombreSector(String nombreSector) {
        this.nombreSector = nombreSector;
    }

    public String getTipoSector() {
        return tipoSector;
    }

    public void setTipoSector(String tipoSector) {
        this.tipoSector = tipoSector;
    }

    public String getTipoAgua() {
        return tipoAgua;
    }

    public void setTipoAgua(String tipoAgua) {
        this.tipoAgua = tipoAgua;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public int getImagenTipo() {

        switch (tipoSector){
            case "Olivares":
                return R.drawable.ic_educacion;
            case "Tomates":
                return R.drawable.ic_reparacion;
            case "Cerezos":
                return R.drawable.ic_social;
            case "Almendros":
                return R.drawable.ic_domestico;
        }
        return -1;

    }
}
