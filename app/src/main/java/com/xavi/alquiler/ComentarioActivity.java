package com.xavi.alquiler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xavi.alquiler.Services.Token;
import com.xavi.alquiler.adapter.Adapter_comentario;
import com.xavi.alquiler.clienteHTTP.HttpConnection;
import com.xavi.alquiler.clienteHTTP.MethodType;
import com.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class ComentarioActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView lv;
    private EditText text_mensaje;
    private Button btn_enviar;
    private RecyclerView.LayoutManager layoutManager;
    private int id_casa, id_usuario;
    private Adapter_comentario adapter;
    private JSONArray arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = findViewById(R.id.list_chat);
        layoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(layoutManager);

        text_mensaje = findViewById(R.id.text_mensaje);
        btn_enviar = findViewById(R.id.btn_enviar);

        btn_enviar.setOnClickListener(this);

        if (getUsr_log() == null) {
            Intent intent = new Intent(ComentarioActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            try {
                id_usuario = (int) getUsr_log().get("id");
                id_casa = getIntent().getIntExtra("id_casa", 0);
                new Get_comentario(id_casa).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enviar:
                validate();
        }

    }

    private void validate() {
        Boolean validar = true;
        String mensaje = text_mensaje.getText().toString().trim();

        if (mensaje.isEmpty()) {
            validar = false;
        }
        if (validar) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("comentario", mensaje);
                arr.put(obj);
                adapter = new Adapter_comentario(ComentarioActivity.this, arr);
                lv.setAdapter(adapter);
                //adapter.addItem(obj,adapter.getItemCount()+1);
                //adapter.notifyDataSetChanged();
                text_mensaje.setText("");
                new EnviarComentario(mensaje).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }


    private class EnviarComentario extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private String mensaje;

        public EnviarComentario(String mensajes) {
            this.mensaje = mensajes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(ComentarioActivity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);

        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> param = new Hashtable<>();
            param.put("evento", "registrar_comentario");
            param.put("TokenAcceso", "servi12sis3");
            param.put("token", Token.currentToken);
            param.put("id_usuario", String.valueOf(id_usuario));
            param.put("id_casa", String.valueOf(id_casa));
            param.put("comentario", mensaje);
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, param));
            return respuesta;
        }

        @Override
        protected void onPostExecute(String usr) {
            super.onPostExecute(usr);
            progreso.dismiss();
            if (usr == null) {
                Toast.makeText(ComentarioActivity.this, "Error al conectarse con el servidor.", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(usr);
                    if (obj.getInt("estado") != 1) {
                        Toast.makeText(ComentarioActivity.this, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ComentarioActivity.this, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }


    private class Get_comentario extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private int id_casa;

        public Get_comentario(int id_casa) {
            this.id_casa = id_casa;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(ComentarioActivity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Hashtable<String, String> param = new Hashtable<>();
            param.put("evento", "getbyidCasa_comentario");
            param.put("TokenAcceso", "servi12sis3");
            param.put("token", Token.currentToken);
            param.put("id_casa", String.valueOf(id_casa));
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, param));
            return respuesta;
        }

        @Override
        protected void onPostExecute(String usr) {
            super.onPostExecute(usr);
            progreso.dismiss();
            if (usr == null) {
                Toast.makeText(ComentarioActivity.this, "Error al conectarse con el servidor.", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(usr);
                    if (obj.getInt("estado") != 1) {

                        Toast.makeText(ComentarioActivity.this, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ComentarioActivity.this, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
                        arr = new JSONArray(obj.getString("resp"));
                        adapter = new Adapter_comentario(ComentarioActivity.this, arr);
                        lv.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }
}
