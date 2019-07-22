package com.example.lecturas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import com.example.lecturas.fragments.homeFragment;
import com.example.lecturas.fragments.importarFragment;
import com.example.lecturas.fragments.lecturafragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class navigationDrawActiviy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    //variable para guardar la opcion selccionada en el menu
    public static int opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_draw_activiy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // capturar la opcion seleccionada en el menu segun el fragment expuesto
        FragmentManager fm = getSupportFragmentManager();
        switch (opcion){
            case 1: fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).commit();break;
            case 2: fm.beginTransaction().replace(R.id.contenedor, new lecturafragment()).addToBackStack(null).commit();break;
            case 3: fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).addToBackStack(null).commit();break;
            case 4: fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).addToBackStack(null).commit();break;
            case 5: fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).addToBackStack(null).commit();break;
            case 6: fm.beginTransaction().replace(R.id.contenedor, new importarFragment()).addToBackStack(null).commit();break;
            case 7: fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).addToBackStack(null).commit();break;
            default:  fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).addToBackStack(null).commit();break;
        }

    // ################ eventos action buton del menu button
        //#################evento 1 lectur nuevas
        final FloatingActionsMenu menuBotones = (FloatingActionsMenu) findViewById(R.id.grupoFab);
        final FloatingActionButton fab1 = findViewById(R.id.fab1);
        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        final FloatingActionButton fab3 = findViewById(R.id.fab3);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lectura Nueva", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.contenedor, new lecturafragment()).addToBackStack(null).commit();
                menuBotones.collapse();
            }
        });

        //############# evento 2 lista de lecturas

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lista de lecturas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                menuBotones.collapse();
            }
        });

        //############# evento 3 editar lectura

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lista de lecturas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                menuBotones.collapse();
            }
        });
    //############################################################################

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_draw_activiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // enlace entre el fragment y el activity
        FragmentManager fm = getSupportFragmentManager();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            fm.beginTransaction().replace(R.id.contenedor, new lecturafragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_slideshow) {
//            fm.beginTransaction().replace(R.id.contenedor, new listaFragment()).commit();
        } else if (id == R.id.nav_tools) {
//            fm.beginTransaction().replace(R.id.contenedor, new editarFragment()).commit();
        } else if (id == R.id.nav_share) {
//            fm.beginTransaction().replace(R.id.contenedor, new medidoresFragment()).commit();
        } else if (id == R.id.importar) {
            fm.beginTransaction().replace(R.id.contenedor, new importarFragment()).addToBackStack(null).commit();
        } else if (id == R.id.exportar) {
//            fm.beginTransaction().replace(R.id.contenedor, new exportarFragment()).commit();
        } else if (id == R.id.nav_send) {
            Intent salida = new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
            finish(); // La cerramos.
            System.exit(0);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
