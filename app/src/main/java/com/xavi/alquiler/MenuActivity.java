package com.xavi.alquiler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {

    private TextView navtitle;
    private TextView text_nameMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_left_drawer);

        navtitle = findViewById(R.id.navtitle);
        text_nameMenu = findViewById(R.id.text_nameMenu);

        if (getUsr_log() == null) {
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

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
                String usuario = usr_log.getString("usuario");

                navtitle.setText(usuario);
                text_nameMenu.setText(nombre + " " + apellido);

                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
