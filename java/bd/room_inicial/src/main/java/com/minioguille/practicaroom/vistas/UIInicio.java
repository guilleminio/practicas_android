package com.minioguille.practicaroom.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.minioguille.practicaroom.databinding.ActivityInicioBinding;

public class UIInicio extends AppCompatActivity {

    private ActivityInicioBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityInicioBinding.inflate(getLayoutInflater());
        View currentActivitiView = mBinding.getRoot();
        setContentView(currentActivitiView);

        mBinding.btnUIIRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIInicio.this,UICrearCuenta.class);
                startActivity(intent);
            }
        });
    }
}