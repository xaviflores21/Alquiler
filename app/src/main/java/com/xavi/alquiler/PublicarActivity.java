package com.xavi.alquiler;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.yarolegovich.slidingrootnav.SlidingRootNav;


public class PublicarActivity extends Fragment implements View.OnClickListener {

    private static final String VENTA = "1";
    private static final String ALQUILER = "2";
    private static final String ANTICRETICO = "3";

    private CheckBox chek_casas;
    private CheckBox chek_alquiler;
    private CheckBox chek_anticretico;
    private Button btn_continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_publicar, container, false);

        chek_casas = view.findViewById(R.id.check_casas);
        chek_alquiler = view.findViewById(R.id.check_alquiler);
        chek_anticretico = view.findViewById(R.id.check_anticretico);

        btn_continuar = view.findViewById(R.id.btn_continuar);

        return view;
    }

    @Override
    public void onClick(View view) {

    }


    /*@Override
    public void onClick(View view) {
        JSONObject obj = new JSONObject();
        switch (view.getId()) {
            case R.id.btn_venta:
                try {
                    Intent intent = new Intent(PublicarActivity.this, SubirFotos.class);
                    obj.put("tipo", VENTA);
                    intent.putExtra("obj_tipo_operacion", obj.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_alquiler:
                try {
                    Intent intent2 = new Intent(PublicarActivity.this, PropiedadActivity.class);
                    obj.put("tipo", ALQUILER);
                    intent2.putExtra("obj_tipo_operacion", obj.toString());
                    startActivity(intent2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_anticretico:
                try {
                    Intent intent3 = new Intent(PublicarActivity.this, PropiedadActivity.class);
                    obj.put("tipo", ANTICRETICO);
                    intent3.putExtra("obj_tipo_operacion", obj.toString());
                    startActivity(intent3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onItemSelected(int position) {
        if (position == Constant.POS_SALIR) {
            finish();
        }
        if (position == Constant.POS_BUSCAR) {
            Intent i = new Intent(getApplication(), Principal.class);
            startActivity(i);
        }
        if (position == Constant.POS_FAVORITOS) {
            Intent i = new Intent(getApplication(), Favoritos.class);
            startActivity(i);
        }
        if (position == Constant.POS_COMPARTIR) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, " Click para descargar la App ServiCasas de wwww.servicasas.com.bo ");
            sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "S E R V I C A S A S");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (position == Constant.POS_CONSULTAS) {
            //   Intent i =new Intent(getApplication(), AdvertiseUs.class);
            //  startActivity(i);
        }
        if (position == Constant.POS_PUBLICACIONES) {
            //   Intent i =new Intent(getApplication(), AdvertiseUs.class);
            //  startActivity(i);
        }
        if (position == Constant.POS_PERFIL) {
            //   Intent i =new Intent(getApplication(), AdvertiseUs.class);
            //  startActivity(i);
        }
        slidingRootNav.closeMenu();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorSecondary))
                .withSelectedIconTint(color(R.color.navfun))
                .withSelectedTextTint(color(R.color.navfun));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }*/

}
