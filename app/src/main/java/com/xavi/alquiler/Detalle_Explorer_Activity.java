package com.xavi.alquiler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xavi.alquiler.Utiles.Contexto;
import com.xavi.alquiler.adapter.Adapter_explore;
import com.xavi.alquiler.clienteHTTP.HttpConnection;
import com.xavi.alquiler.clienteHTTP.MethodType;
import com.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class Detalle_Explorer_Activity extends AppCompatActivity implements View.OnClickListener {

    private JSONObject obj;

    private TextView text_nombre1;
    private TextView text_nombre2;
    private TextView text_nombre3;
    private TextView text_precio1;
    private TextView text_precio2;
    private TextView text_precio3;

    private TextView text_descripcion;
    private TextView text_direccion;
    private TextView text_dormitorio;
    private TextView text_banho;
    private TextView text_m2;
    private Button btn_comentar;
    private LinearLayout linerVenta;
    private LinearLayout linerAlquiler;
    private LinearLayout linerAnticretico;

    private int id_explorer, id_usuario;

    private Boolean venta = false, alquiler = false, anticretico = false;
    private int tipo_public = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__explorer_);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text_nombre1 = findViewById(R.id.text_nombre1);
        text_nombre2 = findViewById(R.id.text_nombre2);
        text_nombre3 = findViewById(R.id.text_nombre3);
        text_precio1 = findViewById(R.id.text_precio1);
        text_precio2 = findViewById(R.id.text_precio2);
        text_precio3 = findViewById(R.id.text_precio3);

        text_descripcion = findViewById(R.id.text_descripcion);
        text_direccion = findViewById(R.id.text_direccion);
        text_dormitorio = findViewById(R.id.text_dormitorio);
        text_banho = findViewById(R.id.text_banho);
        text_m2 = findViewById(R.id.text_m2);

        btn_comentar = findViewById(R.id.btn_comentar);
        btn_comentar.setOnClickListener(this);

        linerVenta = findViewById(R.id.linerVenta);
        linerAlquiler = findViewById(R.id.linerAlquiler);
        linerAnticretico = findViewById(R.id.linerAnticretico);


        String d = getIntent().getStringExtra("obj");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
                id_explorer = obj.getInt("id_explore");
                new get_detalleExplorer().execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //hace la peticion al servidor

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_comentar:
                Intent inte = new Intent(Detalle_Explorer_Activity.this, ComentarioActivity.class);
                inte.putExtra("id_casa", id_explorer);
                startActivity(inte);
                break;
        }
    }

    public class get_detalleExplorer extends AsyncTask<Void, String, String> {
        private ProgressDialog progreso;
        //private int id_casa;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(Detalle_Explorer_Activity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("TokenAcceso", "servi12sis3");
            parametros.put("evento", "getbyid_casa");
            parametros.put("id", String.valueOf(id_explorer));

            String respuesta = "";
            try {
                respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, parametros));
            } catch (Exception ex) {
                Log.e(Contexto.APP_TAG, "Hubo un error al conectarse al servidor.");
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(final String success) {
            super.onPostExecute(success);
            progreso.dismiss();
            if (success != null) {

                if (!success.isEmpty()) {
                    try {
                        JSONObject obj = new JSONObject(success);
                        if (obj.getInt("estado") != 1) {
                            //caso de errro mosotrar mensaje
                            finish();
                        }

                        JSONObject casa = new JSONObject(obj.getString("resp"));
                        JSONArray tipos = casa.getJSONArray("arrCostos");

                        JSONObject objds;
                        int tipo = 0;
                        for (int i = 0; i < tipos.length(); i++) {
                            objds = tipos.getJSONObject(i);
                            tipo = objds.getInt("tipo");
                            if (tipo == 1) {
                                String nombre = objds.getString("nombre");
                                String costo = objds.getString("costo");
                                text_nombre1.setText("En " + nombre);
                                text_precio1.setText(costo + " $");
                                linerVenta.setVisibility(View.VISIBLE);
                            }
                            if (tipo == 2) {
                                String nombre = objds.getString("nombre");
                                String costo = objds.getString("costo");
                                text_nombre2.setText("En " + nombre);
                                text_precio2.setText(costo + " $");
                                linerAlquiler.setVisibility(View.VISIBLE);
                            }
                            if (tipo == 3) {
                                String nombre = objds.getString("nombre");
                                String costo = objds.getString("costo");
                                text_nombre3.setText("En " + nombre);
                                text_precio3.setText(costo + " $");
                                linerAnticretico.setVisibility(View.VISIBLE);
                            }
                        }

                        String descripcion = casa.getString("descripcion");
                        String direccion = casa.getString("direccion");
                        String dormitorio = casa.getString("cant_dormitorios");
                        String banhos = casa.getString("cant_banhos");
                        String metros2 = casa.getString("metros2");

                        text_descripcion.setText(descripcion);
                        text_direccion.setText(direccion);
                        text_dormitorio.setText("Dormitorios: " + dormitorio);
                        text_banho.setText("Baños: " + banhos);
                        text_m2.setText(metros2);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
            }
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }

}
