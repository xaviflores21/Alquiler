package com.xavi.alquiler;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.yarolegovich.slidingrootnav.SlidingRootNav;


public class PublicarActivity extends Fragment implements View.OnClickListener {

    private static final String VENTA = "1";
    private static final String ALQUILER = "2";
    private static final String ANTICRETICO = "3";

    private CheckBox chek_casas;
    private CheckBox chek_alquiler;
    private CheckBox chek_anticretico;
    private Button btn_continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_publicar, container, false);

        chek_casas = view.findViewById(R.id.check_casas);
        chek_alquiler = view.findViewById(R.id.check_alquiler);
        chek_anticretico = view.findViewById(R.id.check_anticretico);

        btn_continuar = view.findViewById(R.id.btn_continuar);

        return view;
    }

    @Override
    public void onClick(View view) {

    }

}
