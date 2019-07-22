package com.example.lecturas;

public class PadronVo {
    private String numloc;
    private String contrato;
    private String nombre;
    private String direccion;
    private String nummed;
    private int foto;

    public PadronVo(String numloc, String contrato, String nombre, String direccion, String nummed, int foto) {
        this.numloc = numloc;
        this.contrato = contrato;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nummed = nummed;
        this.foto = foto;
    }


    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNumloc() {
        return numloc;
    }

    public void setNumloc(String numloc) {
        this.numloc = numloc;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNummed() {
        return nummed;
    }

    public void setNummed(String nummed) {
        this.nummed = nummed;
    }
}
