package com.example.lecturas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterListaLec extends RecyclerView.Adapter<adapterListaLec.ViewHolderDatos> {


    ArrayList<ListaVo> listlecturas;

    public adapterListaLec(ArrayList<ListaVo> listlecturas) {
        this.listlecturas = listlecturas;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder( ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lectura, null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder( ViewHolderDatos holder, int i) {
        holder.l_contrato.setText(listlecturas.get(i).getList_contrato());
        holder.l_medidor.setText(listlecturas.get(i).getList_medidor());
        holder.l_nombre.setText(listlecturas.get(i).getList_nombre());
        holder.l_direccion.setText(listlecturas.get(i).getLista_dir());
        holder.l_l_ante.setText(listlecturas.get(i).getList_lec_ant());
        holder.l_l_actu.setText(listlecturas.get(i).getList_lec_atu());
        holder.imagen.setImageResource(listlecturas.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        return listlecturas.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView l_contrato, l_medidor, l_nombre, l_direccion, l_l_ante, l_l_actu;
        ImageView imagen;

        public ViewHolderDatos( View itemView) {
            super(itemView);
            l_contrato = (TextView) itemView.findViewById(R.id.lista_contrato);
            l_medidor = (TextView) itemView.findViewById(R.id.lista_medidor);
            l_nombre = (TextView) itemView.findViewById(R.id.lista_nombre);
            l_direccion = (TextView) itemView.findViewById(R.id.lista_direccion);
            l_l_ante = (TextView) itemView.findViewById(R.id.lista_lectura_anterior);
            l_l_actu = (TextView) itemView.findViewById(R.id.lista_lecura_actual);
            imagen = (ImageView) itemView.findViewById(R.id.fotito);
        }
    }
}
