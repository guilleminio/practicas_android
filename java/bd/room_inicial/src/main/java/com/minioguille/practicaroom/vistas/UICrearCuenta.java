package com.minioguille.practicaroom.vistas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.minioguille.practicaroom.R;
import com.minioguille.practicaroom.utilidades.Utilidades;
import com.minioguille.practicaroom.controladores.CrearCuentaControlador;
import com.minioguille.practicaroom.databinding.ActivityCrearCuentaBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UICrearCuenta extends AppCompatActivity {



    private ActivityCrearCuentaBinding mBinding;
    private String mMensajeFormulario;

    private ArrayList<TextInputLayout> mCamposFormulario;
    private ArrayList<TextInputLayout> mCamposErroneos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializacion();
        asignarEventos();

    }

    private void inicializacion(){
        mBinding = ActivityCrearCuentaBinding.inflate(getLayoutInflater());
        View currentActivityView = mBinding.getRoot();
        setContentView(currentActivityView);

        mCamposFormulario = new ArrayList<>();
        mCamposErroneos = new ArrayList<>();

        establecerCamposFormulario();
    }

    private void establecerCamposFormulario() {
        ConstraintLayout clContenedorFormulario = mBinding.clUICCContenedorFormulario;
        int camposTotales = clContenedorFormulario.getChildCount();
        for ( int i = 0; i < camposTotales; i++){
            View child = clContenedorFormulario.getChildAt(i);
            if ( child instanceof TextInputLayout){
                TextInputLayout campo = (TextInputLayout) child;
                mCamposFormulario.add(campo);
            }
        }
    }

    private void asignarEventos(){
        mBinding.btnUICCCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurarCampos();
                if ( !formulario()) {
                    crearCuenta();
                }else
                    mostrarAdvertencia();
            }
        });
    }

    private void crearCuenta() {

        CrearCuentaControlador controlador = new CrearCuentaControlador();
        String nombre = mBinding.edUICCNombreUsuario.getEditableText().toString();
        String correo = mBinding.edUICCCorreo.getEditableText().toString();
        String contrasenia = mBinding.edUICCContrasenia.getEditableText().toString();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                controlador.agregarUsuario(UICrearCuenta.this.getApplicationContext(),nombre,correo,contrasenia);
                executor.shutdown();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.btnUICCCrear.setVisibility(View.INVISIBLE);
                        mBinding.pbUICCProgreso.setVisibility(View.VISIBLE);

                        if( executor.isShutdown()) {
                            Intent intent = new Intent(UICrearCuenta.this, UIDatosUsuario.class);
                            intent.putExtra(Utilidades.INTENT_CLAVE_BUSQUEDA, correo);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private boolean formulario(){

        if ( formularioVacio())
            return true;

        if ( !validarNombreUsuario() )
            return true;

        if ( !validarCorreoElectronico())
            return true;

        if ( !validarContrasenia())
            return true;

        return false;
    }

    private boolean validarNombreUsuario(){
        String usuario = mBinding.edUICCNombreUsuario.getEditableText().toString();

        if ( !Utilidades.valiarFormatoNombreUsuairo(usuario)){
            mMensajeFormulario = getString(R.string.mensaje_formato_invalido_usuario);
            mCamposErroneos.add(mBinding.txUICCNombreUsuario);
            return false;
        }

        return true;
    }

    private boolean validarCorreoElectronico(){
        String correo = mBinding.edUICCCorreo.getEditableText().toString();

        if ( !Utilidades.validarFormatoCorreoElectronico(correo)){
            mMensajeFormulario = getString(R.string.mensaje_formato_invalido_correo);
            mCamposErroneos.add(mBinding.txUICCCorreo);
            return false;
        }

        return true;
    }

    private boolean validarContrasenia(){
        String contrasenia = mBinding.edUICCContrasenia.getEditableText().toString();

        if ( !Utilidades.validarFormatoContrasenia(contrasenia)){
            mMensajeFormulario = getString(R.string.mensaje_formato_invalido_contrasenia);
            mCamposErroneos.add(mBinding.txUICCContrasenia);
            return false;
        }

        return true;
    }

    private void resaltarCampoErroneo(TextInputLayout aTxCampo ){
        aTxCampo.setError(" ");
        aTxCampo.setErrorEnabled(true);
    }

    private void borrarErrorEnCampo(TextInputLayout aTxCampo){
        aTxCampo.setErrorEnabled(false);
    }

    private boolean formularioVacio(){

        int camposTotales = mCamposFormulario.size();
        boolean vacio = false;
        for ( int i = 0; i < camposTotales;i++){
            TextInputLayout campo = mCamposFormulario.get(i);
            String contenido = campo.getEditText().getEditableText().toString();
            if ( contenido.isEmpty()){
                mCamposErroneos.add(campo);
                if ( !vacio) {
                    mMensajeFormulario = getString(R.string.mensaje_formulario_incompleto);
                    vacio = true;
                }
            }
        }

        return vacio;
    }

    private void resaltarErrorFormulario(){

        int camposTotales = mCamposErroneos.size();

        if ( camposTotales > 0) {
            mCamposErroneos.get(0).requestFocus();
            for (int i = 0; i < camposTotales; i++) {
                TextInputLayout campo = mCamposErroneos.get(i);
                resaltarCampoErroneo(campo);
            }
        }
    }

    private void restaurarCampos(){

        int camposTotales = mCamposErroneos.size();
        for ( int i = 0; i < camposTotales; i++)
            borrarErrorEnCampo(mCamposErroneos.get(i));
        mCamposErroneos.clear();
    }

    private void mostrarAdvertencia(){
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(this);
        constructorDialogo.setTitle(getString(R.string.dialogo_titulo_advertencia));
        constructorDialogo.setMessage(mMensajeFormulario).setPositiveButton(getString(R.string.dialogo_boton_positivo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resaltarErrorFormulario();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                resaltarErrorFormulario();
            }
        });
        constructorDialogo.create();
        constructorDialogo.show();
    }

}