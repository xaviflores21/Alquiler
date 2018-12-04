package com.xavi.alquiler;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xavi.alquiler.Listener.ExplorerAdapterClick;
import com.xavi.alquiler.Listener.ProductoAdapterClik;
import com.xavi.alquiler.R;
import com.xavi.alquiler.Utiles.Contexto;
import com.xavi.alquiler.adapter.Adapter_explore;
import com.xavi.alquiler.adapter.Adapter_propiedad;
import com.xavi.alquiler.clienteHTTP.HttpConnection;
import com.xavi.alquiler.clienteHTTP.MethodType;
import com.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class ExploreActivity extends Fragment implements ProductoAdapterClik {

    private RecyclerView lv;
    private JSONObject obj;
    private RecyclerView.LayoutManager layoutManager;

    private TextView venta;
    private TextView alquiler;
    private TextView anticretico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_explore, container, false);

        lv = view.findViewById(R.id.list_explore);
        layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);

        venta = view.findViewById(R.id.tx_venta);
        alquiler = view.findViewById(R.id.tx_alquiler);
        anticretico = view.findViewById(R.id.tx_anticretico);

        new get_Casas().execute();

        return view;
    }

    @Override
    public void onClick(int id, View view) {
        Intent intent = new Intent(getActivity(), Detalle_Explorer_Activity.class);
        try {
            obj = new JSONObject();
            obj.put("id_explore", id);
            intent.putExtra("obj", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    public class get_Casas extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        get_Casas() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(getActivity());
            progreso.setIndeterminate(true);
            progreso.setTitle("Si el inmueble que buscas no esta en la lista, publica tu requerimiento y nosotros te haremos llegar una notificación.");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("TokenAcceso", "servi12sis3");
            parametros.put("evento", "getfull_casaOpti");
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
                            getActivity().finish();
                        }
                        JSONArray arr = new JSONArray(obj.getString("resp"));
                        Adapter_explore adaptador = new Adapter_explore(getContext(), arr, ExploreActivity.this);
                        lv.setAdapter(adaptador);
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
