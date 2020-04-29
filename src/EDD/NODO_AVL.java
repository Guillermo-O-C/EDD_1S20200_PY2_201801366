/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

/**
 *
 * @author Guillermo
 */
public class NODO_AVL{
    
    private String value;
    private int AllowedToDelete;
    private NODO_AVL right;
    private NODO_AVL left;
    private int balance;
    private BTree coleccion;

    public NODO_AVL(String value, int AllowedToDelete) {
        this.value = value;
        this.right = null;
        this.left = null;
        this.balance = 1;
        this.AllowedToDelete = AllowedToDelete;
        this.coleccion = new BTree(3); 
    }

    public int getAllowedToDelete() {
        return AllowedToDelete;
    }

    public void setAllowedToDelete(int AllowedToDelete) {
        this.AllowedToDelete = AllowedToDelete;
    }

    public BTree getColeccion() {
        return coleccion;
    }

    public void setColeccion(BTree coleccion) {
        this.coleccion = coleccion;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NODO_AVL getRight() {
        return right;
    }

    public void setRight(NODO_AVL right) {
        this.right = right;
    }

    public NODO_AVL getLeft() {
        return left;
    }

    public void setLeft(NODO_AVL left) {
        this.left = left;
    }
    
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
}
