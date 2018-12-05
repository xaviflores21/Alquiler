package com.xavi.alquiler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class tipo_publicacion_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView buscarCasa;
    private TextView ofertarCasa;

    private TextView text_Titulo;
    private TextView text_Venta;
    private TextView text_Alquiler;
    private TextView text_Anticretico;

    private CheckBox chek_venta;
    private CheckBox chek_alquiler;
    private CheckBox chek_anticretico;
    private Button btn_continuar;
    private Boolean venta = false, alquiler = false, anticretico = false;
    private int valor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_publicacion_);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buscarCasa = findViewById(R.id.buscarCasa);
        buscarCasa.setOnClickListener(this);
        ofertarCasa = findViewById(R.id.ofertarCasa);
        ofertarCasa.setOnClickListener(this);

        if (getUsr_log() == null) {
            Intent intent = new Intent(tipo_publicacion_Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buscarCasa:
                valor = 2;
                showDialogo();
                break;
            case R.id.ofertarCasa:
                valor = 1;
                showDialogo();
                break;
            case R.id.btn_continuar:
                if (validar()) {
                    enviar(valor);
                }
                break;
            case R.id.check_venta:
                if (chek_venta.isChecked()) {
                    venta = true;
                } else {
                    venta = false;
                }
                break;
            case R.id.check_alquiler:
                if (chek_alquiler.isChecked()) {
                    alquiler = true;
                } else {
                    alquiler = false;
                }
                break;
            case R.id.check_anticretico:
                if (chek_anticretico.isChecked()) {
                    anticretico = true;
                } else {
                    anticretico = false;
                }
                break;
        }
    }

    private void showDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(tipo_publicacion_Activity.this);
        View vi = getLayoutInflater().inflate(R.layout.layout_showdialog_publicar, null);

        text_Titulo = vi.findViewById(R.id.text_Titulo);
        text_Venta = vi.findViewById(R.id.text_Venta);
        text_Alquiler = vi.findViewById(R.id.text_Alquiler);
        text_Anticretico = vi.findViewById(R.id.text_Anticretico);

        if (valor == 2){
            text_Titulo.setText("¿Qué tipo de inmueble está buscando?");
            text_Venta.setText("Compra?");
            text_Alquiler.setText("Alquiler?");
            text_Anticretico.setText("Anticrético?");
        }else
        {
            text_Titulo.setText("Seleccione el tipo de inmueble que desea ofertar..");
            text_Venta.setText("Venta");
            text_Alquiler.setText("Alquiler");
            text_Anticretico.setText("Anticrético");
        }

        chek_venta = vi.findViewById(R.id.check_venta);
        chek_alquiler = vi.findViewById(R.id.check_alquiler);
        chek_anticretico = vi.findViewById(R.id.check_anticretico);
        btn_continuar = vi.findViewById(R.id.btn_continuar);

        builder.setView(vi);
        final AlertDialog dialogo = builder.create();
        dialogo.show();

        chek_venta.setOnClickListener(this);
        chek_alquiler.setOnClickListener(this);
        chek_anticretico.setOnClickListener(this);
        btn_continuar.setOnClickListener(this);
    }

    private boolean validar() {
        if (venta == false && alquiler == false && anticretico == false) {
            Toast.makeText(tipo_publicacion_Activity.this, "Debe seleccionar al menos una opcion..", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void enviar(int valor) {
        Intent intent2 = new Intent(tipo_publicacion_Activity.this, PropiedadActivity.class);
        try {
            JSONObject og = new JSONObject();
            og.put("venta", venta);
            og.put("alquiler", alquiler);
            og.put("anticretico", anticretico);
            og.put("tipo_public", valor);
            intent2.putExtra("obj", og.toString());
            startActivity(intent2);
        } catch (JSONException e) {
            e.printStackTrace();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
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

}
