/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

/**
 * @param <T>
 * @author Guillermo
 */
public class ListaDoble<T> {
    private Nodo<T> Head;
    private Nodo<T> Tail;
    private int size;

    public ListaDoble() {
        this.Head=null;
        this.Tail=null;
        this.size=0;
    }

    public Nodo<T> getHead() {
        return Head;
    }

    public void setHead(Nodo<T> Head) {
        this.Head = Head;
    }

    public Nodo<T> getTail() {
        return Tail;
    }

    public void setTail(Nodo<T> Tail) {
        this.Tail = Tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public void AddLast(T value){
        Nodo<T> nuevo = new Nodo(value);
        if(this.Head==null){
            this.Head=nuevo;
            this.Tail=nuevo;
            this.Head.setRight(null);
        }else {
            this.Tail.setRight(nuevo);
            nuevo.setLeft(this.Tail);
            this.Tail=nuevo;
        }
        size++;
    }
    
    public void DeleteElementAt(int x){
        Nodo<T> y = this.Head;
        for(int i =0;i<x;i++){            
            y=y.getRight();
        }
        if(y.getRight()==null){
            y.getLeft().setRight(null);
            this.Tail=y.getLeft();
        }else{
            if(y!=this.Head){
               y.getLeft().setRight(y.getRight());
               y.getRight().setLeft(y.getLeft()); 
            }else{
               this.Head=y.getRight();
               this.Head.setLeft(null);
            }
            
        }
    }
    
}
