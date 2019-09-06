package com.example.lecturas.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.lecturas.AdminSQLiteOpenHelper;
import com.example.lecturas.R;
import com.example.lecturas.clases.Utilidades;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class fugasFragment extends Fragment {

    View vista;
    Spinner spinner;
    Button btTomarFoto, btGuradar;
    ImageView imagenID;
    private Calendar fecha = Calendar.getInstance();
    private int mes = fecha.get(Calendar.MONTH) + 1;
    String path;
    Uri photoURI;
    String imageFileName;
    FragmentManager fragmentManager;
    preCedFragment precedula;


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
        btGuradar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilidades.fuga = spinner.getSelectedItem().toString();
                if (Utilidades.uris == null){
                    Utilidades.imageFileName ="sinfoto";
                }else {
                    Utilidades.imageFileName = Utilidades.uris.toString();
                }
                guardarDatos();
            }
        });
        return vista;
    }

    private void guardarDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();

        if(Utilidades.contrato=="Sin Contrato"){
            Utilidades.contrato = "0";
        }else{

        }

        nuevoRegistro.put("contrato", Utilidades.contrato);
        nuevoRegistro.put("gennombre", Utilidades.GenNombre);
        nuevoRegistro.put("gendireccion", Utilidades.Gendireccion);
        nuevoRegistro.put("gencruzamientos", Utilidades.GenCruzamientos);
        nuevoRegistro.put("gencolonia", Utilidades.GenColonia);
        nuevoRegistro.put("genmanzana", Utilidades.GenManzna);
        nuevoRegistro.put("genlote", Utilidades.GenLote);
        nuevoRegistro.put("genopciones", Utilidades.GenOpciones);
        nuevoRegistro.put("genotros", Utilidades.GenOtros);
        nuevoRegistro.put("tarttarifa", Utilidades.TarTTarifa);
        nuevoRegistro.put("tarvfopciones", Utilidades.TarVFOpciones);
        nuevoRegistro.put("tartuso", Utilidades.TarTUso);
        nuevoRegistro.put("tomismedidor", Utilidades.TomIsMedidor);
        nuevoRegistro.put("tommedidor", Utilidades.TomMedidor);
        nuevoRegistro.put("tomisfunc", Utilidades.TomMIsFunc);
        nuevoRegistro.put("tommisdesc", Utilidades.TomMIsDesc);
        nuevoRegistro.put("tommisdesconectado", Utilidades.TomMIsDesconectado);
        nuevoRegistro.put("tommisrob", Utilidades.TomMIsRob);
        nuevoRegistro.put("tommisina", Utilidades.TomMIsIna);
        nuevoRegistro.put("tommiscanc", Utilidades.TomMIsCanc);
        nuevoRegistro.put("tomtisdirecta", Utilidades.TomTIsDirecta);
        nuevoRegistro.put("tomtiscancelada", Utilidades.TomTIsCancelada);
        nuevoRegistro.put("tomtisconolot", Utilidades.TomTIsConOLot);
        nuevoRegistro.put("tomtisclandes", Utilidades.TomTIsClandes);
        nuevoRegistro.put("tomdmiscance", Utilidades.TomDMIsCance);
        nuevoRegistro.put("tomdtiscence", Utilidades.TomDTIsCance);
        nuevoRegistro.put("tomdiscotrlot", Utilidades.TomDIsCOtrLot);
        nuevoRegistro.put("tomdisclandes", Utilidades.TomDIsClandes);
        nuevoRegistro.put("fuga", Utilidades.fuga);
        if (Utilidades.imageFileName==null){
            nuevoRegistro.put("imagefilename", "sin foto");
        }else{
            nuevoRegistro.put("imagefilename", Utilidades.imageFileName);
        }

        if (Utilidades.Latitud==0.0 || Utilidades.Longitud==0.0){
            Toast.makeText(getContext(),"No hay datos", Toast.LENGTH_LONG).show();
        }
        nuevoRegistro.put("latitud", Utilidades.Latitud);
        nuevoRegistro.put("longitud", Utilidades.Longitud);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        nuevoRegistro.put("fecha", timeStamp);
        nuevoRegistro.put("sector", Utilidades.sector);
        BaseDeDatos.insert("cedula", null, nuevoRegistro);
        BaseDeDatos.close();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        // Setting Title
        progressDialog.setTitle("App Comercial");
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Horizontal
        progressDialog.setIndeterminate(true);
        // Cannot Cancel Progress Dialog
        progressDialog.setCancelable(false);
        //Dismiss the dialog
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetVarGlob();
                precedula = new preCedFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor, precedula).addToBackStack(null).commit();
                //Dismiss the dialog
                progressDialog.dismiss();
            }
        }, 5000);


    }

    private void resetVarGlob() {
        Utilidades. Latitud=0.0;
        Utilidades. Longitud=0.0;

        //varios para determinar la colonia
        Utilidades. sector="";
        Utilidades. sb="";
        Utilidades. colonia="";

        //Fragment Generales
        Utilidades. GenNombre="";
        Utilidades. Gendireccion="";
        Utilidades. GenCruzamientos="";
        Utilidades. GenColonia="";
        Utilidades. GenManzna="";
        Utilidades. GenLote="";
        Utilidades. GenOpciones="";
        Utilidades. GenOtros="";

        // Fragment Tarifas
        Utilidades. TarTTarifa=""; // tarifa de la tabla padron
        Utilidades. TarVFOpciones="";
        Utilidades. TarTUso="";

        //Fragment Tomas
        Utilidades. TomIsMedidor="";
        Utilidades. TomMedidor=""; //Numero de medidor de la tablaPadmed
        Utilidades. TomMIsFunc="";
        Utilidades. TomMIsDesc="";
        Utilidades. TomMIsDesconectado = "";
        Utilidades. TomMIsRob="";
        Utilidades. TomMIsIna="";
        Utilidades. TomMIsCanc="";
        Utilidades. TomTIsDirecta ="";
        Utilidades. TomTIsCancelada="";
        Utilidades. TomTIsConOLot="";
        Utilidades. TomTIsClandes="";
        Utilidades. TomDMIsCance="";
        Utilidades. TomDTIsCance="";
        Utilidades. TomDIsCOtrLot="";
        Utilidades. TomDIsClandes="";

        //Fragment Fugas
        Utilidades. fuga="";
        Utilidades. imageFileName="";
    }


    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = Utilidades.contrato+"_Fuga_"+ timeStamp + "_";
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
