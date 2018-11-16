package com.example.xavi.alquiler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.xavi.alquiler.Menu.DrawerAdapter;
import com.example.xavi.alquiler.Menu.DrawerItem;
import com.example.xavi.alquiler.Menu.SimpleItem;
import com.example.xavi.alquiler.Menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Principal extends AppCompatActivity implements TabLayout.OnTabSelectedListener, DrawerAdapter.OnItemSelectedListener {

    private SlidingRootNav slidingRootNav;
    private static final int POS_BUSCAR = 0;
    private static final int POS_PUBLICAR = 1;
    private static final int POS_FAVORITOS = 2;
    private static final int POS_CONSULTAS = 3;
    private static final int POS_PUBLICACIONES = 4;
    private static final int POS_PERFIL = 5;
    private static final int POS_SALIR = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NAVIGATION

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter drawadapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_BUSCAR).setChecked(true),
                createItemFor(POS_PUBLICAR),
                createItemFor(POS_FAVORITOS),
                createItemFor(POS_CONSULTAS),
                createItemFor(POS_PUBLICACIONES),
                createItemFor(POS_PERFIL),
                new SpaceItem(48),
                createItemFor(POS_SALIR)));
        drawadapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(drawadapter);

        drawadapter.setSelected(POS_BUSCAR);

        //BUTTON

        Spinner spin1 = findViewById(R.id.spn1);
        Spinner spin2 = findViewById(R.id.spn2);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item2 = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_SALIR) {
            finish();
        }
        if (position == POS_PUBLICAR) {
            Intent i = new Intent(getApplication(), PublicarActivity.class);
            startActivity(i);
        }
        if (position == POS_FAVORITOS) {
            Intent i = new Intent(getApplication(), Favoritos_Clientes.class);
            startActivity(i);
        }
        if (position == POS_CONSULTAS) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, " Click to download Colors Soda app from wwww. ");
            sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "G E E N  B O X");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (position == POS_PUBLICACIONES) {
            //   Intent i =new Intent(getApplication(), AdvertiseUs.class);
            //  startActivity(i);
        }
        if (position == POS_PERFIL) {
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

}
