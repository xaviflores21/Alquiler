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

public class Adapter_comentario extends RecyclerView.Adapter<Adapter_comentario.MyViewHolder> {

    private JSONArray objArray;

    private Context contexto;
    private JSONArray reservas;

    public Adapter_comentario() {
    }

    public Adapter_comentario(Context contexto, JSONArray lista) {
        this.contexto = contexto;
        this.objArray = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comentario, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {
        try {
            final JSONObject obj = objArray.getJSONObject(i);
            holder.text_chat.setText(obj.getString("precio"));

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

        public TextView text_chat;

        public MyViewHolder(View v) {
            super(v);
            text_chat = v.findViewById(R.id.text_chat);
        }
    }

}