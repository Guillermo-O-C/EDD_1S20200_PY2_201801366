package EDD;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @param <T> 
 * @author Guillermo
 */
public class Nodo<T> {
    private T value;
    private Nodo right;
    private Nodo left;
    private Nodo up;
    private Nodo down;
    private int balance;

    public Nodo(T value) {
        this.value = value;
        this.right = null;
        this.left = null;
        this.up = null;
        this.down = null;
        this.balance = 0;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Nodo<T> getRight() {
        return right;
    }

    public void setRight(Nodo right) {
        this.right = right;
    }

    public Nodo<T> getLeft() {
        return left;
    }

    public void setLeft(Nodo left) {
        this.left = left;
    }

    public Nodo<T> getUp() {
        return up;
    }

    public void setUp(Nodo up) {
        this.up = up;
    }

    public Nodo<T> getDown() {
        return down;
    }

    public void setDown(Nodo down) {
        this.down = down;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
}
