package com.example.xavi.alquiler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavi.alquiler.clienteHTTP.HttpConnection;
import com.example.xavi.alquiler.clienteHTTP.MethodType;
import com.example.xavi.alquiler.clienteHTTP.StandarRequestConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private Button btn_registrar, btn_login;
    private EditText text_usuario, text_contraseña;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_registrar = findViewById(R.id.btn_regitrar);
        btn_login.setOnClickListener(this);
        btn_registrar.setOnClickListener(this);

        text_usuario = findViewById(R.id.text_usuario);
        text_contraseña = findViewById(R.id.text_contraseña);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                validate();
                break;
            case R.id.btn_regitrar:
                Intent inte = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(inte);
                break;
        }
    }

    private void validate() {
        Boolean validar = true;
        String usuario = text_usuario.getText().toString().trim();
        String contraseña = text_contraseña.getText().toString().trim();

        if (usuario.isEmpty()) {
            text_usuario.setError("Campo Obligatorio");
            validar = false;
        }
        if (contraseña.isEmpty()) {
            text_contraseña.setError("Campo Obligatorio");
            validar = false;
        }
        if (validar) {
            new Login(usuario, contraseña).execute();
        } else {
            return;
        }
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    private class Login extends AsyncTask<Void, String, String> {

        private ProgressDialog progreso;
        private String usuario, contraseña;

        public Login(String usuario, String comtraseña) {
            this.usuario = usuario;
            this.contraseña = comtraseña;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(LoginActivity.this);
            progreso.setIndeterminate(true);
            progreso.setTitle("Esperando Respuesta");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            Hashtable<String, String> param = new Hashtable<>();
            param.put("evento", "login");
            param.put("TokenAcceso", "servi12sis3");
            param.put("usuario", usuario);
            param.put("pass", md5(contraseña));
            String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(getString(R.string.url_servlet_admin), MethodType.POST, param));
            return respuesta;
        }

        @Override
        protected void onPostExecute(String usr) {
            super.onPostExecute(usr);
            progreso.dismiss();
            if (usr == null) {
                Toast.makeText(LoginActivity.this, "Error al conectarse con el servidor.", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(usr);
                    if (obj.getInt("estado") != 1) {
                        Toast.makeText(LoginActivity.this, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, obj.getString("mensaje"), Toast.LENGTH_SHORT).show();
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

