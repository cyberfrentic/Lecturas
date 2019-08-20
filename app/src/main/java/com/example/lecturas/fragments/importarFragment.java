package com.example.lecturas.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class importarFragment extends Fragment {

    View vista;
    private List<String> nombreArchivos;
    private List<String> rutaArchivos;
    private ArrayAdapter<String> adaptador;
    private String directorioRaiz;
    private TextView carpetaActual;
    ListView listas;
    Button btnImport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_importar, container, false);

        carpetaActual = (TextView) vista.findViewById(R.id.rutaactual);
        listas = (ListView) vista.findViewById(R.id.recyclerlistado);

        final String directorioRaiz = Environment.getExternalStorageDirectory().getPath();

        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                File archivo = new File(rutaArchivos.get(i));
                if (archivo.isFile()) {
                    Toast.makeText(getContext(), "Has seleccionado el archivo: " + archivo.getName(), Toast.LENGTH_SHORT).show();
                    carpetaActual.setText(archivo.getName());
                } else {
                    verDirectorio((rutaArchivos.get(i)));
                }
            }
        });
        verDirectorio(directorioRaiz);

        // cast del imagenButton
        btnImport = (Button) vista.findViewById(R.id.Button);
        // evento OnclickListener del imagenButton
        btnImport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String ruta = (String) carpetaActual.getText().toString();
                Toast.makeText(getContext(), ruta, Toast.LENGTH_LONG).show();
                String linea, sCadena;
                List<String> listado = new ArrayList<String>();
                ContentValues contenedor = new ContentValues();
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
                SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                int numRows1 = (int) DatabaseUtils.queryNumEntries(BaseDeDatos,"padron");
                if (numRows1 == 0){
                    try {
                        File textFile = new File(Environment.getExternalStorageDirectory(), (String) carpetaActual.getText());
                        FileInputStream fis = new FileInputStream(textFile);
                        if (fis != null) {
                            int contador1 = 0;
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader lector = new BufferedReader(isr);
                            while ((linea = lector.readLine()) != null) {
                                contador1+=1;
                                String locali = linea.substring(1, 24).trim();
                                String contra = linea.substring(25, 31).trim();
                                String nomb = linea.substring(32, 70).trim();
                                String direc = linea.substring(71, 111).trim();
                                String medidor = linea.substring(112, 120).trim();
                                String tarif = linea.substring(121, 124).trim();
                                String anterior = linea.substring(138, 147).trim();
                                contenedor.put("id", contador1);
                                contenedor.put("numloc", locali);
                                contenedor.put("contrato", contra);
                                contenedor.put("nombre", nomb);
                                contenedor.put("direccion", direc);
                                contenedor.put("nummed", medidor);
                                contenedor.put("tarifa", tarif);
                                contenedor.put("lecant", anterior);
                                contenedor.put("modif","0");
                                BaseDeDatos.insert("padron", null, contenedor);
                            }
                            int numRows = (int) DatabaseUtils.queryNumEntries(BaseDeDatos,"padron");
                            BaseDeDatos.close();
                            String contador = Integer.toString(numRows);
                            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            // Setting Dialog Title
                            alertDialog.setTitle("Sistema Lecturas");

                            // Setting Dialog Message
                            alertDialog.setMessage("Los "+ contador +" registros se han cargado con exito");

                            // Setting Icon to Dialog
                            alertDialog.setIcon(R.drawable.alerta);

                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog closed
//                                Toast.makeText(getContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();
//                        Toast.makeText(getContext(), contador +" Registros agregados con exito", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "El archivo esta Vacio", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(), "la tabla padron ya cueta con registros debe limpiarlo antes", Toast.LENGTH_LONG).show();
                }

            }

        });
        return vista;
    }


    // metodo para examinar archivos
    private void verDirectorio(String rutaDirecorio) {
        nombreArchivos = new ArrayList<String>();
        rutaArchivos = new ArrayList<String>();
        int count = 0;
        File directorioActual = new File(rutaDirecorio);
        File[] listaArchivos = directorioActual.listFiles();

        if (!rutaDirecorio.equals(directorioRaiz)) {
            nombreArchivos.add("../");
            rutaArchivos.add(directorioActual.getParent());
            count = 1;
        }
        for (File archivo : listaArchivos) {
            rutaArchivos.add(archivo.getPath());
        }
        Collections.sort(rutaArchivos, String.CASE_INSENSITIVE_ORDER);
        for (int i = count; i < rutaArchivos.size(); i++) {
            File archivo = new File(rutaArchivos.get(i));
            if (archivo.isFile()) {
                nombreArchivos.add(archivo.getName());
            } else {
                nombreArchivos.add("/" + archivo.getName());
            }
        }
        if (listaArchivos.length < 1) {
            nombreArchivos.add("No hay ningun archivo");
            rutaArchivos.add(rutaDirecorio);
        }
        adaptador = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.lista_archivos, nombreArchivos);
        listas.setAdapter(adaptador);
    }
}
