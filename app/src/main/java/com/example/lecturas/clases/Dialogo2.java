package com.example.lecturas.clases;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;

public class Dialogo2 extends AppCompatDialogFragment {
    private EditText direcciones;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogo2_ipes, null);

        builder. setView(view)
                .setTitle("Direccion ip del servidor")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
                        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                        String direc = direcciones.getText().toString();
                        ContentValues dire = new ContentValues();
                        dire.put("direccion", direc);
                        BaseDeDatos.update("ipes", dire,"id=1", null);
                        Toast.makeText(getActivity(),"Si realizo un cambio de ip debe reiniciar la aplicacion", Toast.LENGTH_SHORT).show();
                    }
                });
        direcciones = view.findViewById(R.id.direccion);

        return builder.create();
    }
}
