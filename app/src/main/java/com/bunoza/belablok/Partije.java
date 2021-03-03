package com.bunoza.belablok;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Partije implements Serializable {
    private List<List<Integer>>  listaMi;
    private List<List<Integer>>  listaVi;

    public List<List<Integer>> getListaMi() {
        return listaMi;
    }

    public void setListaMi(List<List<Integer>> listaMi) {
        this.listaMi = new ArrayList<>();
        for( List<Integer> sublist : listaMi) {
            this.listaMi.add(new ArrayList<>(sublist));
        }
    }
    public void setListaMiUnutar(List<List<Integer>> listaMi) {
        int i;
        for(i =0; i < listaMi.size(); i++){
            this.listaMi.add(i, listaMi.get(i));
        }
    }

    public void setListaViUnutar(List<List<Integer>> listaVi) {
        int i;
        for(i =0; i < listaVi.size(); i++){
            this.listaVi.add(i, listaVi.get(i));
        }
    }

    public List<List<Integer>> getListaVi() {
        return listaVi;
    }

    public void setListaVi(List<List<Integer>> listaVi) {
        this.listaVi = new ArrayList<>();
        for( List<Integer> sublist : listaVi) {
            this.listaVi.add(new ArrayList<>(sublist));
        }
    }
}
