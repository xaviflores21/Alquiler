package com.xavi.alquiler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xavi.alquiler.Datos_basicosActivity;
import com.xavi.alquiler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_propiedad;
    public LinearLayout liner_propiedad;
    private Context context;
    private JSONArray jsonarray;
    private JSONObject obj_us;

    public MyViewHolder(View v, JSONArray array, JSONObject objeto) {
        super(v);
        this.context = v.getContext();
        this.jsonarray = array;
        this.obj_us = objeto;
        text_propiedad = v.findViewById(R.id.text_propiedad);
        liner_propiedad = v.findViewById(R.id.liner_propiedad);
        liner_propiedad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_propiedad:
                acceder();
                break;
        }
    }

    private void acceder() {
        int position = getAdapterPosition();
        try {
            final JSONObject obj = jsonarray.getJSONObject(position);
            Intent intent = new Intent(context, Datos_basicosActivity.class);
            obj_us.put("id_propiedad", obj.getInt("id"));
            intent.putExtra("obj", obj.toString());
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}