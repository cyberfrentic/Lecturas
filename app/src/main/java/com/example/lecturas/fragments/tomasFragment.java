package com.example.lecturas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.lecturas.R;
import com.example.lecturas.clases.Utilidades;

import static com.example.lecturas.fragments.cedulaFragment.viewPager;

public class tomasFragment extends Fragment {

    View vista;
    private Spinner spinner;
    private EditText etMedidor;
    private Switch nMedidor, mFuncionando, mDescompuesto, mDesconectado, mrobado, mInaccesible, nCancelado;
    private Switch tDirecta, tCancelada, tCoL, tClandestina, dMCancelado, dTCancelada, dTCoL, dTClandestina;
    private Button btTomanext;


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
        btTomanext = (Button) vista.findViewById(R.id.btTomanext);
        if (Utilidades.contrato!="Sin contrato"){
            etMedidor.setText(Utilidades.TomMedidor);
        }

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

        btTomanext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilidades.contrato!="Sin Contrato") {
                    if (nMedidor.isChecked()) {
                        Utilidades.TomIsMedidor = "Si";
                        Utilidades.TomMedidor = etMedidor.getText().toString();
                    } else {
                        Utilidades.TomIsMedidor = "No";
                        Utilidades.TomMedidor = "Sin Medidor";
                    }
                }else{
                    if (nMedidor.isChecked()) {
                        Utilidades.TomIsMedidor = "Si";
                        Utilidades.TomMedidor = etMedidor.getText().toString();
                    } else {
                        Utilidades.TomIsMedidor = "No";
                        Utilidades.TomMedidor = Utilidades.TomMedidor;
                    }
                }
                if (mFuncionando.isChecked()){
                    Utilidades.TomMIsFunc="Si";
                }
                if (mDescompuesto.isChecked()){
                    Utilidades.TomMIsDesc="Si";
                }
                if (mDesconectado.isChecked()){
                    Utilidades.TomMIsDesconectado="Si";
                }
                if (mrobado.isChecked()){
                    Utilidades.TomMIsRob="Si";
                }
                if (mInaccesible.isChecked()){
                    Utilidades.TomMIsIna= spinner.getSelectedItem().toString();
                }
                if(nCancelado.isChecked()){
                    Utilidades.TomMIsCanc="Si";
                }
                if(tDirecta.isChecked()){
                    Utilidades.TomTIsDirecta="Si";
                }
                if(nCancelado.isChecked()){
                    Utilidades.TomTIsCancelada="Si";
                }
                if(tCoL.isChecked()){
                    Utilidades.TomTIsConOLot="Si";
                }
                if(tClandestina.isChecked()){
                    Utilidades.TomTIsClandes="Si";
                }
                if (dMCancelado.isChecked()){
                    Utilidades.TomDMIsCance="Si";
                }
                if(dTCancelada.isChecked()){
                    Utilidades.TomDTIsCance="Si";
                }
                if(dTCoL.isChecked()){
                    Utilidades.TomDIsCOtrLot="Si";
                }
                if(dTClandestina.isChecked()){
                    Utilidades.TomDIsClandes="Si";
                }
                viewPager.setCurrentItem(3);
            }
        });
        return vista;
    }
}
