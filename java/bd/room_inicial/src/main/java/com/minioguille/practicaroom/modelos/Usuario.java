package com.minioguille.practicaroom.modelos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
     public @PrimaryKey(autoGenerate = true) int mId;
     public @ColumnInfo(name = "nombre_usuario") String mNombre;
     public @ColumnInfo(name = "correo_usuario") String mCorreo;
     public @ColumnInfo(name = "contrasenia_usuario") String mContrasenia;


    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmCorreo() {
        return mCorreo;
    }

    public void setmCorreo(String mCorreo) {
        this.mCorreo = mCorreo;
    }

    public String getmContrasenia() {
        return mContrasenia;
    }

    public void setmContrasenia(String mContrasenia) {
        this.mContrasenia = mContrasenia;
    }
}
