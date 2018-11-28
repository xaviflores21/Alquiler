package com.xavi.alquiler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PerfilUsuarioActivity extends AppCompatActivity implements View.OnClickListener{
    private JSONObject obj;
    private TextView text_user;
    private TextView text_name;
    private TextView text_ci;
    private TextView text_fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        text_user = findViewById(R.id.text_user);
        text_name = findViewById(R.id.text_name);
        text_ci = findViewById(R.id.text_ci);
        text_fecha = findViewById(R.id.text_fecha);

        if (getUsr_log() == null) {
            Intent intent = new Intent(PerfilUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

    public JSONObject getUsr_log() {
        SharedPreferences preferencias = getSharedPreferences("myPref", MODE_PRIVATE);
        String usr = preferencias.getString("usr_log", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            try {
                JSONObject usr_log = new JSONObject(usr);

                String nombre = usr_log.getString("nombre");
                String apellido = usr_log.getString("apellidos");
                String ci = usr_log.getString("ci");
                String fecha = usr_log.getString("fecha_nac");
                String usuario = usr_log.getString("usuario");

                text_user.setText(usuario);
                text_name.setText(nombre + " " + apellido);
                text_ci.setText(ci);
                text_fecha.setText(fecha);

                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}
