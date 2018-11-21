package com.xavi.alquiler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xavi.alquiler.Datos_basicosActivity;
import com.xavi.alquiler.Listener.ProductoAdapterClik;
import com.xavi.alquiler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Adapter_propiedad extends RecyclerView.Adapter<Adapter_propiedad.MyViewHolder> {

    private JSONArray array;
    private Context contexto;
    private JSONObject obj_us;
    private ProductoAdapterClik listener;

    public Adapter_propiedad(Context contexto, JSONArray lista, JSONObject obj , ProductoAdapterClik listener) {
        this.contexto = contexto;
        this.array = lista;
        this.obj_us = obj;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_propiedad, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

               @Override
            public void onBindViewHolder(final MyViewHolder holder, int i) {
                try {
                    final JSONObject obj = array.getJSONObject(i);
                    holder.text_propiedad.setText(obj.getString("nombre"));
                    holder.itemView.setTag(obj.getInt("id"));
                    //holder.text_propiedad.setTag(obj.getInt("id"));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onClick((int)view.getTag(),view);
                        }
                    });


                } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_propiedad;
        public LinearLayout liner_propiedad;

        public MyViewHolder(View v) {
            super(v);
            text_propiedad = v.findViewById(R.id.text_propiedad);
            liner_propiedad = v.findViewById(R.id.liner_propiedad);

        }
    }
}
