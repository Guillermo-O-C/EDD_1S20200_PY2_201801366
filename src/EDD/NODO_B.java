/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import org.json.simple.JSONObject;
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

    public NODO_B() {
        this.order=5;
        this.shelf = new Books[5];
        this.isLeaf = true;
        this.branches =  new NODO_B[6];
        this.occupied=0;
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
    
    public void addToOccupied(){
        this.occupied++;
    }

    public void substractToOccupied(){
        this.occupied--;
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
    }
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
    void SpacedIncert(Books x){
        AddBooksToblock(x);
        int i=occupied-1;//más a la derecha
        if(this.isLeaf){
            while(i>=0 && GetISBN(shelf, i)>x.getISBN())
            {
                shelf[i+1]=shelf[i];
                i--;
            }
            shelf[i+1]=x;
            this.occupied++;
        }else{
            while(i>=0 && GetISBN(shelf, i) > x.getISBN()){
                i--;
            }
          
            branches[i+1].SpacedIncert(x);
            if( branches[i+1].getOccupied()==5){
                split(i+1, branches[i+1]);
            }
            
            
        }
    }
    void SplitWatcher(NODO_B x){
        if(x.getOccupied()==5){
            
        }
    }
    void split(int i, NODO_B y){
        NODO_B z = new NODO_B();
        z.setOccupied(2);//assing that is going to store 2 books
        for(int e =0;e<2;e++){
            z.getShelf()[e]=y.getShelf()[e+3];
        }
        if(y.isIsLeaf()==false){
            for(int e=0; e<3;e++){
                z.setIsLeaf(false);
                z.getBranches()[e]=y.getBranches()[e+3];
                //code for deleting the other children
            }
        }
        y.setOccupied(2);
        for(int e=occupied;e>=i+1;e--){
            branches[e+1] = branches[e];
        }          
        branches[i+1]=z;
        for(int e=occupied-1;e>=i;e--){
            shelf[e+1]= shelf[e];
        }
        shelf[i]=y.getShelf()[2];
        occupied++;        
    }
    //my code:?
    
    int t = 3;//I'm not sure about the 3 used
    void delete(int ISBN)    {
        int x = FindBook(ISBN);
        if(x<occupied && GetISBN(shelf, x)==ISBN){
            DeleteBookFromblock(shelf[x]);
            if(isLeaf){
                LeafDelete(x);
            }else{
                BranchDelete(x);
            }
        }else{
            if(isLeaf){
                //didn't found the ISBN
                return;
            }
            boolean y;
            y = x==occupied;
            if(branches[x].getOccupied()<t){
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
        if(branches[x].getOccupied()>=t){
            Books before = getBefore(x);
            shelf[x]=before;
            branches[x].delete(before.getISBN());
        }else if(branches[x+1].getOccupied()>=t){
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
        if(x!=0 && branches[x-1].getOccupied()>=t){
            useFromBefore(x);
        }else if(x!=occupied && branches[x+1].getOccupied()>=t){
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
        son.addToOccupied();
        brother.substractToOccupied();
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
        son.addToOccupied();
        brother.substractToOccupied();
    }
    
    void merge(int x){
        NODO_B son = branches[x];
        NODO_B brother = branches[x+1];
        son.getShelf()[t-1]= shelf[x];
        for(int i =0;i<brother.getOccupied();i++){
            son.getShelf()[i+t]=brother.getShelf()[i];
        }
        if(son.isIsLeaf()==false){
            for(int i=0;i<=brother.getOccupied();i++){
                son.getBranches()[i+t]=brother.getBranches()[i];
                //idk si va codigo de limpiar branches aquí
            }
        }
        for(int i=x+1;i<occupied;i++){
            shelf[i-1]=shelf[i];
        }
        for(int i =x+2;i<=occupied;i++){
            branches[i-1]=branches[i];
        }
        son.setOccupied(son.getOccupied()+brother.getOccupied()+1);
        occupied--;
    }
    
     private void AddBooksToblock(Books x){
        JSONObject temp = new JSONObject();
        JSONObject temporal = new JSONObject();
        temporal.put("ISBN", x.getISBN());
        temporal.put("Año", x.getAnio());
        temporal.put("Idioma", x.getIdioma());
        temporal.put("Titulo", x.getTitulo());
        temporal.put("Editorial", x.getEditorial());
        temporal.put("Autor", x.getAutor());
        temporal.put("Edicion", x.getEdicion());
        temporal.put("Categoria", x.getCategoria());
        temporal.put("Usuario", x.getUsuario());
        temp.put("CREAR_LIBRO", temporal);
        usaclibrary.USACLibrary.CurrentBlockData.add(temp);
    }
    private void DeleteBookFromblock(Books x){
        JSONObject temp = new JSONObject();
        JSONObject temporal = new JSONObject();
        temporal.put("ISBN", x.getISBN());
        temporal.put("Titulo", x.getTitulo());
        temporal.put("Categoria", x.getCategoria());
        temp.put("ElIMINAR_LIBRO", temporal);
        usaclibrary.USACLibrary.CurrentBlockData.add(temp);
    }
}
