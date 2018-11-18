package com.xavi.alquiler;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xavi.alquiler.R;

import org.json.JSONObject;

public class ExploreActivity extends Fragment {

    private RecyclerView lv;
    private JSONObject obj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_explore, container, false);

        lv = view.findViewById(R.id.list_explore);
        return view;

    }


}
