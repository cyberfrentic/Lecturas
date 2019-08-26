package com.example.lecturas.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lecturas.R;
import com.example.lecturas.clases.Utilidades;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.Result;

public class fugasFragment extends Fragment {

    View vista;
    Spinner spinner;
    Button btTomarFoto, btGuradar;
    ImageView imagenID;
    private Calendar fecha = Calendar.getInstance();
    private int mes = fecha.get(Calendar.MONTH) + 1;
    String path;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fugas, container, false);

        imagenID = (ImageView) vista.findViewById(R.id.imagenID);

        //#################### Adapter del spiner COLONIA##################
        spinner = (Spinner) vista.findViewById(R.id.spinner);
        String [] opciones= {"Elije una opción", "Medidor", "Drenaje", "Cuadro", "Calle", "Descarga", "Linea de Condcción", "Llave de Paso", "Red", "Poliducto"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
        spinner.setAdapter(adapter);
        //###########################################################
        btTomarFoto = (Button) vista.findViewById(R.id.btFoto);
        btGuradar = (Button) vista.findViewById(R.id.btGuardar);



        btTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFotografia();
            }
        });
        return vista;
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = Utilidades.contrato+"_Fuga_"+ timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void tomarFotografia() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Utilidades.uris=photoURI;
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
                imagenID.setImageURI(Utilidades.uris);
                Toast.makeText(getContext(),"foto tomada", Toast.LENGTH_LONG).show();
            }
        }
    }

}
