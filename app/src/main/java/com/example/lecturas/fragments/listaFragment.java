package com.example.lecturas.fragments;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.ListaVo;
import com.example.lecturas.R;
import com.example.lecturas.adapterListaLec;

import java.util.ArrayList;


public class listaFragment extends Fragment {

    View vista;
    ArrayList<ListaVo> listaLecturas;
    RecyclerView recyclerListaLecturas;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_lista, container, false);

        // inicia el llenado del recycler
        listaLecturas =  new ArrayList<>();
        recyclerListaLecturas = (RecyclerView) vista.findViewById(R.id.recyclerlecturas);
        recyclerListaLecturas.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Generando Lista...");
        progressDialog.show();
        llenarLista();
        adapterListaLec adapter = new adapterListaLec(listaLecturas);
        recyclerListaLecturas.setAdapter(adapter);
        progressDialog.dismiss();
        
        return vista;
    }

    private void llenarLista() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("select contrato, lectura from lectura",null);
        fila.moveToFirst();
        if (fila.moveToFirst()){
            int numRows = (int) DatabaseUtils.queryNumEntries(BaseDeDatos, "lectura");
            for (int x =0; x < numRows; x++){
                String contras = fila.getString(0);
                Cursor fila2 = BaseDeDatos.rawQuery("select contrato, nummed, nombre, direccion from padron where contrato =?", new String[] {contras});
                fila2.moveToFirst();
                if (!fila2.moveToFirst()){
                    Toast.makeText(getContext(), "Algo Salio Realmente Mal comunicate con Alguien que sepa que paso jejeje", Toast.LENGTH_LONG).show();
                }
                listaLecturas.add(new ListaVo(fila.getString(0), fila2.getString(1), fila2.getString(2), fila2.getString(3),fila.getString(1),R.drawable.lecturas2));

                fila.moveToNext();
            }
        }else{
            Toast.makeText(getContext(), "No hay datos", Toast.LENGTH_LONG).show();

        }
        BaseDeDatos.close();
    }
}
