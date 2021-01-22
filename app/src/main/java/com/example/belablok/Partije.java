package com.example.belablok;

import java.io.Serializable;
import java.util.List;

public class Partije implements Serializable {
    private List<List<Integer>>  listaMi;
    private List<List<Integer>>  listaVi;

    public List<List<Integer>> getListaMi() {
        return listaMi;
    }

    public void setListaMi(List<List<Integer>> listaMi) {
        this.listaMi = listaMi;
    }

    public List<List<Integer>> getListaVi() {
        return listaVi;
    }

    public void setListaVi(List<List<Integer>> listaVi) {
        this.listaVi = listaVi;
    }
}
