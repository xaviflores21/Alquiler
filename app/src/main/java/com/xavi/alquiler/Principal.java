package com.xavi.alquiler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.xavi.alquiler.R;
import com.xavi.alquiler.Menu.DrawerAdapter;
import com.xavi.alquiler.Menu.DrawerItem;
import com.xavi.alquiler.Menu.SimpleItem;
import com.xavi.alquiler.Menu.SpaceItem;
import com.xavi.alquiler.Utiles.Constant;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Principal extends AppCompatActivity implements TabLayout.OnTabSelectedListener, DrawerAdapter.OnItemSelectedListener, View.OnClickListener {

    private SlidingRootNav slidingRootNav;
    private DrawerAdapter drawadapter;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private TextView txtNombre;
    private TextView text_nameMenu;

    Fragment fragment_home = null;
    Fragment fragment_buscar = null;
    Fragment fragment_publicar = null;
    Fragment fragment_favoritos = null;
    Fragment fragment_perfil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbarPricipal);
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

        crearListaMenus();

        if (getUsr_log() != null) {
            txtNombre = slidingRootNav.getLayout().findViewById(R.id.navtitle);
            text_nameMenu = slidingRootNav.getLayout().findViewById(R.id.text_nameMenu);
            try {
                String[] array = getResources().getStringArray(R.array.ld_activityScreenTitles);
                array[5] = "Cerrar Sesión";
                screenTitles = array;
                crearListaMenus();

                txtNombre.setText(getUsr_log().getString("usuario"));
                text_nameMenu.setText(getUsr_log().getString("nombre") + " " + getUsr_log().getString("apellidos"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            text_nameMenu = findViewById(R.id.text_nameMenu);
            txtNombre = findViewById(R.id.navtitle);
            text_nameMenu.setOnClickListener(this);
            txtNombre.setOnClickListener(this);
        }

        fragment_home = new ExploreActivity();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_home).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_nameMenu:
                Intent inten = new Intent(Principal.this, LoginActivity.class);
                startActivity(inten);
                break;
            case R.id.navtitle:
                Intent inten1 = new Intent(Principal.this, LoginActivity.class);
                startActivity(inten1);
                break;
        }
    }

    public JSONObject getUsr_log() {
        SharedPreferences preferencias = getSharedPreferences("myPref", MODE_PRIVATE);
        String usr = preferencias.getString("usr_log", "");
        if (usr.length() <= 0) {
            return null;
        } else {
            try {
                JSONObject usr_log = new JSONObject(usr);
                return usr_log;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private void crearListaMenus() {
        drawadapter = new DrawerAdapter(Arrays.asList(
                createItemFor(Constant.POS_HOME).setChecked(true),
                //createItemFor(Constant.POS_BUSCAR),
                createItemFor(Constant.POS_PUBLICAR),
                createItemFor(Constant.POS_FAVORITOS),
                createItemFor(Constant.POS_PERFIL),
                new SpaceItem(10),
                createItemFor(Constant.POS_SALIR)));
        drawadapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(drawadapter);
        drawadapter.setSelected(Constant.POS_HOME);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == Constant.POS_HOME) {
            fragment_home = new ExploreActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_home).commit();
        }
        /*if (position == Constant.POS_BUSCAR) {
            //fragment_buscar = new BuscarFrament();
            //getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_buscar).commit();
            Intent intent = new Intent(Principal.this, RegistroBusquedaActivity.class);
            startActivity(intent);
        }*/
        if (position == Constant.POS_PUBLICAR) {
            Intent intent = new Intent(Principal.this, tipo_publicacion_Activity.class);
            startActivity(intent);
        }
        if (position == Constant.POS_FAVORITOS) {
            fragment_favoritos = new Favoritos();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment_favoritos).commit();
        }
        if (position == Constant.POS_PERFIL) {
            Intent intent = new Intent(Principal.this, PerfilUsuarioActivity.class);
            startActivity(intent);
        }
        if (position == Constant.POS_SALIR) {
            SharedPreferences preferences = getSharedPreferences("myPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            //editor.putString("usr_log", obj.getString("resp"));
            editor.remove("usr_log");
            editor.commit();
            //finish();
            Intent inten = new Intent(Principal.this, SplashActivity.class);
            startActivity(inten);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Qué está buscando?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Principal.this, "", Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //textView.setText(newText);
                return true;
            }
        });
        //check http://stackoverflow.com/questions/11085308/changing-the-background-drawable-of-the-searchview-widget
        View searchPlate = (View) searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundResource(R.mipmap.textfield_custom);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //action(R.string.action_settings);
                return true;
            case R.id.action_help:
                //action(R.string.action_help);
                return true;
            case R.id.action_about:
                //action(R.string.action_about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void action(int resid) {
        Toast.makeText(this, getText(resid), Toast.LENGTH_SHORT).show();
    }

}
