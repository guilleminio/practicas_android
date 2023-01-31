package com.minioguille.crudroom.views;

import androidx.appcompat.app.AppCompatActivity;

public abstract class UICustom extends AppCompatActivity {

    protected abstract void setView();
    protected abstract void init();
    protected abstract void linkComponents();
    protected abstract void setEvents();

}
