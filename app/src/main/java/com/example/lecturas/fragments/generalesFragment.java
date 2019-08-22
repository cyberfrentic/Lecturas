package com.example.lecturas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.lecturas.R;
//import com.example.lecturas.clases.Utilidades;


public class generalesFragment extends Fragment {

    private View vista;
    private CheckBox checkNombre, checkDireccion, checkCruzamientos, checkColonia, checkManzana,checkLote;
    private EditText etContrato,etNombre, etDireccion, etCruzamientos, etManzana, etLote;
    private RadioButton rbBaldio, rbHabitada, rbDesabitada, rbComercioF, rbComercioC, rbOtro;
    private Spinner spinner, etColonia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_generales, container, false);

        // Inicia el codigo
        checkNombre = (CheckBox) vista.findViewById(R.id.checkNombre);
        checkDireccion = (CheckBox) vista.findViewById(R.id.checkDireccion);
        checkCruzamientos = (CheckBox) vista.findViewById(R.id.checkCruzamientos);
        checkColonia = (CheckBox) vista.findViewById(R.id.checkColonia);
        checkManzana = (CheckBox) vista.findViewById(R.id.checkManzana);
        checkLote = (CheckBox) vista.findViewById(R.id.checkLote);

        etContrato = (EditText) vista.findViewById(R.id.etContrato);
        etNombre = (EditText) vista.findViewById(R.id.etNombre);
        etDireccion = (EditText) vista.findViewById(R.id.etDireccion);
        etCruzamientos = (EditText) vista.findViewById(R.id.etCruzamientos);
        etColonia = (Spinner) vista.findViewById(R.id.etColonia);
        etManzana = (EditText) vista.findViewById(R.id.etManzana);
        etLote = (EditText) vista.findViewById(R.id.etLote);

        rbBaldio = (RadioButton) vista.findViewById(R.id.rbBaldio);
        rbHabitada = (RadioButton) vista.findViewById(R.id.rbHabitada);
        rbDesabitada = (RadioButton) vista.findViewById(R.id.rbDeshabitada);
        rbComercioF = (RadioButton) vista.findViewById(R.id.rbComercioF);
        rbComercioC = (RadioButton) vista.findViewById(R.id.rbComercioC);
        rbOtro = (RadioButton) vista.findViewById(R.id.rbOtro);

        etColonia.setEnabled(false);
        etColonia.setClickable(false);

        //#################### Adapter del spiner OTRO ##################
        spinner = (Spinner) vista.findViewById(R.id.etOtro);
        String [] opciones2= {"Elije una Opcion","Renta", "Prestamo", "Cuartería", "Intestada", "Embargada(Banco)"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones2);
        spinner.setAdapter(adapter2);
        //###########################################################

        //#################### Adapter del spiner COLONIA##################
        etColonia = (Spinner) vista.findViewById(R.id.etColonia);
        String [] opciones= {"Elije una Colonia","Centro", "Fco. May", "J. Bautista V.", "Constituyentes", "Benito Juarez", "Cecilio C.", "Lazaro Cardenas", "Rafael E. M.", "Javier Rojo G.", "Leona Vicario", "J. Martinez R.", "Emiliano Z. I", "Emiliano Z. II", "Plan de Ayala", "Plan de Ayutla", "Plan de Gpe.", "Plan de la Noria"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
        etColonia.setAdapter(adapter);
        //###########################################################


        checkNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriNombre();
            }
        });
        checkDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriDireccion();
            }
        });
        checkCruzamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriCruz();
            }
        });
        checkColonia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriCol();
            }
        });
        checkManzana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriMan();
            }
        });
        checkLote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriLot();
            }
        });


        rbBaldio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriEtOtro();
            }
        });
        rbHabitada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriEtOtro();
            }
        });
        rbDesabitada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriEtOtro();
            }
        });
        rbComercioF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriEtOtro();
            }
        });
        rbComercioC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veriEtOtro();
            }
        });
        rbOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbOtro.isChecked()) {
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });

        return vista;
    }
    private void veriNombre(){
        if (checkNombre.isChecked()){
            etNombre.setEnabled(true);
        }else{
            etNombre.setEnabled(false);
        }
    }
    private void veriDireccion(){
        if (checkDireccion.isChecked()==true){
            etDireccion.setEnabled(true);
        }else{
            etDireccion.setEnabled(false);
        }
    }
    private void veriCruz(){
        if (checkCruzamientos.isChecked()){
            etCruzamientos.setEnabled(true);
        }else{
            etCruzamientos.setEnabled(false);
        }
    }
    private void veriCol(){
        if (checkColonia.isChecked()){
            etColonia.setEnabled(true);
            etColonia.setClickable(true);
        }else{
            etColonia.setEnabled(false);
            etColonia.setClickable(false);
        }
    }
    private void veriMan(){
        if (checkManzana.isChecked()){
            etManzana.setEnabled(true);
        }else{
            etManzana.setEnabled(false);
        }
    }
    private void veriLot(){
        if (checkLote.isChecked()){
            etLote.setEnabled(true);
        }else{
            etLote.setEnabled(false);
        }
    }
    private void veriEtOtro(){
       spinner.setVisibility(View.GONE);
    }
}