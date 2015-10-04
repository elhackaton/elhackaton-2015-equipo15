package riegatucultivo.hackaton.org.riegatucultivo.Tools;

/**
 * Created by Sebas on 08/08/2015.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {


    public static final String URL_APP = "http://people-services.es/info/";


    private final String URI_MODIFICAR="servicios";
    private final String URI_INSERTAR_SERVICIO="servicios";
    private final String URI_ELIMINAR_SERVICIO="servicios/";
    private final String URI_OBTENER_SERVICIOS_USUARIO="servicios/";
    private final static String HTTP_RESTFUL="http://192.168.1.162/servicioweb/";


    public static final String GET_SENSORES=HTTP_RESTFUL +"obtener_sensores.php";


    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";

    public static boolean claveValida(String text) {
        String expresion = "((?=.*[a-z])(?=.*\\d).{6,20})";
        Pattern pat = Pattern.compile(expresion);
        Matcher comprobacion = pat.matcher( text );

        if(comprobacion.find()){
            return true;
        }

        return false;
    }

}