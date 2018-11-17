package com.example.xavi.alquiler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class Favoritos_Clientes extends Fragment {

    private Button btn_elegir_destino;
    private LinearLayout container_frame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favoritos_clientes, container, false);

        return view;
    }

}
