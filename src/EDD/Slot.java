/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import usaclibrary.Estudiante;

/**
 *
 * @author Guillermo
 */
public class Slot {
    private ListaSimple<Estudiante> listado;

    public Slot() {
        this.listado = new ListaSimple<>();
    }

    public ListaSimple<Estudiante> getListado() {
        return listado;
    }

    public void setListado(ListaSimple<Estudiante> listado) {
        this.listado = listado;
    }
    
}
