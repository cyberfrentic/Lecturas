package com.example.lecturas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.lecturas.R;

public class tomasFragment extends Fragment {

    View vista;
    private Spinner spinner;
    private EditText etMedidor;
    private Switch nMedidor, mFuncionando, mDescompuesto, mDesconectado, mrobado, mInaccesible, nCancelado;
    private Switch tDirecta, tCancelada, tCoL, tClandestina, dMCancelado, dTCancelada, dTCoL, dTClandestina;

    public tomasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_tomas, container, false);

        etMedidor = (EditText) vista.findViewById(R.id.etMedidor);
        nMedidor = (Switch) vista.findViewById(R.id.nMedidor);
        mFuncionando = (Switch) vista.findViewById(R.id.mFuncionando);
        mDescompuesto = (Switch) vista.findViewById(R.id.mDescompuesto);
        mDesconectado = (Switch) vista.findViewById(R.id.mDesconectado);
        mrobado = (Switch) vista.findViewById(R.id.mRobado);
        mInaccesible = (Switch) vista.findViewById(R.id.mInaccesible);
        nCancelado = (Switch) vista.findViewById(R.id.mCancelado);
        tDirecta = (Switch) vista.findViewById(R.id.tDirecta);
        tCancelada = (Switch) vista.findViewById(R.id.tCancelada);
        tCoL = (Switch) vista.findViewById(R.id.tCoL);
        tClandestina = (Switch) vista.findViewById(R.id.tClandestina);
        dMCancelado = (Switch) vista.findViewById(R.id.dMCancelado);
        dTCancelada = (Switch) vista.findViewById(R.id.dTCancelada);
        dTCoL = (Switch) vista.findViewById(R.id.dTCoL);
        dTClandestina = (Switch) vista.findViewById(R.id.dTClandestina);

        //#################### Adapter del spiner##################
        spinner = (Spinner) vista.findViewById(R.id.tomasspinner);
        String [] opciones= {"Elije una Opcion","Material Construcción", "Obstaculo", "Dentro Predio", "Perro", "Maleza"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
        spinner.setAdapter(adapter);
        //###########################################################

        nMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nMedidor.isChecked()){
                    etMedidor.setVisibility(View.VISIBLE);
                }else{
                    etMedidor.setVisibility(View.GONE);
                }
            }
        });

        mInaccesible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInaccesible.isChecked()){
                    spinner.setVisibility(View.VISIBLE);
                }else{
                    spinner.setVisibility(View.GONE);
                }
            }
        });
        return vista;
    }
}
