package com.xavi.alquiler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xavi.alquiler.Utiles.Contexto;
import com.xavi.alquiler.clienteHTTP.HttpConnection;
import com.xavi.alquiler.clienteHTTP.MethodType;
import com.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class Detalle_Explorer_Activity extends AppCompatActivity {

    private JSONObject obj;

    private TextView text_precio1;
    private TextView text_nombre1;
    private TextView text_precio2;
    private TextView text_nombre2;
    private TextView text_precio3;
    private TextView text_nombre3;

    private  TextView text_descripcion;
    private  TextView text_direccion;
    private  TextView text_dormitorio;
    private  TextView text_banho;
    private  TextView text_m2;

    private LinearLayout linerVenta;
    private LinearLayout linerAlquiler;
    private LinearLayout linerAnticretico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__explorer_);

        text_precio1 = findViewById(R.id.text_precio1);
        text_precio2 = findViewById(R.id.text_precio2);
        text_precio3 = findViewById(R.id.text_precio3);
        text_nombre1 = findViewById(R.id.text_nombre1);
        text_nombre2 = findViewById(R.id.text_nombre2);
        text_nombre3 = findViewById(R.id.text_nombre3);

        text_descripcion = findViewById(R.id.text_descripcion);
        text_direccion = findViewById(R.id.text_direccion);
        text_dormitorio = findViewById(R.id.text_dormitorio);
        text_banho = findViewById(R.id.text_banho);
        text_m2 = findViewById(R.id.text_m2);


        linerVenta = findViewById(R.id.linerVenta);
        linerAlquiler = findViewById(R.id.liner_alquiler);
        linerAnticretico = findViewById(R.id.linerAnticretico);

        String d = getIntent().getStringExtra("obj");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //hace la peticion al servidor
        new Detalle_Explorer_Activity.get_detalleExplorer().execute();
    }


    public class get_detalleExplorer extends AsyncTask<Void, String, String> {
        private ProgressDialog progreso;

        get_detalleExplorer() {
        }

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
                        //caso de error mosotrar mensaje
                        finish();
                    }

                    JSONObject obj2 = new JSONObject(obj.getString("res"));
                    JSONArray array = new JSONArray(obj2.getString("costo"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject yeison = array.getJSONObject(i);
                        String nombre = yeison.getString("nombre");
                        int precio = yeison.getInt("precio");
                        int tipo = yeison.getInt("tipo");
                        if (tipo == 1) {
                            linerVenta.setVisibility(View.VISIBLE);
                            text_nombre1.setText(nombre);
                            text_precio1.setText(precio + "");
                        }
                        if (tipo == 2) {
                            linerAlquiler.setVisibility(View.VISIBLE);
                            text_nombre2.setText(nombre);
                            text_precio2.setText(precio + "");
                        }
                        if (tipo == 3) {
                            linerAnticretico.setVisibility(View.VISIBLE);
                            text_nombre3.setText(nombre);
                            text_precio3.setText(precio + "");
                        }
                    }

                    text_descripcion.setText(obj2.getString("descripcion"));
                    text_direccion.setText(obj2.getString("direccion"));
                    text_dormitorio.setText(obj2.getString("dormitorio"));
                    text_banho.setText(obj2.getString("banho"));
                    text_m2.setText(obj2.getString("m2"));

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
