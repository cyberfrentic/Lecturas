package com.example.lecturas;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lecturas.fragments.homeFragment;
import com.example.lecturas.fragments.importarFragment;
import com.example.lecturas.fragments.lecturafragment;
import com.example.lecturas.fragments.listaFragment;
import com.example.lecturas.fragments.preCedFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class navigationDrawActiviy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Response.Listener<JSONObject>, Response.ErrorListener {

    //variable para guardar la opcion selccionada en el menu
    public static int opcion;
    private Calendar fecha = Calendar.getInstance();
    private int mes = fecha.get(Calendar.MONTH) + 1;
    final private int REQUEST_CODE_ASK_PERMISSION=111;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_draw_activiy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        solicitarPermisos();

        // capturar la opcion seleccionada en el menu segun el fragment expuesto
        FragmentManager fm = getSupportFragmentManager();
        switch (opcion){
            case 1: fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).commit();break;
            case 2: fm.beginTransaction().replace(R.id.contenedor, new lecturafragment()).addToBackStack(null).commit();break;
            case 3: fm.beginTransaction().replace(R.id.contenedor, new listaFragment()).addToBackStack(null).commit();break;
            case 4: fm.beginTransaction().replace(R.id.contenedor, new preCedFragment()).addToBackStack(null).commit();break;
            case 5: fm.beginTransaction().replace(R.id.contenedor, new importarFragment()).addToBackStack(null).commit();break;
            default:  fm.beginTransaction().replace(R.id.contenedor, new homeFragment()).addToBackStack(null).commit();break;
        }

    // ################ eventos action buton del menu button
        //#################evento 1 lectur nuevas
        final FloatingActionsMenu menuBotones = (FloatingActionsMenu) findViewById(R.id.grupoFab);
        final FloatingActionButton fab1 = findViewById(R.id.fab1);
        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        final FloatingActionButton fab3 = findViewById(R.id.fab3);
        if (mes == 1 || mes==5 || mes== 8) {
            fab3.setVisibility(View.VISIBLE);
        }

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
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.contenedor, new listaFragment()).addToBackStack(null).commit();
                menuBotones.collapse();


            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cedula de Verificación de Predios", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.contenedor, new preCedFragment()).addToBackStack(null).commit();
                menuBotones.collapse();


            }
        });
    //############################################################################

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //##############################################
        // Aqui estás el navigator para ocultar opciones
        NavigationView navigationView = findViewById(R.id.nav_view);
        // #############################################

        String usuario = getIntent().getStringExtra("usuario");
        if (!usuario.equals("admin")){
            navigationView.getMenu().findItem(R.id.importar).setVisible(false);
            navigationView.getMenu().findItem(R.id.exportar).setVisible(false);
        }

        if (mes == 1 || mes==5 || mes== 9) {
            navigationView.getMenu().findItem(R.id.nav_share).setVisible(true);
        }else{
            navigationView.getMenu().findItem(R.id.nav_share).setVisible(false);
        }
        navigationView.getMenu().findItem(R.id.nav_tools).setVisible(false);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void solicitarPermisos() {
        int permisoRES = ActivityCompat.checkSelfPermission(navigationDrawActiviy.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoWES = ActivityCompat.checkSelfPermission(navigationDrawActiviy.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoCam = ActivityCompat.checkSelfPermission(navigationDrawActiviy.this, Manifest.permission.CAMERA);
        int permisoLocation = ActivityCompat.checkSelfPermission(navigationDrawActiviy.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int pemisoInternet = ActivityCompat.checkSelfPermission(navigationDrawActiviy.this, Manifest.permission.INTERNET);
        if(permisoRES != PackageManager.PERMISSION_GRANTED || permisoWES != PackageManager.PERMISSION_GRANTED || permisoCam != PackageManager.PERMISSION_GRANTED || permisoLocation != PackageManager.PERMISSION_GRANTED || pemisoInternet != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, REQUEST_CODE_ASK_PERMISSION);
            }
        }
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
            fm.beginTransaction().replace(R.id.contenedor, new listaFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_tools) {
//            fm.beginTransaction().replace(R.id.contenedor, new editarFragment()).commit();
        } else if (id == R.id.nav_share) {
           fm.beginTransaction().replace(R.id.contenedor, new preCedFragment()).addToBackStack(null).commit();
        } else if (id == R.id.importar) {
            fm.beginTransaction().replace(R.id.contenedor, new importarFragment()).addToBackStack(null).commit();
        } else if (id == R.id.exportar) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            // Setting Dialog Title
            alertDialog.setTitle("Sistema Lecturas");
            // Setting Dialog Message
            alertDialog.setMessage("Se procedera a Exportar los datos de las Lecturas tomadas!");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.alerta);
            // Setting OK Button
            alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String fileName = "ListaLecturas.txt";
                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                    Cursor fila = BaseDeDatos.rawQuery("select contrato, lectura, anomalia, longitud, latitud, imgMedidor, imgpred from lectura",null);
                    try{

                        String nombre_completo = Environment.getExternalStorageDirectory() + File.separator +fileName;
                        File outputFile = new File(nombre_completo);
                        if (outputFile.exists()){
                            outputFile.delete();
                        }
                        //OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nombre_completo, Activity.MODE_PRIVATE));

                        /*------------------------------*/
                        FileOutputStream fOut = new FileOutputStream(outputFile, true);
                        BufferedWriter archivo = new BufferedWriter(new OutputStreamWriter(fOut));
                        //OutputStreamWriter archivo = new OutputStreamWriter(fOut);
                        /*------------------------------*/
                        while (fila.moveToNext()) {
                            archivo.write(fila.getString(0) + ", " + fila.getString(1)  + ", " + fila.getString(2)+ ", " + fila.getString(3)+ ", " + fila.getString(4)+ ", " + fila.getString(5)+ ", " + fila.getString(6));
                            archivo.newLine();
                        }
                        archivo.flush();
                        archivo.close();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
            });
            alertDialog.setButton2("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            // Showing Alert Message
            alertDialog.show();

            //#################################################################
            //#### Comunicación con la Api Rest Full de python y por POST #####
            //#################################################################
            request = Volley.newRequestQueue(this);
            try {
                cargarWebService();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //##################################################################

        } else if (id == R.id.nav_send) {
            Intent salida = new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
            finish(); // La cerramos.
            System.exit(0);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //#################################################################
    //#### Comunicación con la Api Rest Full de python y por POST #####
    //#################################################################
    private void cargarWebService() throws JSONException {
        progreso = new ProgressDialog(this);
        progreso.setTitle("App Comercial");
        progreso.setMessage("Conectando...");
        progreso.show();
        JSONObject postparams = new JSONObject();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String [] campos = new String[] {"contrato", "gennombre", "gendireccion", "gencruzamientos", "gencolonia", "genmanzana", "genlote", "genopciones", "genotros", "tarttarifa", "tarvfopciones", "tartuso", "tomismedidor", "tommedidor", "tomisfunc", "tommisdesc" , "tommisdesconectado", "tommisrob", "tommisina", "tommiscanc", "tomtisdirecta", "tomtiscancelada", "tomtisconolot", "tomtisclandes", "tomdmiscance", "tomdtiscence", "tomdiscotrlot", "tomdisclandes", "fuga", "imagefilename", "latitud", "longitud", "fecha", "sector"};
        Cursor fila = BaseDeDatos.query("cedula",campos,null, null, null, null, null);
        if (fila.moveToFirst()){
            do{
                postparams.put("contrato", fila.getString(0));
                postparams.put("gennombre", fila.getString(1));
                postparams.put("gendireccion", fila.getString(2));
                postparams.put("gencruzamientos", fila.getString(3));
                postparams.put("gencolonia", fila.getString(4));
                postparams.put("genmanzana", fila.getString(5));
                postparams.put("genlote", fila.getString(6));
                postparams.put("genopciones", fila.getString(7));
                postparams.put("genotros", fila.getString(8));
                postparams.put("tarttarifa", fila.getString(9));
                postparams.put("tarvfopciones", fila.getString(10));
                postparams.put("tartuso", fila.getString(11));
                postparams.put("tomismedidor", fila.getString(12));
                postparams.put("tommedidor", fila.getString(13));
                postparams.put("tomisfunc", fila.getString(14));
                postparams.put("tomisdesc", fila.getString(15));
                postparams.put("tommisdesconectado", fila.getString(16));
                postparams.put("tommisrob", fila.getString(17));
                postparams.put("tommisina", fila.getString(18));
                postparams.put("tommiscanc", fila.getString(19));
                postparams.put("tomtisdirecta", fila.getString(20));
                postparams.put("tomtiscancelada", fila.getString(21));
                postparams.put("tomtisconolot", fila.getString(22));
                postparams.put("tomtisclandes", fila.getString(23));
                postparams.put("tomdmiscance", fila.getString(24));
                postparams.put("tomdtiscence", fila.getString(25));
                postparams.put("tomdiscontrlot", fila.getString(26));
                postparams.put("tomdisclandes", fila.getString(27));
                postparams.put("fuga", fila.getString(28));
                postparams.put("imagefilename", fila.getString(29));
                postparams.put("latitud", fila.getString(30));
                postparams.put("longitud", fila.getString(31));
                postparams.put("fecha", fila.getString(32));
                postparams.put("sector", fila.getString(33));
                String url="http://192.168.15.45:7550/comercial/api/rest";
                jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,  this,this);
                request.add(jsonObjectRequest);
            }while (fila.moveToNext());
        }else{
            Toast.makeText(this, "No existen datos", Toast.LENGTH_SHORT).show();
        }

//        BaseDeDatos.delete("cedula", null, null);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(this, "no se pudo conectar: "+error.toString(), Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show();
        progreso.dismiss();
    }
    //#############################################################################

}
