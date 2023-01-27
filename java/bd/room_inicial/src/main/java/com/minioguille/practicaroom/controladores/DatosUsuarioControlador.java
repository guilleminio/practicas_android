package com.minioguille.practicaroom.controladores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.minioguille.practicaroom.dao.BDCliente;
import com.minioguille.practicaroom.modelos.Usuario;

public class DatosUsuarioControlador {

    private Usuario mUsuario;
    public  DatosUsuarioControlador(Context aContexto, String aCorreo){

        mUsuario = BDCliente.obtenerInstancia(aContexto).obtenerBaseDeDatos().accionesUsuario().obtenerUsuarioPorEmail(aCorreo);

    }
    public Usuario getmUsuario(){ return  mUsuario;}
}
