package com.example.xavi.alquiler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class PublicarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String VENTA = "1";
    private static final String ALQUILER = "2";
    private static final String ANTICRETICO = "3";

    private Button btn_venta;
    private Button btn_alquiler;
    private Button btn_anticretico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_venta = findViewById(R.id.btn_venta);
        btn_alquiler = findViewById(R.id.btn_alquiler);
        btn_anticretico = findViewById(R.id.btn_anticretico);

        btn_venta.setOnClickListener(this);
        btn_alquiler.setOnClickListener(this);
        btn_anticretico.setOnClickListener(this);

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
        JSONObject obj = new JSONObject();
        switch (view.getId()){
            case R.id.btn_venta:
                try {
                    Intent intent = new Intent(PublicarActivity.this,PropiedadActivity.class);
                    obj.put("tipo",VENTA);
                    intent.putExtra("obj_tipo_operacion", obj.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_alquiler:
                try {
                    Intent intent2 = new Intent(PublicarActivity.this,PropiedadActivity.class);
                    obj.put("tipo",ALQUILER);
                    intent2.putExtra("obj_tipo_operacion", obj.toString());
                    startActivity(intent2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_anticretico:
                try {
                    Intent intent3 = new Intent(PublicarActivity.this,PropiedadActivity.class);
                    obj.put("tipo",ANTICRETICO);
                    intent3.putExtra("obj_tipo_operacion", obj.toString());
                    startActivity(intent3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
