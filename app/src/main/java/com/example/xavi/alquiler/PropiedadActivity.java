package com.example.xavi.alquiler;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.xavi.alquiler.Utiles.Contexto;
import com.example.xavi.alquiler.adapter.Adapter_propiedad;
import com.example.xavi.alquiler.clienteHTTP.HttpConnection;
import com.example.xavi.alquiler.clienteHTTP.MethodType;
import com.example.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class PropiedadActivity extends AppCompatActivity {

    private ListView lv;
    private JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propiedad);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = findViewById(R.id.list_propiedad);

        String d = getIntent().getStringExtra("obj_tipo_operacion");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            parametros.put("evento", "get_mis_propiedades");
            String respuesta = "";
            try {
                respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_index), MethodType.POST, parametros));
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
                    JSONArray jsonArray = new JSONArray(success);
                    Adapter_propiedad adaptador_mis_viajes = new Adapter_propiedad(PropiedadActivity.this, jsonArray, obj);
                    lv.setAdapter(adaptador_mis_viajes);
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
