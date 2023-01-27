package com.minioguille.practicaroom.controladores;

import android.content.Context;

import com.minioguille.practicaroom.dao.BDCliente;
import com.minioguille.practicaroom.modelos.Usuario;

public class CrearCuentaControlador {

    public void agregarUsuario(Context aContexto,String aNombre, String aCorreo, String aContrasenia ){
        Usuario usuario = new Usuario();
        usuario.setmNombre(aNombre);
        usuario.setmCorreo(aCorreo);
        usuario.setmContrasenia(aContrasenia);
        BDCliente.obtenerInstancia(aContexto).obtenerBaseDeDatos().accionesUsuario().agregarUsuario(usuario);

    }

}
