package com.xavi.alquiler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class Datos_basicosActivity extends AppCompatActivity {

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
    private Button btn_registro;
    private JSONObject obj;
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
        setContentView(R.layout.activity_datos_basicos);

        liner_venta = findViewById(R.id.liner_venta);
        liner_alquiler = findViewById(R.id.liner_alquiler);
        liner_anticretico = findViewById(R.id.liner_anticretico);
        text_precioVenta = findViewById(R.id.text_precioVenta);
        text_precioAlquiler = findViewById(R.id.text_precioAlquiler);
        text_precioAnticretico = findViewById(R.id.text_precioAnticretico);
        text_metros_terreno = findViewById(R.id.text_metros_terreno);
        text_descripcion = findViewById(R.id.text_descripcion);
        btn_registro = findViewById(R.id.btn_registro);
        textView_baño = findViewById(R.id.tv_baño);
        textView_dormitorio = findViewById(R.id.tv_dormitorio);

        String d = getIntent().getStringExtra("obj");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
                Venta = obj.getBoolean("venta");
                Alquiler = obj.getBoolean("alquiler");
                Anticretico = obj.getBoolean("anticretico");
                id_propiedad = obj.getString("id_propiedad");
                tipo_public = obj.getInt("tipo_public");
                if (Venta == true) {
                    liner_venta.setVisibility(View.VISIBLE);
                }
                if (Alquiler == true) {

                    liner_alquiler.setVisibility(View.VISIBLE);
                }
                if (Anticretico == true) {
                    liner_anticretico.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        spin1 = findViewById(R.id.spn1);
        spin2 = findViewById(R.id.spn2);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Str_spin1=item;
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
                Str_spin2=item2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView_baño.setText("");
            }
        });


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
            Intent intent = new Intent(Datos_basicosActivity.this, UbicacionActivity.class);
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


}
