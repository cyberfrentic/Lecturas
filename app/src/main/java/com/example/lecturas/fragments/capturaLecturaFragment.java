package com.example.lecturas.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;
import com.example.lecturas.clases.Utilidades;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class capturaLecturaFragment extends Fragment {
    View vista;
    TextView contrato, medidor, nombre, direccion, anterior;
    EditText lectura;
    Spinner spinner;
    Button btn_guardar;
    ImageView imgMedidor, imgPredio;
    FragmentManager fragmentManager;
    lecturafragment lecturaatras;
    String imageFileName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_captura_lectura, container, false);
        // Inflate the layout for this fragment
        contrato = (TextView) vista.findViewById(R.id.contrato);
        medidor = (TextView) vista.findViewById(R.id.medidor);
        nombre = (TextView) vista.findViewById(R.id.nombre);
        direccion = (TextView) vista.findViewById(R.id.direccion);
//        anterior = (TextView) vista.findViewById(R.id.lectAnt);
        lectura = (EditText) vista.findViewById(R.id.actualLectura);
        spinner = (Spinner) vista.findViewById(R.id.spinner1);
        btn_guardar = (Button) vista.findViewById(R.id.button2);
        imgMedidor = (ImageView) vista.findViewById(R.id.imgMedidor);
        imgPredio = (ImageView) vista.findViewById(R.id.imgPredio);


        String [] opciones= {"  ","Cerrada", "Obstaculo", "Caratula", "Sin Medidor", "Deshabilitado", "Baldio", "Fuga", "Al Reves","Sin sello", "Rec Ilegal"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
        spinner.setAdapter(adapter);

        contrato.setText(getArguments().getString("contrato"));
        medidor.setText(getArguments().getString("medidor"));
        nombre.setText(getArguments().getString("nombre"));
        direccion.setText(getArguments().getString("direccion"));


        lectura.requestFocus();

        imgMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFotografia("Medidor");
            }
        });
        imgPredio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFotografia("Predio");
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilidades.tomadaM && Utilidades.tomadaP){
                    if (!lectura.getText().toString().isEmpty()){
                        int actu = Integer.parseInt(lectura.getText().toString());
                        guardar(contrato.getText().toString(), lectura.getText().toString(), spinner.getSelectedItem().toString());
                        contrato.setText("");
                        medidor.setText("");
                        nombre.setText("");
                        direccion.setText("");
                        lectura.setText("");
                        spinner.setSelection(0);
                        imgMedidor.setImageResource(R.drawable.lecturas2);
                        imgPredio.setImageResource(R.drawable.ic_action_name);
                        Toast.makeText(getContext(),Utilidades.Latitud+" "+Utilidades.Longitud, Toast.LENGTH_LONG).show();

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
                }else{
                    Toast.makeText(getContext(),"Tiene que tomar la foto del predio y el medidor", Toast.LENGTH_LONG).show();
                }

            }
        });
        return vista;
    }


    private void guardar(String contrato, String lectura, String anomalia){
        ContentValues contenedor = new ContentValues();
        ContentValues contenedor2 = new ContentValues();
        contenedor.put("contrato", contrato);
        contenedor.put("lectura", lectura);
        contenedor.put("anomalia", anomalia);
        contenedor.put("longitud", Utilidades.Longitud);
        contenedor.put("latitud", Utilidades.Latitud);
        contenedor.put("imgMedidor", Utilidades.nam);
        contenedor.put("imgpred", Utilidades.nap);
        contenedor2.put("modif", "1");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        int modificados = BaseDeDatos.update("padron", contenedor2,"contrato=?", new String[] {contrato});
        final long lectura1 = BaseDeDatos.insert("lectura", null, contenedor);

        Toast.makeText(getContext(),lectura1+"regsitros modificados: "+modificados,Toast.LENGTH_SHORT).show();
        if (modificados>1){
            Toast.makeText(getContext(),"Exise mas de un registro con el mismo numero de contrato",Toast.LENGTH_SHORT).show();
        }

    }

//################################################################################################
String currentPhotoPath;

    private File createImageFile(String cual) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = getArguments().getString("contrato")+"_"+cual+"_"+ timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void tomarFotografia(String cual) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(cual);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Utilidades.uris=photoURI;
                Utilidades.Cual=cual;
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);


            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE=1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if(Utilidades.uris != null){
                if(Utilidades.Cual.equals("Medidor")){
                    imgMedidor.setImageURI(Utilidades.uris);
                    Utilidades.tomadaM=true;
                    Utilidades.nam = Utilidades.uris.toString();
                    Toast.makeText(getContext(),"foto tomada", Toast.LENGTH_LONG).show();
                }else{
                    imgPredio.setImageURI(Utilidades.uris);
                    Utilidades.tomadaP=true;
                    Utilidades.nap = Utilidades.uris.toString();
                    Toast.makeText(getContext(),"foto tomada", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}
