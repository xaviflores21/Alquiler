package com.xavi.alquiler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class tipo_publicacion_Activity extends AppCompatActivity {

    private TextView buscarCasa;
    private TextView ofertarCasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_publicacion_);

        if (getUsr_log() == null) {
            Intent intent = new Intent(tipo_publicacion_Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        buscarCasa = findViewById(R.id.buscarCasa);
        ofertarCasa = findViewById(R.id.ofertarCasa);
        buscarCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tipo_publicacion_Activity.this, PublicarActivity.class);
                intent.putExtra("tipo_public", 2);
                startActivity(intent);
            }
        });
        ofertarCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tipo_publicacion_Activity.this, PublicarActivity.class);
                intent.putExtra("tipo_public", 1);
                startActivity(intent);
            }
        });
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

}
