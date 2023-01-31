package com.minioguille.crudroom.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.minioguille.crudroom.databinding.ActivityMainBinding;

public class UIMain extends UICustom {

    private ActivityMainBinding mBinding;

    private Button mBtnExplore;
    private Button mBtnNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();
        linkComponents();
        setEvents();
    }

    @Override
    protected void setView() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View activityView = mBinding.getRoot();
        setContentView(activityView);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void linkComponents() {
        mBtnExplore = mBinding.btnUIMExploreProducts;
        mBtnNew = mBinding.btnNewProduct;
    }

    @Override
    protected void setEvents() {
        mBtnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIMain.this, UIProductsList.class);
                startActivity(intent);
            }
        });

        mBtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIMain.this, UIProductDetail.class);
                startActivity(intent);
            }
        });
    }
}