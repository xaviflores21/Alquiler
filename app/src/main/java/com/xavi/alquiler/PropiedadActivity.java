package com.xavi.alquiler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
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

    private static final String VENTA = "1";
    private static final String ALQUILER = "2";
    private static final String ANTICRETICO = "3";

    private EditText text_precioVenta;
    private EditText text_precioAlquiler;
    private EditText text_precioAnticretico;
    private EditText text_metros_terreno;
    private TextView textView_dormitorio;
    private TextView textView_baño;
    private EditText text_descripcion;
    private LinearLayout liner_venta;
    private LinearLayout liner_alquiler;
    private LinearLayout liner_anticretico;
    private LinearLayout liner_publicar_busqueda;
    private Button btn_registro;
    private Boolean Venta;
    private Boolean Alquiler;
    private Boolean Anticretico;
    private String id_propiedad;
    private Spinner spin1;
    private Spinner spin2;
    private String Str_spin1;
    private String Str_spin2;
    private int tipo_public = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propiedad);

        Toolbar toolbar = findViewById(R.id.toolbarTipoPropiedad);
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

    @Override
    public void onClick(final int id, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PropiedadActivity.this);
        View vi = getLayoutInflater().inflate(R.layout.layout_showdialog_datos_basicos, null);

        liner_venta = vi.findViewById(R.id.liner_venta);
        liner_alquiler = vi.findViewById(R.id.liner_alquiler);
        liner_anticretico = vi.findViewById(R.id.liner_anticretico);
        liner_publicar_busqueda = vi.findViewById(R.id.liner_publicar_busqueda);

        text_precioVenta = vi.findViewById(R.id.text_precioVenta);
        text_precioAlquiler = vi.findViewById(R.id.text_precioAlquiler);
        text_precioAnticretico = vi.findViewById(R.id.text_precioAnticretico);
        text_metros_terreno = vi.findViewById(R.id.text_metros_terreno);
        text_descripcion = vi.findViewById(R.id.text_descripcion);
        btn_registro = vi.findViewById(R.id.btn_registro);
        textView_baño = vi.findViewById(R.id.tv_baño);

        textView_dormitorio = vi.findViewById(R.id.tv_dormitorio);

        String d = getIntent().getStringExtra("obj");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
                Venta = obj.getBoolean("venta");
                Alquiler = obj.getBoolean("alquiler");
                Anticretico = obj.getBoolean("anticretico");
                id_propiedad = id + "";
                tipo_public = obj.getInt("tipo_public");

                if (tipo_public == 1) {
                    if (Venta == true) {
                        liner_venta.setVisibility(vi.VISIBLE);
                    }
                    if (Alquiler == true) {

                        liner_alquiler.setVisibility(vi.VISIBLE);
                    }
                    if (Anticretico == true) {
                        liner_anticretico.setVisibility(vi.VISIBLE);
                    }
                } else if (tipo_public == 2) {
                    liner_publicar_busqueda.setVisibility(vi.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        spin1 = vi.findViewById(R.id.spn1);
        spin2 = vi.findViewById(R.id.spn2);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Str_spin1 = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView_dormitorio.setText("");
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item2 = adapterView.getItemAtPosition(i).toString();
                Str_spin2 = item2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView_baño.setText("");
            }
        });

        builder.setView(vi);
        final AlertDialog dialogo = builder.create();
        dialogo.show();

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_registro:
                        validate();
                        break;
                }
            }
        });
    }

    private void validate() {

        Boolean validar = true;
        String precioVenta = text_precioVenta.getText().toString().trim();
        String precioAlquiler = text_precioAlquiler.getText().toString().trim();
        String precioAnticretico = text_precioAnticretico.getText().toString().trim();
        String dormitorio = Str_spin1;
        String baño = Str_spin2;
        String metros_propiedad = text_metros_terreno.getText().toString().trim();
        String descripcion = text_descripcion.getText().toString().trim();

        if (Venta == true) {
            if (precioVenta.isEmpty()) {
                text_precioVenta.setError("Campo Obligatorio");
                validar = false;
            }
        }
        if (Alquiler == true) {
            if (precioAlquiler.isEmpty()) {
                text_precioAlquiler.setError("Campo Obligatorio");
                validar = false;
            }
        }
        if (Anticretico == true) {
            if (precioAnticretico.isEmpty()) {
                text_precioAnticretico.setError("Campo Obligatorio");
                validar = false;
            }
        }
        if (dormitorio.isEmpty()) {
            textView_dormitorio.setError("Campo Obligatorio");
            validar = false;
        }
        if (baño.isEmpty()) {
            textView_baño.setError("Campo Obligatorio");
            validar = false;
        }
        if (metros_propiedad.isEmpty()) {
            text_metros_terreno.setError("Campo Obligatorio");
            validar = false;
        }
        if (descripcion.isEmpty()) {
            text_descripcion.setError("Campo Obligatorio");
            validar = false;
        }
        if (validar) {
            Intent intent = new Intent(PropiedadActivity.this, UbicacionActivity.class);
            try {
                JSONObject newObj = new JSONObject();
                JSONArray array = new JSONArray();
                JSONObject aux;

                aux = new JSONObject();
                aux.put("tipo", VENTA);
                aux.put("isActivo", Venta);
                aux.put("costo", precioVenta);
                array.put(aux);

                aux = new JSONObject();
                aux.put("tipo", ALQUILER);
                aux.put("isActivo", Alquiler);
                aux.put("costo", precioAlquiler);
                array.put(aux);

                aux = new JSONObject();
                aux.put("tipo", ANTICRETICO);
                aux.put("isActivo", Anticretico);
                aux.put("costo", precioAnticretico);
                array.put(aux);

                newObj.put("costos", array.toString());
                newObj.put("id_tipo_propiedad", id_propiedad);
                newObj.put("cant_dormitorios", dormitorio);
                newObj.put("cant_banhos", baño);
                newObj.put("metros2", metros_propiedad);
                newObj.put("descripcion", descripcion);
                newObj.put("tipo_public", tipo_public);

                intent.putExtra("obj", newObj.toString());
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return;
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
