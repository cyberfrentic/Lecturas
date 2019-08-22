package com.example.lecturas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lecturas.R;

public class tarifasFragment extends Fragment {

    View vista;
    private Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_tarifas, container, false);

        //#################### Adapter del spiner COLONIA##################
        spinner = (Spinner) vista.findViewById(R.id.spTipoUso);
        String [] opciones= {"Elije una opción", "Terreno Baldio", "Casa", "Cuartos o Deptos en Renta", "Lavadero de Autos", "Lavandería", "Dispensador de Agua", "Abarrotes", "Tendejon", "Minisuper", "Subagencia", "Papelería", "Carnicería", "Frutería", "Panadería", "Taller Mecanico", "Taller de Motos", "Taller de Bicicletas", "Corralon", "Herrería", "Bar", "Centro Nocturno", "Pescadería", "Tortillería", "Lonchería", "Restaurant", "Taquería", "Pizzería", "Karaoke", "Zapatería", "CerveFrio", "Six", "Carpintería", "Bisutería",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
        spinner.setAdapter(adapter);
        //###########################################################
        return vista;
    }
}
