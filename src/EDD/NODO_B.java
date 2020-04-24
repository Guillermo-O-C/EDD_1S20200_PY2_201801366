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
    
    
    
    
    
    /*
    public void Cross(){
        int i =0;
        for(i =0;i<this.occupied;i++){
            if(this.isLeaf==false){
               this.branches[i].Cross();
            }
            System.out.println(Integer.toString(shelf[i].getISBN())+" ");
        }
        if(this.isLeaf==false){
            this.branches[i].Cross();
        }
    }
    public NODO_B Search(int ISBN){
        int i =0;
        while(i<this.occupied && ISBN > shelf[i].getISBN()){
            i++;
        }
        if(this.shelf[i].getISBN()==ISBN){
            return this;
        }
        if(this.isLeaf){
            return null;
        }
        return this.branches[i].Search(ISBN);
    }

    public void InsertNode(int ISBN, Books Data){
        int degree =3;
        int i = occupied-1;
        if(isLeaf){
            while(i>=0 && shelf[i].getISBN()>ISBN){
                shelf[i+1] = shelf[i];
                i--;
            }
            shelf[i+1]=Data;
            occupied++;
        }else{
            while(i>=0 && shelf[i].getISBN() > ISBN){
                i--;
            }
            if(branches[i+1].getOccupied()==2*degree-1){
                SplitNode(i+1, branches[i+1]);
                if(shelf[i+1].getISBN()<ISBN){
                    i++;
                }
            }
            branches[i+1].InsertNode(ISBN, Data);
        }
    }
    
    public void SplitNode(int i, NODO_B x){
        int degree = 3;
        NODO_B y = new NODO_B(x.isIsLeaf());
        y.setOccupied(degree-1);
        for(int e =0;e<degree-1;e++){
            y.getShelf()[e] = x.getShelf()[e+degree];
        }
        if(x.isIsLeaf()==false){
            for(int e =0;e <degree;e++){
                y.getBranches()[e] = x.getBranches()[e+degree];
            }
        }
        x.setOccupied(degree-1);
        for(int e = occupied; e>=i+1;e--){
            branches[e+1]=branches[e];
        }
        branches[i+1] = y;
        for(int e =occupied-1;e>=i;e--){
            shelf[e+1] = shelf[e];
        }
        shelf[i]=x.getShelf()[degree-1];
        occupied++;
    }
    */
    
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
    
}
