package com.example.lecturas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorpadron extends RecyclerView.Adapter<adaptadorpadron.ViewHolderpadron> implements View.OnClickListener {

    ArrayList<PadronVo> listapadron;
    private View.OnClickListener listener;

    public adaptadorpadron(ArrayList<PadronVo> listapadron) {
        this.listapadron = listapadron;
    }

    @Override
    public adaptadorpadron.ViewHolderpadron onCreateViewHolder( ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecturaslista, null, false);
        view.setOnClickListener(this);
        return new ViewHolderpadron(view);
    }

    @Override
    public void onBindViewHolder(adaptadorpadron.ViewHolderpadron viewHolderpadron, int i) {
        viewHolderpadron.contrato.setText(listapadron.get(i).getContrato());
        viewHolderpadron.medidor.setText(listapadron.get(i).getNummed());
        viewHolderpadron.localizacion.setText(listapadron.get(i).getNumloc());
        viewHolderpadron.nombre.setText(listapadron.get(i).getNombre());
        viewHolderpadron.direccion.setText(listapadron.get(i).getDireccion());
        viewHolderpadron.foto.setImageResource(listapadron.get(i).getFoto());
    }

    @Override
    public int getItemCount() {
        return listapadron.size();
    }

    public  void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderpadron extends RecyclerView.ViewHolder {
        TextView contrato, medidor, localizacion, nombre, direccion;
        ImageView foto;
        public ViewHolderpadron(View itemView) {
            super(itemView);
            contrato = (TextView) itemView.findViewById(R.id.contrato);
            medidor = (TextView) itemView.findViewById(R.id.nummed);
            localizacion = (TextView) itemView.findViewById(R.id.numloc);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            foto = (ImageView) itemView.findViewById(R.id.imageView5);
        }
    }
}
