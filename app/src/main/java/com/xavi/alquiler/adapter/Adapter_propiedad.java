package com.xavi.alquiler.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xavi.alquiler.Datos_basicosActivity;
import com.xavi.alquiler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Adapter_propiedad extends BaseAdapter {

    private JSONArray array;
    private Context contexto;
    private JSONObject obj_us;

    public Adapter_propiedad(Context contexto, JSONArray lista, JSONObject obj) {
        this.contexto = contexto;
        this.array = lista;
        this.obj_us = obj;
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return array.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(contexto)
                    .inflate(R.layout.layout_item_propiedad, viewGroup, false);
        }
        TextView text_fecha = view.findViewById(R.id.text_propiedad);
        try {
            final JSONObject obj = array.getJSONObject(i);
            text_fecha.setText(obj.getString("nombre"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contexto, Datos_basicosActivity.class);
                    try {
                        JSONObject og = new JSONObject();
                        og.put("obj_us", obj_us);
                        og.put("obj_nombre", obj.getString("nombre"));
                        intent.putExtra("inten_propiedad", og.toString());
                        contexto.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


}
