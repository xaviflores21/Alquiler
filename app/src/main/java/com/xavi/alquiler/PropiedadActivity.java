package com.xavi.alquiler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.xavi.alquiler.Listener.ProductoAdapterClik;
import com.xavi.alquiler.R;
import com.xavi.alquiler.Utiles.Contexto;
import com.xavi.alquiler.adapter.Adapter_propiedad;
import com.xavi.alquiler.clienteHTTP.HttpConnection;
import com.xavi.alquiler.clienteHTTP.MethodType;
import com.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class PropiedadActivity extends AppCompatActivity implements ProductoAdapterClik {

    private RecyclerView lv;
    private JSONObject obj;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propiedad);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = findViewById(R.id.list_propiedad);

        layoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(layoutManager);

        String d = getIntent().getStringExtra("obj");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //hace la peticion al servidor
        new get_propiedades().execute();
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
    public void onClick(int id, View view) {
        Intent intent = new Intent(PropiedadActivity.this, Datos_basicosActivity.class);
        try {
            obj.put("id_propiedad", id);

            intent.putExtra("obj", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        startActivity(intent);
    }


    public class get_propiedades extends AsyncTask<Void, String, String> {
        private ProgressDialog progreso;

        get_propiedades() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(PropiedadActivity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("TokenAcceso", "servi12sis3");
            parametros.put("evento", "getall_tipo_propiedad");
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
            if (!success.isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(success);
                    if (obj.getInt("estado") != 1) {
                        //caso de errro mosotrar mensaje
                        finish();
                    }
                    JSONArray arr = new JSONArray(obj.getString("resp"));
                    Adapter_propiedad adaptador = new Adapter_propiedad(PropiedadActivity.this, arr, obj, PropiedadActivity.this);

                    lv.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }

}
