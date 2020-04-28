/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import usaclibrary.Books;

/**
 *
 * @author Guillermo
 */
public class NODO_B {
    private int order;
    private Books[] shelf;
    NODO_B[] branches;
    NODO_B trunk;
    int occupied;
    boolean isLeaf;

    public NODO_B(NODO_B trunk) {
        this.order=3;
        this.shelf = new Books[2*this.order-1];
        this.isLeaf = true;
        this.branches =  new NODO_B[2*this.order];
        this.occupied=0;
        this.trunk=trunk;
    }
    public Books getBook(int i){
        return shelf[i];
    }
    public NODO_B getBranch(int i){
        return branches[i];
    }
    
    public Books[] getShelf() {
        return shelf;
    }

    public void setShelf(Books[] shelf) {
        this.shelf = shelf;
    }

    public NODO_B[] getBranches() {
        return branches;
    }

    public void setBranches(NODO_B[] branches) {
        this.branches = branches;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public boolean isIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public NODO_B getTrunk() {
        return trunk;
    }

    public void setTrunk(NODO_B trunk) {
        this.trunk = trunk;
    }
    
    public NODO_B UpdateBook(Books Data){
        int i =0;
        while(i<this.occupied && Data.getISBN() > this.shelf[i].getISBN()){
            i++;
        }
        if(this.shelf[i].getISBN()==Data.getISBN()){
            this.shelf[i]=Data;
        }
        if(isIsLeaf()){
            return null;
        }
        return this.branches[i].UpdateBook(Data);
    }
     public Books Search(int ISBN){
        int i =0;
        while(i<this.occupied && ISBN > this.shelf[i].getISBN()){
            i++;
        }
        if(this.shelf[i].getISBN()==ISBN){
            return this.shelf[i];
        }
        if(isIsLeaf()){
            return null;
        }
        return this.branches[i].Search(ISBN);
    }
}
