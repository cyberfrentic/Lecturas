package com.example.lecturas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lecturas.R;
import com.example.lecturas.clases.Utilidades;

import static com.example.lecturas.fragments.cedulaFragment.viewPager;

public class tarifasFragment extends Fragment {

    private View vista;
    private Spinner spinner;
    private Button btGuardarTar;
    private RadioButton rbTDomestico, rbTComercial, rbTHotelero, rbTIndustrial, rbTServGen, rbTClandestino;
    private TextView tTarifa;
    private String tarvfopciones="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_tarifas, container, false);

        rbTDomestico = (RadioButton) vista.findViewById(R.id.rbTDomestico);
        rbTComercial = (RadioButton) vista.findViewById(R.id.rbTComercial);
        rbTHotelero = (RadioButton) vista.findViewById(R.id.rbTHotelero);
        rbTIndustrial = (RadioButton) vista.findViewById(R.id.rbTIndustrial);
        rbTServGen = (RadioButton) vista.findViewById(R.id.rbTServGen);
        rbTClandestino = (RadioButton) vista.findViewById(R.id.rbTClandestino);
        btGuardarTar = (Button) vista.findViewById(R.id.btGuardarTar);
        tTarifa = (TextView) vista.findViewById(R.id.tTarifa);

        if (Utilidades.contrato == "Sin Contrato"){
            tTarifa.setText("Sin Tarifa");
        }else{
            tTarifa.setText(Utilidades.TarTTarifa);
        }

        //#################### Adapter del spiner COLONIA##################
        spinner = (Spinner) vista.findViewById(R.id.spTipoUso);
//        String [] opciones= {"Elije una opción", "Terreno Baldio", "Casa", "Cuartos o Deptos en Renta", "Lavadero de Autos", "Lavandería", "Dispensador de Agua", "Abarrotes", "Tendejon", "Minisuper", "Subagencia", "Papelería", "Carnicería", "Frutería", "Panadería", "Taller Mecanico", "Taller de Motos", "Taller de Bicicletas", "Corralon", "Herrería", "Bar", "Centro Nocturno", "Pescadería", "Tortillería", "Lonchería", "Restaurant", "Taquería", "Pizzería", "Karaoke", "Zapatería", "CerveFrio", "Six", "Carpintería", "Bisutería",};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
//        spinner.setAdapter(adapter);
        //###########################################################
        rbTDomestico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.GONE);
            }
        });

        rbTComercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                String [] opciones= {"Elije una opción", "Restaurantes", "Bar", "Cantina", "Sala de Fiestas", "Carnicerías", "Panaderías", "Hospitales o Clinicas Privadas", "Escuelas Privadas", "Estadios o Centros deportivos Privados", "Casas de Asistencia", "Cuartos de Renta", "Centros Nocturnos", "Iglesias", "Imprentas", "Autolacados", "Lavanderías", "Tintorerías", "Peluquerias", "Oxxos", "Taquerías", "Loncherías",  "Abarrotes", "Tendejon", "Minisuper", "Subagencia", "Papelería","Frutería","Pescadería", "Pizzería", "Karaoke", "Zapatería", "CerveFrio", "Six", "Carpintería", "Bisutería",};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
                spinner.setAdapter(adapter);
            }
        });

        rbTHotelero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.GONE);
            }
        });

        rbTIndustrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                String [] opciones= {"Elije una opción", "Fabrica de Hielo", "Purificadora de Agua", "Embotelladora de Agua", "Naves Industriales", "Tortillería"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_anomalia, opciones);
                spinner.setAdapter(adapter);
            }
        });

        rbTServGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.GONE);
            }
        });



        rbTClandestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.GONE);
            }
        });


        btGuardarTar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilidades.TarTTarifa="Sin Tarifa";
                if (rbTDomestico.isChecked()){
                    Utilidades.TarVFOpciones="Dom";
                    Utilidades.TarTUso="";
                }else if(rbTComercial.isChecked()){
                    Utilidades.TarVFOpciones ="Com";
                    Utilidades.TarTUso = spinner.getSelectedItem().toString();
                }else if(rbTHotelero.isChecked()){
                    Utilidades.TarVFOpciones ="Hot";
                    Utilidades.TarTUso = "";
                }else if(rbTIndustrial.isChecked()){
                    Utilidades.TarVFOpciones ="Ind";
                    Utilidades.TarTUso = spinner.getSelectedItem().toString();
                }else if(rbTServGen.isChecked()){
                    Utilidades.TarVFOpciones ="Serv";
                    Utilidades.TarTUso = "";
                }else if(rbTClandestino.isChecked()){
                    Utilidades.TarVFOpciones ="Clandestino";
                    Utilidades.TarTUso = "";
                }
                viewPager.setCurrentItem(2);
                Toast.makeText(getContext(),Utilidades.TarVFOpciones+" - "+Utilidades.TarTUso,Toast.LENGTH_LONG).show();
            }
        });
        return vista;
    }
}
