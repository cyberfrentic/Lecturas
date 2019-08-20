package com.example.lecturas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lecturas.R;


public class generalesFragment extends Fragment {

    View vista;
    CheckBox checkContrato, checkNombre;
    EditText etContrato, etNombre;
    public generalesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_generales, container, false);

        // Inicia el codigo
        checkContrato = (CheckBox) vista.findViewById(R.id.checkContrato);
        checkNombre = (CheckBox) vista.findViewById(R.id.checkNombre);
        etContrato = (EditText) vista.findViewById(R.id.etContrato);
        etNombre = (EditText) vista.findViewById(R.id.etNombre);

        checkContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkContrato.isChecked()){
                    etContrato.setEnabled(true);
                }else{
                    etContrato.setEnabled(false);
                }
            }
        });

        return vista;
    }



}
