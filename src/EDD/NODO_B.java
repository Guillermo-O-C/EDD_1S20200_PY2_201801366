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
        while(i<this.occupied && ISBN > GetISBN(this.shelf, i)){
            i++;
        }
        if(i==5)i=4;//to avoid the array failed possition
        if(GetISBN(this.shelf, i)==ISBN){
            return this.shelf[i];
        }
        if(isIsLeaf()){
            return null;
        }
        return this.branches[i].Search(ISBN);
    }/*
    public NODO_B Search(NODO_B x, int ISBN){
        int i =0;
        while(i<x.getOccupied() && ISBN>GetISBN(x.getShelf(), i)){
            i++;
        }
        if(i<=x.getOccupied() && ISBN==GetISBN(x.getShelf(), i)){
            return x;
        }
        if(x.isIsLeaf()){
            return null;
        }else{
            return Search(x.getBranches()[i], ISBN);
        }
    }*/
    int FindBook(int ISBN){
        int x=0;
        while(x<this.occupied && GetISBN(shelf, x) < ISBN){
            x++;
        }
        return x;
    }
    int GetISBN(Books[] x, int y){
        if(x[y]==null){
            return 0;
        }
        return x[y].getISBN();
    }
    void delete(int ISBN)    {
        int x = FindBook(ISBN);
        if(x<occupied && GetISBN(shelf, x)==ISBN){
            if(isLeaf){
                LeafDelete(x);
            }else{
                BranchDelete(x);
            }
        }else{
            if(isLeaf){
                return;
            }
            boolean y;
            y = x==occupied;
            if(branches[x].getOccupied()<order){
                fill(x);
            }
            if(y && x>occupied){
                branches[x-1].delete(ISBN);
            }else{
                branches[x].delete(ISBN);
            }
        }
    }
    void LeafDelete(int x){
        for(int i =x+1;i<occupied;i++){
            shelf[i-1]=shelf[i];
        }
        occupied--;
    }
    void BranchDelete(int x){
        Books y = shelf[x];
        if(branches[x].getOccupied()>=order){
            Books before = getBefore(x);
            shelf[x]=before;
            branches[x].delete(before.getISBN());
        }else if(branches[x+1].getOccupied()>=order){
            Books next = getNext(x);
            shelf[x]=next;
            branches[x+1].delete(next.getISBN());
        }else{
            merge(x);
            branches[x].delete(y.getISBN());
        }
    }
    Books getBefore(int x){
        NODO_B y = branches[x];
        while(y.isIsLeaf()==false){
            y=y.getBranches()[y.getOccupied()];
        }
        return y.getShelf()[y.getOccupied()-1];
    } 
    Books getNext(int x){
        NODO_B y = branches[x+1];
        while(y.isIsLeaf()==false){
            y=y.getBranches()[0];
        }
        return y.getShelf()[0];
    }
    void fill(int x){
        if(x!=0 && branches[x-1].getOccupied()>=order){
            useFromBefore(x);
        }else if(x!=occupied && branches[x+1].getOccupied()>=order){
            useFromNext(x);
        }else{
            if(x!=occupied){
                merge(x);
            }else{
                merge(x-1);
            }
        }
    }
    void useFromBefore(int x){
        NODO_B son = branches[x];
        NODO_B brother = branches[x-1];
        for(int i=son.getOccupied()-1;i>=0;i--){
            son.getShelf()[i+1]=son.getShelf()[i];
        }
        if(son.isIsLeaf()==false){
            for(int i =son.getOccupied();i>=0;i--){
                son.getBranches()[i+1]=son.getBranches()[i];
                //code part where you could delte the unused branches
                //son.branches[i+1]=null;
            }
        }
        son.getShelf()[0]=shelf[x-1];
        if(son.isIsLeaf()==false){
            son.getBranches()[0]=brother.getBranches()[brother.getOccupied()];
        }
        shelf[x-1] = brother.getShelf()[brother.getOccupied()-1];
        son.setOccupied(son.getOccupied()+1);
        brother.setOccupied(brother.getOccupied()-1);
    }
    void useFromNext(int x){
        NODO_B son = branches[x];
        NODO_B brother = branches[x+1];
        son.getShelf()[son.getOccupied()]=shelf[x];
        if(son.isIsLeaf()==false){
            son.getBranches()[son.getOccupied()+1]=brother.getBranches()[0];
        }
        shelf[x]=brother.getShelf()[0];
        for(int i=1;i<brother.getOccupied();i++){
            brother.getShelf()[i-1]=brother.getShelf()[i];
        }
        if(brother.isIsLeaf()==false){
            for(int i=1;i<=brother.getOccupied();i++){
                brother.getBranches()[i-1]=brother.getBranches()[i];
                //code to dete the rest of the branche nodes;
            }
        }
        son.setOccupied(son.getOccupied()+1);
        brother.setOccupied(brother.getOccupied()-1);
    }
    void merge(int x){
        NODO_B son = branches[x];
        NODO_B brother = branches[x+1];
        son.getShelf()[order-1]= shelf[x];
        for(int i =0;i<brother.getOccupied();i++){
            son.getShelf()[i+order]=brother.getShelf()[i];
        }
        if(son.isIsLeaf()==false){
            for(int i=0;i<=brother.getOccupied();i++){
                son.getBranches()[i+order]=brother.getBranches()[i];
                //idk si va codigo de limpiar branches aquí
            }
        }
        for(int i=x+1;i<occupied;i++){
            shelf[i-1]=shelf[i];
        }
        for(int i =x+2;i<=occupied;i++){
            branches[i-1]=branches[i];
        }
        son.setOccupied(brother.getOccupied()+1);
        occupied--;
    }
    void travel(){
        int i =0;
        for(i=0;i<occupied;i++){
            if(isLeaf==false){
                branches[i].travel();
            }
        }
        if(isLeaf==false){
            branches[i].travel();
        }
    }
}
