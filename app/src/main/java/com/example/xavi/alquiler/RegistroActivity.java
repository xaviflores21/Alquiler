package com.example.xavi.alquiler;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xavi.alquiler.clienteHTTP.HttpConnection;
import com.example.xavi.alquiler.clienteHTTP.MethodType;
import com.example.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONException;
import org.json.JSONObject;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Hashtable;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_nombre;
    private EditText text_apellidos;
    private EditText text_fechaNacimiento;
    private EditText text_usuario;
    private EditText text_contraseña;
    private EditText text_carnet;
    private Button btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        text_nombre = findViewById(R.id.text_nombre);
        text_apellidos = findViewById(R.id.text_apellidos);
        text_fechaNacimiento = findViewById(R.id.text_fechaNacimiento);
        text_usuario = findViewById(R.id.text_usuario);
        text_contraseña = findViewById(R.id.text_contraseña);
        text_carnet = findViewById(R.id.text_carnet);
        btn_registro = findViewById(R.id.btn_registro);

        btn_registro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_registro:
                validate();
                break;
        }
    }


    private void validate() {
        Boolean validar = true;
        String nombre = text_nombre.getText().toString().trim();
        String apellido = text_apellidos.getText().toString().trim();
        String fecha = text_fechaNacimiento.getText().toString().trim();
        String usuario = text_usuario.getText().toString().trim();
        String contraseña = text_contraseña.getText().toString().trim();
        String carnet = text_carnet.getText().toString().trim();

        if (nombre.isEmpty())
        {
            text_nombre.setError("Campo Obligatorio");
            validar = false;
        }
        if (apellido.isEmpty())
        {
            text_apellidos.setError("Campo Obligatorio");
            validar = false;
        }
        if (fecha.isEmpty())
        {
            text_fechaNacimiento.setError("Campo Obligatorio");
            validar = false;
        }
        if (usuario.isEmpty())
        {
            text_usuario.setError("Campo Obligatorio");
            validar = false;
        }
        if (contraseña.isEmpty())
        {
            text_contraseña.setError("Campo Obligatorio");
            validar = false;
        }
        if (carnet.isEmpty())
        {
            text_carnet.setError("Campo Obligatorio");
            validar = false;
        }

        if (validar) {
            new Registrar(nombre,apellido,fecha,usuario,contraseña,carnet).execute();
        }else {
            return;
        }
    }

    private void ShowDatapinckerDialog(){
        final java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(Calendar.YEAR , 1900);
        c.set(Calendar.MONTH , 0);
        c.set(Calendar.DAY_OF_MONTH , 0);
        int año = c.get(java.util.Calendar.YEAR);
        int mes = c.get(java.util.Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePickerDialog = new DatePickerDialog
                (this ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fecha;
                        if ((month+1)<10) {
                            fecha = year + "-0" + (month+1);
                        }else{
                            fecha = year + "-" + (month+1);
                        }
                        if(dayOfMonth<10){
                            fecha += "-0"+ dayOfMonth;
                        }else{
                            fecha += "-" + dayOfMonth;
                        }
                        text_fechaNacimiento.setText(fecha);
                        text_fechaNacimiento.setError(null);
                    }
                }, dia, mes , año);
        datePickerDialog.show();
    }

    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }


    private class Registrar extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private String  nombre , apellidos , fecha ,  usuario ,  contraseña , carnet;

        public Registrar( String nombre , String apellidos , String fecha ,String usuario , String comtraseña
                , String carnet ) {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.fecha = fecha;
            this.usuario = usuario;
            this.contraseña = comtraseña;
            this.carnet = carnet;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(RegistroActivity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            Hashtable<String,String> param = new Hashtable<>();
            param.put("evento","registrar_usuario");
            param.put("TokenAcceso","servi12sis3");
            param.put("nombre", nombre);
            param.put("apellidos",apellidos);
            param.put("fecha_nac",fecha);
            param.put("ci", carnet);
            param.put("usuario",usuario);
            param.put("contrasena", md5(contraseña));
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, param));
            return respuesta;

        }

        @Override
        protected void onPostExecute(String usr) {
            super.onPostExecute(usr);
            progreso.dismiss();
            if(usr==null){
                Toast.makeText(RegistroActivity.this,"Error al conectarse con el servidor.",Toast.LENGTH_SHORT).show();
            }else{
                JSONObject obj = null;
                try {
                    obj = new JSONObject(usr);
                if (obj.getInt("estado") != 1) {
                    Toast.makeText(RegistroActivity.this,obj.getString("mensaje") ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroActivity.this,obj.getString("mensaje") ,Toast.LENGTH_SHORT).show();
                    Intent inte = new Intent(RegistroActivity.this,LoginActivity.class);
                    startActivity(inte);
                    finish();
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
