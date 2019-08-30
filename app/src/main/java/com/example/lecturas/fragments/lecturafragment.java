package com.example.lecturas.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.PadronVo;
import com.example.lecturas.R;
import com.example.lecturas.adaptadorpadron;
import com.example.lecturas.clases.Utilidades;

import java.util.ArrayList;
import java.util.Calendar;


public class lecturafragment extends Fragment{

    View vista;
    ArrayList<PadronVo> listaPadron;
    RecyclerView listas;
    FragmentManager fragmentManager;
    capturaLecturaFragment lectura;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_lecturafragment, container, false);

        // codigo abajo
        listaPadron = new ArrayList<>();

        listas = (RecyclerView) vista.findViewById(R.id.recyclerlistado);
        listas.setLayoutManager(new LinearLayoutManager(getContext()));
        listas.setItemAnimator(new DefaultItemAnimator());

        llenarListaPadron();
        adaptadorpadron adapter = new adaptadorpadron(listaPadron);
        listas.setAdapter(adapter);
        //evento onClickListener del RecyclerView
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"Has seleccionado el contrato: "+ listaPadron.get(listas.getChildAdapterPosition(view)).getContrato(),Toast.LENGTH_LONG).show();
                // Envio de datos de un fragment a otro
                String c = listaPadron.get(listas.getChildAdapterPosition(view)).getContrato();
                String m = listaPadron.get(listas.getChildAdapterPosition(view)).getNummed();
                String n = listaPadron.get(listas.getChildAdapterPosition(view)).getNombre();
                String d = listaPadron.get(listas.getChildAdapterPosition(view)).getDireccion();

                goToCapturaLectura(c,m,n,d);
            }
        });
        return vista;
    }


    private void llenarListaPadron() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("select numloc, contrato, nombre, direccion, nummed, modif from padron",null);
        if (fila.moveToFirst()){
            int numRows = (int) DatabaseUtils.queryNumEntries(BaseDeDatos, "padron");
            for (int x =0; x < numRows; x++){
                if(fila.getString(5).toString().equals("0")){
                    listaPadron.add(new PadronVo(fila.getString(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),R.drawable.user));
                }
                fila.moveToNext();
            }
        }

    }

    private void goToCapturaLectura(String c, String m, String n, String d) {
        locationStart();
        lectura = new capturaLecturaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("contrato", c);
        bundle.putString("medidor", m);
        bundle.putString("nombre", n);
        bundle.putString("direccion", d);
        fragmentManager = getFragmentManager();
        lectura.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.contenedor, lectura).addToBackStack(null).commit();

    }
    public void locationStart() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Utilidades.Latitud = location.getLatitude();
                Utilidades.Longitud = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
}

