package com.minioguille.practicaroom.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.minioguille.practicaroom.modelos.Usuario;

@Database(entities = {Usuario.class}, version = 1,exportSchema = false)
public abstract class BaseDeDatos extends RoomDatabase {
    public abstract UsuarioDao accionesUsuario();
}
