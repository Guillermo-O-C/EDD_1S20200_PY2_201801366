/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import javax.swing.JOptionPane;
import usaclibrary.Estudiante;

/**
 *
 * @author Guillermo
 */
public class HashTable {
    private Slot[] bucket;

    public HashTable() {
        this.bucket = new Slot[45];
    }    
    public boolean Insert(Estudiante x){
        int key = HashFunction(x.getCarne());
        if(key>=0 && key <45){
            if(this.bucket[key]==null){
                this.bucket[key]=new Slot();
            }
            if(!SearchStudent(x, this.bucket[key])){                
                this.bucket[key].getListado().AddLast(x);
                return true;
            }else{                
                JOptionPane.showMessageDialog(null, "El carné que se ha intentado ingresar ya existe.");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error en el registro, inténtalo de nuevo.");
        }
        return false;
    }
    private int HashFunction(int key){
        return key % 45;
    }
    private boolean SearchStudent(Estudiante x, Slot y){
        Nodo<Estudiante> z = y.getListado().getHead();
        while(z!=null){
            if(z.getValue().getCarne()==x.getCarne()){
                return true;
            }
            z=z.getRight();
        }
        return false;
    }
}
