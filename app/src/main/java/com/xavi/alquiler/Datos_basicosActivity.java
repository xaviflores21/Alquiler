package com.xavi.alquiler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Datos_basicosActivity extends AppCompatActivity {

    private EditText text_precio;
    private EditText text_metros_terreno;
    private TextView textView_dormitorio;
    private TextView textView_banho;
    private EditText text_descripcion;
    private Button btn_registro;
    private JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_basicos);

        text_precio = findViewById(R.id.text_precio);
        text_metros_terreno = findViewById(R.id.text_metros_terreno);
        text_descripcion = findViewById(R.id.text_descripcion);
        btn_registro = findViewById(R.id.btn_registro);

        String d = getIntent().getStringExtra("inten_propiedad");
        if (d.length() > 0) {
            try {
                obj = new JSONObject(d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Spinner spin1 = findViewById(R.id.spn1);
        Spinner spin2 = findViewById(R.id.spn2);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                textView_dormitorio.setText(item);
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
                textView_banho.setText(item2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView_banho.setText("");
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
        String precio = text_precio.getText().toString().trim();
        String dormitorio = textView_dormitorio.getText().toString().trim();
        String banho = textView_banho.getText().toString().trim();
        String metros_propiedad = text_metros_terreno.getText().toString().trim();
        String descripcion = text_descripcion.getText().toString().trim();

        if (precio.isEmpty()) {
            text_precio.setError("Campo Obligatorio");
            validar = false;
        }
        if (dormitorio.isEmpty()) {
            textView_dormitorio.setError("Campo Obligatorio");
            validar = false;
        }
        if (banho.isEmpty()) {
            textView_banho.setError("Campo Obligatorio");
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
            //new RegistroActivity.Registrar(nombre,apellido,fecha,usuario,contraseña,carnet).execute();
        } else {
            return;
        }
    }


}
