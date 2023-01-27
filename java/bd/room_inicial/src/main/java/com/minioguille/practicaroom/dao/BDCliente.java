package com.minioguille.practicaroom.dao;

import android.content.Context;

import androidx.room.Room;

public class BDCliente {

    private static BDCliente mInstancia;

    private final BaseDeDatos mBD;

    private BDCliente(Context aContexto){
        mBD = Room.databaseBuilder(aContexto,BaseDeDatos.class,"Storage.db").fallbackToDestructiveMigration().build();
    }

    public static synchronized BDCliente obtenerInstancia(Context aContext){
        if ( mInstancia == null)
            mInstancia = new BDCliente(aContext);
        return mInstancia;
    }

    public BaseDeDatos obtenerBaseDeDatos(){
        return mBD;
    }
}
