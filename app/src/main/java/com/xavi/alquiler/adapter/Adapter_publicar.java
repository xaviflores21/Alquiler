package com.xavi.alquiler.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

public class Adapter_publicar extends BaseAdapter {

    private JSONArray array;
    private Context contexto;
    private JSONObject obj_us;

    public Adapter_publicar(Context contexto, JSONArray lista, JSONObject obj) {
        this.contexto = contexto;
        this.array = lista;
        this.obj_us = obj;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
