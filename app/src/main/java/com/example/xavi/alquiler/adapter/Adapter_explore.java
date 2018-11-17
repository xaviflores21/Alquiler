package com.example.xavi.alquiler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.xavi.alquiler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Adapter_explore extends RecyclerView.Adapter<Adapter_explore.MyViewHolder> {

    private JSONArray objArray;

    private Context contexto;
    private JSONArray reservas;

    public Adapter_explore() {
    }

    public Adapter_explore(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.objArray = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_explore, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {
        try {
            final JSONObject obj = objArray.getJSONObject(i);
            Boolean like = obj.getBoolean("like");
            holder.text_precio.setText(obj.getString("precio"));
            holder.text_descripcion.setText(obj.getString("descripcion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return objArray.length();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView text_propiedad;
        public TextView text_precio;
        public TextView text_descripcion;
        public LinearLayout liner_favorito;
        public LinearLayout liner_comentario;

        public MyViewHolder(View v) {
            super(v);
            text_propiedad = v.findViewById(R.id.text_propiedad);
            text_precio= v.findViewById(R.id.text_precio);
            text_descripcion= v.findViewById(R.id.text_descripcion);
            liner_favorito= v.findViewById(R.id.liner_favorito);
            liner_comentario= v.findViewById(R.id.liner_comentario);
        }
    }


}