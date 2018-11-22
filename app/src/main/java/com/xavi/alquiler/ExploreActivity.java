package com.xavi.alquiler;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


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

public class ExploreActivity extends Fragment {

    private RecyclerView lv;
    private JSONObject obj;
    private RecyclerView.LayoutManager layoutManager;

    private TextView text_dormitorio;
    private TextView text_baños;
    private TextView text_metros;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_explore, container, false);

       // text_dormitorio = view.findViewById(R.id.text_dormitorio);
        //text_baños = view.findViewById(R.id.text_baños);
        //text_metros = view.findViewById(R.id.text_metros);


        lv = view.findViewById(R.id.list_explore);
        layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);

        new get_Casas().execute();

        return view;

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
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("TokenAcceso", "servi12sis3");
            parametros.put("evento", "getfull_casa");
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
            if(success !=null){

            if (!success.isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(success);
                    if (obj.getInt("estado") != 1) {
                        //caso de errro mosotrar mensaje
                        getActivity().finish();
                    }
                    JSONArray arr = new JSONArray(obj.getString("resp"));
                    Adapter_explore adaptador = new Adapter_explore(getActivity(),arr);
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
