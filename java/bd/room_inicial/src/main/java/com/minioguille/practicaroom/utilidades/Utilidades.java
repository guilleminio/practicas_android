package com.minioguille.practicaroom.utilidades;

import java.util.regex.Pattern;

public class Utilidades {

    public static final String INTENT_CLAVE_BUSQUEDA = "correo";
    private static final Pattern PATRON_NOMBRE_USUARIO = Pattern.compile("[a-zA-Z0-9\\_]{1,256}");

    private static final Pattern PATRON_CORREO_ELECTRONICO = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static final Pattern PATRON_CONTRASENIA = Pattern.compile("[a-zA-Z0-9\\_]{8,16}");

    public static boolean valiarFormatoNombreUsuairo(String aNombreUsuario ){
        return PATRON_NOMBRE_USUARIO.matcher(aNombreUsuario).matches();
    }

    public static boolean validarFormatoCorreoElectronico(String aCorreo ){
        return PATRON_CORREO_ELECTRONICO.matcher(aCorreo).matches();
    }
    public static boolean validarFormatoContrasenia( String aContrasenia){
        return PATRON_CONTRASENIA.matcher(aContrasenia).matches();
    }

}
