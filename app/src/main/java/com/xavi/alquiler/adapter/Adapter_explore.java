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
    public void onBindViewHolder(MyViewHolder holder, int i) {
        try {
            final JSONObject obj = objArray.getJSONObject(i);
            //Boolean like = obj.getBoolean("like");
            JSONArray array = obj.getJSONArray("arrCostos");
            JSONObject objtemp;
            String tipos = "";
            if (obj.getInt("tipo_public") == 1) {
                holder.tipopublic.setText("OFERTO: " + obj.getJSONObject("tipo_propiedad").getString("nombre"));
            } else if (obj.getInt("tipo_public") == 2) {
                holder.tipopublic.setText("BUSCO: " + obj.getJSONObject("tipo_propiedad").getString("nombre"));
            }
            for (int j = 0; j < array.length(); j++) {
                objtemp = array.getJSONObject(j);
                //  tipos+=objtemp.getString("nombre") + " ";
                switch (objtemp.getInt("tipo")) {
                    case 1:
                        holder.tx_venta.setText(objtemp.getString("nombre"));
                        holder.tx_pecio_venta.setText(objtemp.getDouble("costo") + " $");
                        break;
                    case 2:
                        holder.tx_alquiler.setText(objtemp.getString("nombre"));
                        holder.tx_precio_alquiler.setText(objtemp.getDouble("costo") + " $");
                        break;
                    case 3:
                        holder.tx_anticretico.setText(objtemp.getString("nombre"));
                        holder.tx_pecio_anticretico.setText(objtemp.getDouble("costo") + " $");
                        break;
                }
            }
            if (obj.has("cant_banhos")) {
                holder.text_baños.setText(obj.getInt("cant_banhos") + " Baños");
            }

            if (obj.has("cant_dormitorios")) {
                holder.text_dormitorio.setText(obj.getInt("cant_dormitorios") + " Dormitorios");
            }
            if (obj.has("metros2")) {
                holder.text_metros.setText(obj.getInt("metros2") + " M²");
            }


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

        public ImageView imag_explorer;
        public TextView text_precio1;
        public TextView text_precio2;
        public TextView text_precio3;
        public TextView text_dormitorio;
        public TextView text_baños;
        public TextView text_metros;

        //costos
        public TextView tx_venta;
        public TextView tx_pecio_venta;
        public TextView tx_alquiler;
        public TextView tx_precio_alquiler;
        public TextView tx_anticretico;
        public TextView tx_pecio_anticretico;
        //

        public TextView tipopublic;
        public LinearLayout liner_1;
        public LinearLayout liner_2;
        public LinearLayout liner_3;

        public LinearLayout liner_favorito;
        public LinearLayout liner_comentario;

        public MyViewHolder(View v) {
            super(v);
            imag_explorer = v.findViewById(R.id.imag_explorer);
            text_precio1 = v.findViewById(R.id.text_precio1);
            text_precio2 = v.findViewById(R.id.text_precio2);
            text_precio3 = v.findViewById(R.id.text_precio3);
            text_dormitorio = v.findViewById(R.id.text_dormitorio);
            text_baños = v.findViewById(R.id.text_baños);
            text_metros = v.findViewById(R.id.text_metros);
            liner_1 = v.findViewById(R.id.liner_1);
            liner_2 = v.findViewById(R.id.liner_2);
            liner_3 = v.findViewById(R.id.liner_3);
            liner_favorito = v.findViewById(R.id.liner_favorito);
            liner_comentario = v.findViewById(R.id.liner_comentario);

            tx_venta = v.findViewById(R.id.tx_venta);
            tx_pecio_venta = v.findViewById(R.id.tx_pecio_venta);
            tx_alquiler = v.findViewById(R.id.tx_alquiler);
            tx_precio_alquiler = v.findViewById(R.id.tx_precio_alquiler);
            tx_anticretico = v.findViewById(R.id.tx_anticretico);
            tx_pecio_anticretico = v.findViewById(R.id.tx_pecio_anticretico);
            tipopublic = v.findViewById(R.id.tipopublic);
        }
    }

}