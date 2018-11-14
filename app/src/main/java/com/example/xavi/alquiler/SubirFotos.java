package com.example.xavi.alquiler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.xavi.alquiler.Utiles.imagenModel;
import com.example.xavi.alquiler.adapter.Adapter_Images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubirFotos extends AppCompatActivity {

    private GridView gridView;
    private final int IMG_REQ = 1;
    private Adapter_Images images;
    private List<imagenModel> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_fotos);
        gridView = findViewById(R.id.grid);
        arr = new ArrayList<>();
        arr.add(new imagenModel(0, null));
        images = new Adapter_Images(SubirFotos.this, arr);
        gridView.setAdapter(images);


    }

    public void mostrar_subir_foto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ && resultCode == RESULT_OK) {
            Uri path = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                arr.add(new imagenModel(1, bm));
                images.notifyDataSetChanged();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
