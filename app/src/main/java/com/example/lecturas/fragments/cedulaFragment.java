package com.example.lecturas.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lecturas.R;
import com.example.lecturas.adapters.SeccionesAdapter;
import com.example.lecturas.clases.Utilidades;


public class cedulaFragment extends Fragment {

    View vista;
    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;

    public cedulaFragment() {
        // Required empty public constructor
    }

       public static cedulaFragment newInstance(String param1, String param2) {
        cedulaFragment fragment = new cedulaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_cedula, container, false);


        if (Utilidades.rotacion==0){
            View parent = (View) container.getParent();
            if(appBar==null){
                appBar=(AppBarLayout) parent.findViewById(R.id.appBar);
                pestanas = new TabLayout(getActivity());
                pestanas.setTabTextColors(Color.parseColor("#C0C0C0"), Color.parseColor("#00FFFF"));
                appBar.addView(pestanas);
                viewPager = (ViewPager) vista.findViewById(R.id.idViewPagerInformation);

                llenarViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
                pestanas.setupWithViewPager(viewPager);
            }
            pestanas.setTabGravity(TabLayout.GRAVITY_FILL);
        }else{
            Utilidades.rotacion=1;
        }
        return vista;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Utilidades.rotacion==0){
            appBar.removeView(pestanas);
        }
    }

    private void llenarViewPager(ViewPager viewPager){
        SeccionesAdapter adapter = new SeccionesAdapter(getFragmentManager());
        adapter.addFragment(new generalesFragment(), "Datos Generales");
        adapter.addFragment(new tarifasFragment(), "Datos Tarifas");
        adapter.addFragment(new tomasFragment(), "Datos Tomas");
        adapter.addFragment(new fugasFragment(), "Datos Fugas");

        viewPager.setAdapter(adapter);
    }

   }
