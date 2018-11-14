package com.example.xavi.alquiler.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavi.alquiler.Datos_basicosActivity;
import com.example.xavi.alquiler.R;
import com.example.xavi.alquiler.SubirFotos;
import com.example.xavi.alquiler.Utiles.imagenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class Adapter_Images extends BaseAdapter {

    private List<imagenModel> array;
    private Context contexto;


    public Adapter_Images(Context contexto, List<imagenModel> lista ) {
        this.contexto = contexto;
        this.array = lista;

    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
            return array.get(i);

    }

    @Override
    public long getItemId(int i) { return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(contexto)
                    .inflate(R.layout.layout_item_image, viewGroup, false);

        ImageView imagen = view.findViewById(R.id.imageView);
            final imagenModel obj = array.get(i);
            if(obj.getTipo()==0){
                view = LayoutInflater.from(contexto)
                        .inflate(R.layout.layout_item_subir_image, viewGroup, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((SubirFotos)contexto).mostrar_subir_foto();
                    }
                });
            }else{
                final ImageView imageView=view.findViewById(R.id.imageView);
                imageView.setImageBitmap(obj.getBm());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(contexto,"",Toast.LENGTH_LONG).show();

//                    Intent intent = new Intent(contexto, Datos_basicosActivity.class);
//                    try {
//                        JSONObject og = new JS    ONObject();
//                        og.put("obj_us",obj_us);
//                        og.put("obj_nombre", obj.getString("nombre"));
//                        intent.putExtra("inten_propiedad",og.toString());
//                        contexto.startActivity(intent);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    }
                });
            }



        return view;
    }



}
