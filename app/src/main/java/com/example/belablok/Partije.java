package com.example.belablok;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Partije implements Serializable {
    private List<List<Integer>>  listaMi = new ArrayList<>();
    private List<List<Integer>>  listaVi = new ArrayList<>();

    public List<List<Integer>> getListaMi() {
        return listaMi;
    }

    public void setListaMi(List<List<Integer>> listaMi) {
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
        for( List<Integer> sublist : listaVi) {
            this.listaVi.add(new ArrayList<>(sublist));
        }
    }
}
