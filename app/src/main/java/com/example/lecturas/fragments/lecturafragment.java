package com.example.lecturas.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;


public class lecturafragment extends Fragment {

    View vista;
    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_lecturafragment, container, false);

        // codigo abajo
        lista = (ListView) vista.findViewById(R.id.listado);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("select numloc, contrato, nombre, direccion, nummed, tarifa, lecant from padron",null);
        if (fila.moveToFirst()){
            int numRows = (int) DatabaseUtils.queryNumEntries(BaseDeDatos, "padron");
            String [][] datos = new String [7][numRows];
            for (int x =0; x < numRows; x++){
                datos[0][x] = fila.getString(0);
                datos[1][x] = fila.getString(1);
                datos[2][x] = fila.getString(2);
                datos[3][x] = fila.getString(3);
                datos[4][x] = fila.getString(4);
                datos[5][x] = fila.getString(5);
                datos[6][x] = fila.getString(6);
                fila.moveToNext();
            }
            Toast.makeText(getContext(), datos[0][1000], Toast.LENGTH_LONG).show();
        }

        return vista;
    }


}
