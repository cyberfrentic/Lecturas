package com.example.lecturas.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;
import com.example.lecturas.clases.Utilidades;


public class preCedFragment extends Fragment {

    View vista;
    private RadioButton cContrato, sContrato;
    private EditText etContrato;
    private Button btCedula;
    FragmentManager fragmentManager;
    cedulaFragment cedula;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_pre_ced, container, false);
        cContrato = (RadioButton) vista.findViewById(R.id.rbContrato);
        sContrato = (RadioButton) vista.findViewById(R.id.rbSContrato);
        etContrato = (EditText) vista.findViewById(R.id.contrato);
        btCedula = (Button) vista.findViewById(R.id.btCedula);
        btCedula.setEnabled(false);

        cContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContrato.setVisibility(View.VISIBLE);
                btCedula.setEnabled(true);
            }
        });

        sContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContrato.setText("");
                etContrato.setVisibility(View.GONE);
                btCedula.setEnabled(true);
            }
        });

        btCedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cContrato.isChecked() ){
                    if (etContrato.length()==0){
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        // Setting Dialog Title
                        alertDialog.setTitle("App Comercial");

                        // Setting Dialog Message
                        alertDialog.setMessage("Debe Ingresar un contrato");

                        // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.alerta);

                        // Setting OK Button
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
//                                Toast.makeText(getContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                                etContrato.requestFocus();
                                alertDialog.dismiss();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }else{
                        Utilidades.contrato = etContrato.getText().toString();
                        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
                        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                        String cuenta = Utilidades.contrato;
                        String [] campos = new String[]{"cuenta", "sb", "sector", "manzana", "lote", "nombre", "direccion", "colonia", "tarifa"};
                        String [] args = new String[]{cuenta};

                        Cursor fila = BaseDeDatos.query("padronTotal", campos, "cuenta=?", args, null,null,null);
                        if (fila.moveToFirst()){
                            Utilidades.contrato = fila.getString(0);
                            Utilidades.sb = fila.getString(1);
                            Utilidades.sector = fila.getString(2);
                            Utilidades.GenManzna = fila.getString(3);
                            Utilidades.GenLote = fila.getString(4);
                            Utilidades.GenNombre = fila.getString(5);
                            Utilidades.Gendireccion = fila.getString(6);
                            Utilidades.colonia = fila.getString(7);
                            Utilidades.TarTTarifa = fila.getString(8);
                            Toast.makeText(getContext(), Utilidades.GenNombre, Toast.LENGTH_SHORT).show();
                            String [] campos2 = new String[]{"nummed"};
                            Cursor fila2 = BaseDeDatos.query("padron", campos2, "contrato=?", args, null,null,null);
                            if (fila2.moveToFirst()){
                                Utilidades.TomMedidor=fila2.getString(0);
                            }else{
                                Utilidades.TomMedidor="0";
                            }
                            BaseDeDatos.close();
                            cedula = new cedulaFragment();
                            fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.contenedor, cedula).addToBackStack(null).commit();
                        }else{
                            Toast.makeText(getContext(), "El Contrato No existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Utilidades.contrato = "Sin Contrato";
                    cedula = new cedulaFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor, cedula).addToBackStack(null).commit();
                }
            }
        });
        return vista;
    }

}
