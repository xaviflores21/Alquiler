package com.example.xavi.alquiler.Utiles;

import android.app.Application;
import android.content.BroadcastReceiver;

public class Contexto extends Application {

    private static Contexto instancia;
    public static String APP_TAG = "alquiler";
    private BroadcastReceiver reciberconect;
    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;
    }

    public static Contexto getInstancia() {
        return instancia;
    }

}