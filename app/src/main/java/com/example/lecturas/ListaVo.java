package com.example.lecturas;

public class ListaVo {
    private String list_contrato;
    private String list_medidor;
    private String list_nombre;
    private String lista_dir;
    private String list_lec_ant;
    private String list_lec_atu;
    private int photo;

    public ListaVo(){

    }

    public ListaVo(String list_contrato, String list_medidor, String list_nombre, String lista_dir, String list_lec_ant, String list_lec_atu, int photo) {
        this.list_contrato = list_contrato;
        this.list_medidor = list_medidor;
        this.list_nombre = list_nombre;
        this.lista_dir = lista_dir;
        this.list_lec_ant = list_lec_ant;
        this.list_lec_atu = list_lec_atu;
        this.photo = photo;
    }

    public String getList_contrato() {
        return list_contrato;
    }

    public void setList_contrato(String list_contrato) {
        this.list_contrato = list_contrato;
    }

    public String getList_medidor() {
        return list_medidor;
    }

    public void setList_medidor(String list_medidor) {
        this.list_medidor = list_medidor;
    }

    public String getList_nombre() {
        return list_nombre;
    }

    public void setList_nombre(String list_nombre) {
        this.list_nombre = list_nombre;
    }

    public String getLista_dir() {
        return lista_dir;
    }

    public void setLista_dir(String lista_dir) {
        this.lista_dir = lista_dir;
    }

    public String getList_lec_ant() {
        return list_lec_ant;
    }

    public void setList_lec_ant(String list_lec_ant) {
        this.list_lec_ant = list_lec_ant;
    }

    public String getList_lec_atu() {
        return list_lec_atu;
    }

    public void setList_lec_atu(String list_lec_atu) {
        this.list_lec_atu = list_lec_atu;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
