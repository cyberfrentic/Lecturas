package com.example.lecturas.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;


public class capturaLecturaFragment extends Fragment {
    View vista;
    TextView contrato, medidor, nombre, direccion, anterior;
    EditText lectura;
    Spinner spinner;
    Button btn_guardar;

    FragmentManager fragmentManager;
    lecturafragment lecturaatras;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_captura_lectura, container, false);
        // Inflate the layout for this fragment
        contrato = (TextView) vista.findViewById(R.id.contrato);
        medidor = (TextView) vista.findViewById(R.id.medidor);
        nombre = (TextView) vista.findViewById(R.id.nombre);
        direccion = (TextView) vista.findViewById(R.id.direccion);
        anterior = (TextView) vista.findViewById(R.id.lectAnt);
        lectura = (EditText) vista.findViewById(R.id.actualLectura);
        spinner = (Spinner) vista.findViewById(R.id.spinner1);
        btn_guardar = (Button) vista.findViewById(R.id.button2);

        String [] opciones= {"  ","Cerrada", "Obstaculo", "Caratula", "Sin Medidor", "Deshabilitado", "Baldio", "Fuga", "Al Reves","Sin sello", "Rec Ilegal"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
        spinner.setAdapter(adapter);

        contrato.setText(getArguments().getString("contrato"));
        medidor.setText(getArguments().getString("medidor"));
        nombre.setText(getArguments().getString("nombre"));
        direccion.setText(getArguments().getString("direccion"));

        buscar(getArguments().getString("contrato"));
        lectura.requestFocus();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lectura.getText().toString().isEmpty()){
                    int actu = Integer.parseInt(lectura.getText().toString());
                    int ant = Integer.parseInt(anterior.getText().toString());
                    if (actu<ant){
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("Departamento de Informatica");
                        alertDialog.setMessage("La lectura Actual es menor a la lectura Anterior");
                        alertDialog.setIcon(R.drawable.alerta);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                lectura.requestFocus();
                                lectura.setText("");
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }else {
                        guardar(contrato.getText().toString(), lectura.getText().toString(), spinner.getSelectedItem().toString());
                        contrato.setText("");
                        medidor.setText("");
                        nombre.setText("");
                        direccion.setText("");
                        lectura.setText("");
                        spinner.setSelection(0);
//                        lecturaatras = new lecturafragment();
//                        fragmentManager = getFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.contenedor, lecturaatras).addToBackStack(null).commit();
                    }
                }else{
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Sistema Lecturas");
                    alertDialog.setMessage("El campo lectura esta vacio");
                    alertDialog.setIcon(R.drawable.alerta);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                                lectura.requestFocus();
                                alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }

            }
        });
        return vista;
    }

    private void buscar(String contrato) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select lecant from padron where contrato=?",new String[] {contrato});
        if (fila.moveToFirst()){
            fila.moveToFirst();
            String le = fila.getString(0);
            anterior.setText(le);
        }
    }

    private void guardar(String contrato, String lectura, String anomalia){
        ContentValues contenedor = new ContentValues();
        ContentValues contenedor2 = new ContentValues();
        contenedor.put("contrato", contrato);
        contenedor.put("lectura", lectura);
        contenedor.put("anomalia", anomalia);
        contenedor2.put("modif", "1");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        int modificados = BaseDeDatos.update("padron", contenedor2,"contrato=?", new String[] {contrato});
        Toast.makeText(getContext(),"regsitros modificados: "+modificados,Toast.LENGTH_SHORT).show();
        if (modificados>1){
            Toast.makeText(getContext(),"Exise mas de un registro con el mismo numero de contrato",Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.insert("lectura", null, contenedor);
    }

}
