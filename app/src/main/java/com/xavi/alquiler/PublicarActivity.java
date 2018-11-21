package com.xavi.alquiler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PublicarActivity extends AppCompatActivity implements View.OnClickListener {


    private CheckBox chek_venta;
    private CheckBox chek_alquiler;
    private CheckBox chek_anticretico;
    private Button btn_continuar;

    private Boolean venta =false , alquiler = false , anticretico = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chek_venta = findViewById(R.id.check_venta);
        chek_alquiler = findViewById(R.id.check_alquiler);
        chek_anticretico = findViewById(R.id.check_anticretico);
        btn_continuar = findViewById(R.id.btn_continuar);

        chek_venta.setOnClickListener(this);
        chek_alquiler.setOnClickListener(this);
        chek_anticretico.setOnClickListener(this);
        btn_continuar.setOnClickListener(this);

        if(getUsr_log()==null){
            Intent intent = new Intent(PublicarActivity.this , LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Opcion para ir atras sin reiniciar el la actividad anterior de nuevo
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public JSONObject getUsr_log() {
        SharedPreferences preferencias = getSharedPreferences("myPref", MODE_PRIVATE);
        String usr = preferencias.getString("usr_log", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            try {
                JSONObject usr_log = new JSONObject(usr);
                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continuar:
                validar();
                break;
            case R.id.check_venta:
                if (chek_venta.isChecked()){
                    venta  = true;
                }else{
                    venta = false;
                }
                break;
            case R.id.check_alquiler:
                if (chek_alquiler.isChecked()){
                    alquiler = true;
                }else{
                    alquiler = false;
                }
                break;
            case R.id.check_anticretico:
                if (chek_anticretico.isChecked()){
                    anticretico = true;
                }else{
                    anticretico = false;
                }
                break;
            }
        }

    private void validar() {
        if(venta == false && alquiler == false && anticretico == false){
            Toast.makeText(PublicarActivity.this, "tiene que tener algun preccionadoad", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(PublicarActivity.this, PropiedadActivity.class);
        try {
            JSONObject og = new JSONObject();
            og.put("venta", venta);
            og.put("alquiler", alquiler);
            og.put("anticretico", anticretico);
            intent.putExtra("obj", og.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
