package com.xavi.alquiler;

import android.support.v4.app.Fragment;
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

import com.xavi.alquiler.R;
import com.xavi.alquiler.Menu.DrawerAdapter;
import com.xavi.alquiler.Menu.DrawerItem;
import com.xavi.alquiler.Menu.SimpleItem;
import com.xavi.alquiler.Menu.SpaceItem;
import com.xavi.alquiler.Utiles.Constant;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Principal extends AppCompatActivity implements TabLayout.OnTabSelectedListener, DrawerAdapter.OnItemSelectedListener {

    private SlidingRootNav slidingRootNav;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    Fragment fragment_home = null;
    Fragment fragment_buscar = null;
    Fragment fragment_publicar = null;
    Fragment fragment_favoritos = null;
    Fragment fragment_novedades = null;
    Fragment fragment_consultar = null;
    Fragment fragment_publicaciones = null;
    Fragment fragment_perfil = null;

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
                createItemFor(Constant.POS_HOME).setChecked(true),
                createItemFor(Constant.POS_BUSCAR),
                createItemFor(Constant.POS_PUBLICAR),
                createItemFor(Constant.POS_FAVORITOS),
                createItemFor(Constant.POS_NOVEDADES),
                createItemFor(Constant.POS_CONSULTAS),
                createItemFor(Constant.POS_PUBLICACIONES),
                createItemFor(Constant.POS_PERFIL),
                new SpaceItem(10),
                createItemFor(Constant.POS_SALIR)));
        drawadapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(drawadapter);
        drawadapter.setSelected(Constant.POS_HOME);

        fragment_home = new HomeActivity();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_home).commit();

    }


    @Override
    public void onItemSelected(int position) {
        if (position == Constant.POS_HOME) {
            fragment_home = new HomeActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_home).commit();
        }
        if (position == Constant.POS_BUSCAR) {
            fragment_buscar = new BuscarFrament();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_buscar).commit();
        }
        if (position == Constant.POS_PUBLICAR) {
            fragment_publicar = new PublicarActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_publicar).commit();
        }
        if (position == Constant.POS_FAVORITOS) {
            fragment_favoritos = new Favoritos();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_favoritos).commit();
        }
        if (position == Constant.POS_NOVEDADES) {
            fragment_novedades = new ExploreActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_novedades).commit();
        }
        if (position == Constant.POS_CONSULTAS) {
            fragment_consultar = new ComentarioActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_consultar).commit();
        }
        if (position == Constant.POS_PUBLICACIONES) {
            //fragment_publicaciones = new PublicarActivity();
            //getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_publicaciones).commit();
        }
        if (position == Constant.POS_PERFIL) {
            fragment_perfil = new PerfilUsuarioActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_perfil).commit();
        }
        if (position == Constant.POS_SALIR) {
            finish();
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
