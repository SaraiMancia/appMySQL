package com.itca.appmysql.ui.qcategorias;

import java.io.Serializable;

public class Dtocate implements Serializable {
    private int idCategoria;
    private String nombrecategoria;
    private int estadocategoria;

    public Dtocate(){

    }
    public Dtocate(int idCategoria, String nombrecategoria, int estadocategoria) {
        this.idCategoria = idCategoria;
        this.nombrecategoria = nombrecategoria;
        this.estadocategoria = estadocategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public int getEstadocategoria() {
        return estadocategoria;
    }

    public void setEstadocategoria(int estadocategoria) {
        this.estadocategoria = estadocategoria;
    }
}
