package com.example.xavi.alquiler.Utiles;

import android.graphics.Bitmap;

public class imagenModel {
    private int tipo;
    private Bitmap bm;


    public imagenModel(int tipo, Bitmap bm) {
        this.tipo = tipo;
        this.bm = bm;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }
}
