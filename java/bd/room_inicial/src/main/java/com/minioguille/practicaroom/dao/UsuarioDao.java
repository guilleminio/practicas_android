package com.minioguille.practicaroom.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.minioguille.practicaroom.modelos.Usuario;

@Dao
public interface UsuarioDao {

    @Insert
    void agregarUsuario(Usuario aUsuario);

    @Query("SELECT * FROM Usuario WHERE correo_usuario =:aCorreo")
    Usuario obtenerUsuarioPorEmail(String aCorreo);
}
