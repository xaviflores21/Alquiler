package com.xavi.alquiler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xavi.alquiler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Adapter_explore extends RecyclerView.Adapter<Adapter_explore.MyViewHolder> {

    private JSONArray objArray;

    private Context contexto;

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
            JSONArray array = obj.getJSONArray("arrCostos");
            JSONObject objtemp;
            String tipos="";
            for (int j = 0; j <obj.length() ; j++) {
                objtemp=array.getJSONObject(i);
                tipos+=objtemp.getString("nombre");
            }
            holder.text_baños.setText(obj.getInt("baños")+" Baños");
            holder.text_dormitorio.setText(obj.getInt("cuartos ") + "Cuartos");
            holder.text_metros.setText(obj.getInt("metros2") + "Metros 2");

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
        public TextView text_precio1;
        public TextView text_precio2;
        public TextView text_precio3;
        public TextView text_dormitorio;
        public TextView text_baños;
        public TextView text_metros;
        public LinearLayout liner_1;
        public LinearLayout liner_2;
        public LinearLayout liner_3;

        public LinearLayout liner_favorito;
        public LinearLayout liner_comentario;

        public MyViewHolder(View v) {
            super(v);
            text_propiedad = v.findViewById(R.id.text_propiedad);
            text_precio1 = v.findViewById(R.id.text_precio1);
            text_precio2 = v.findViewById(R.id.text_precio2);
            text_precio3 = v.findViewById(R.id.text_precio3);
            text_dormitorio= v.findViewById(R.id.text_dormitorio);
            text_baños= v.findViewById(R.id.text_baños);
            text_metros= v.findViewById(R.id.text_metros);
            liner_1 = v.findViewById(R.id.liner_1);
            liner_2 = v.findViewById(R.id.liner_2);
            liner_3 = v.findViewById(R.id.liner_3);
            liner_favorito= v.findViewById(R.id.liner_favorito);
            liner_comentario= v.findViewById(R.id.liner_comentario);
        }
    }

}