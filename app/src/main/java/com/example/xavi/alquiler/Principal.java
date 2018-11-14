package com.example.xavi.alquiler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
    private TextView text_casa;
    private TextView text_alquiler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        text_casa = findViewById(R.id.text_casa);
        text_alquiler = findViewById(R.id.text_alquiler);
        Spinner spin1 = findViewById(R.id.spn1);
        Spinner spin2 = findViewById(R.id.spn2);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                text_casa.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                text_alquiler.setText("");
            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item2 = adapterView.getItemAtPosition(i).toString();
                text_alquiler.setText(item2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                text_alquiler.setText("");
            }
        });
    }


}
