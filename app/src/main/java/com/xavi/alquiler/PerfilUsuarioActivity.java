package com.xavi.alquiler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xavi.alquiler.Listener.AdapterUsuario;
import com.xavi.alquiler.Utiles.Contexto;
import com.xavi.alquiler.clienteHTTP.HttpConnection;
import com.xavi.alquiler.clienteHTTP.MethodType;
import com.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class PerfilUsuarioActivity extends Fragment implements AdapterUsuario {

    private TextView text_correo;
    private TextView text_user;
    private TextView text_llamada;
    private TextView text_pais;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil_usuario, container, false);

        text_correo = view.findViewById(R.id.text_correo);
        text_user = view.findViewById(R.id.text_user);
        text_llamada = view.findViewById(R.id.text_llamada);
        text_pais = view.findViewById(R.id.text_pais);


        if (getUsr_log() == null) {
            Intent intent = new Intent(PublicarActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return view;

    }

    @Override
    public void onClick(int id, View view) {

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
            parametros.put("evento", "getbyid_casa");
            try {
                parametros.put("id", obj.getInt("id_explore") + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                                text_nombre1.setText("Nombre: " + nombre);
                                text_precio1.setText("Precio: " + costo);
                                linerVenta.setVisibility(View.VISIBLE);
                            }
                            if (tipo == 2) {
                                String nombre = objds.getString("nombre");
                                String costo = objds.getString("costo");
                                text_nombre2.setText("Nombre: " + nombre);
                                text_precio2.setText("Precio: " + costo);
                                linerAlquiler.setVisibility(View.VISIBLE);
                            }
                            if (tipo == 3) {
                                String nombre = objds.getString("nombre");
                                String costo = objds.getString("costo");
                                text_nombre3.setText("Nombre: " + nombre);
                                text_precio3.setText("Precio: " + costo);
                                linerAnticretico.setVisibility(View.VISIBLE);
                            }
                        }

                        String descripcion = casa.getString("descripcion");
                        String direccion = casa.getString("direccion");
                        String dormitorio = casa.getString("cant_dormitorios");
                        String banhos = casa.getString("cant_banhos");
                        String metros2 = casa.getString("metros2");

                        text_descripcion.setText("Descripcion: " + descripcion);
                        text_direccion.setText("Direccion: " + direccion);
                        text_dormitorio.setText("Cant. Dormitorios: " + dormitorio);
                        text_banho.setText("Cant. Bnahos" + banhos);
                        text_m2.setText("M2: " + metros2);


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
