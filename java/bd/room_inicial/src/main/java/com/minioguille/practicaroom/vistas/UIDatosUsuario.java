package com.minioguille.practicaroom.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.minioguille.practicaroom.R;
import com.minioguille.practicaroom.controladores.DatosUsuarioControlador;
import com.minioguille.practicaroom.databinding.ActivityDatosUsuarioBinding;
import com.minioguille.practicaroom.utilidades.Utilidades;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UIDatosUsuario extends AppCompatActivity {

    private ActivityDatosUsuarioBinding mBinding;
    private String mCorreo;
    private DatosUsuarioControlador mControlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDatosUsuarioBinding.inflate(getLayoutInflater());
        View currentActivityView = mBinding.getRoot();
        setContentView(currentActivityView);

        obtenerDatos();

    }

    private void obtenerDatos(){
        String correo = getIntent().getStringExtra(Utilidades.INTENT_CLAVE_BUSQUEDA);

        if ( !correo.isEmpty()){
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    mControlador = new DatosUsuarioControlador(UIDatosUsuario.this.getApplicationContext(),correo);
                    executor.shutdown();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if ( executor.isShutdown())
                                completarDatos();
                        }
                    });
                }
            });
        }
    }

    private void completarDatos(){

        String nombre = getString(R.string.usuario) + ": " + mControlador.getmUsuario().getmNombre();
        mBinding.txUIDUNombre.setText(nombre);
        String correo = getString(R.string.correo_electronico) + ": " + mControlador.getmUsuario().getmCorreo();
        mBinding.txUIDUCorreo.setText(correo);
    }
}