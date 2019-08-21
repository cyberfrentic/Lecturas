package com.example.lecturas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.lecturas.R;
//import com.example.lecturas.clases.Utilidades;


public class generalesFragment extends Fragment {

    private View vista;
    private CheckBox checkNombre, checkDireccion, checkCruzamientos, checkColonia, checkManzana,checkLote;
    private EditText etContrato,etNombre, etDireccion, etCruzamientos, etColonia, etManzana, etLote, etOtro;
    private RadioButton rbBaldio, rbHabitada, rbDesabitada, rbComercioF, rbComercioC, rbOtro;

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
        etColonia = (EditText) vista.findViewById(R.id.etColonia);
        etManzana = (EditText) vista.findViewById(R.id.etManzana);
        etLote = (EditText) vista.findViewById(R.id.etLote);

        rbBaldio = (RadioButton) vista.findViewById(R.id.rbBaldio);
        rbHabitada = (RadioButton) vista.findViewById(R.id.rbHabitada);
        rbDesabitada = (RadioButton) vista.findViewById(R.id.rbDeshabitada);
        rbComercioF = (RadioButton) vista.findViewById(R.id.rbComercioF);
        rbComercioC = (RadioButton) vista.findViewById(R.id.rbComercioC);
        rbOtro = (RadioButton) vista.findViewById(R.id.rbOtro);
        etOtro = (EditText) vista.findViewById(R.id.etOtro);

//        etContrato.setText(Utilidades.contrato);
//        etNombre.setText(Utilidades.nombre);
//        etDireccion.setText(Utilidades.direccion);

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
                etOtro.setEnabled(true);
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
        }else{
            etColonia.setEnabled(false);
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
        if (etOtro.isEnabled()){
            etOtro.setEnabled(false);
        }
    }
}