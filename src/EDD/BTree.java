/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import usaclibrary.Books;

/**
 *
 * @author Guillermo
 */
public class BTree {
    static int order;
    NODO_B root;
    int size;
    public BTree(int order) {
        this.order=order;
        this.root = new NODO_B();
    }

    public NODO_B getRoot() {
        return root;
    }

    public void setRoot(NODO_B root) {
        this.root = root;
    }

    public Books Search(int ISBN){
        if(this.root.getOccupied()==0){
            return null;
        }else{
            return this.root.Search(ISBN);
        }
    }
    
    public NODO_B UpdateBook(Books Data){
        if(this.root==null){
            return null;
        }else{
            return this.root.UpdateBook(Data);
        }
    }
   
    public void SplitChild(NODO_B x, int i, NODO_B y){
        NODO_B z = new NODO_B();
        z.setIsLeaf(y.isIsLeaf());
        z.setOccupied(this.order-1);
        for(int e =0;e<this.order-1;e++){
            z.getShelf()[e]=y.getShelf()[e+this.order];
        }
        if(y.isIsLeaf()==false){
            int e;
            for(e =0;e<this.order;e++){
                z.getBranches()[e]=y.getBranches()[e+this.order];
            }//added
            for(int w=e;w<this.order;w++){
                z.getBranches()[w]=null;
            }
        }
        y.setOccupied(order-1);
        int w=0;
        for(int e=x.getOccupied();e>i;e--){
            x.getBranches()[e+1]=x.getBranches()[e];
            w=e;
        }
        x.getBranches()[i+1]=z;
        for(int e= x.getOccupied();e>i;e--){
            x.getShelf()[e+1] = x.getShelf()[e];
        }
        x.getShelf()[i] = y.getShelf()[this.order-1];
        y.getShelf()[this.order-1]=null;
        for(int e =0;e<this.order-1;e++){
            y.getShelf()[e+this.order]=null;
        }        
        for(int e =5; e>this.order;e--){//deleting branches from splited node when it's not a leaf
            y.getBranches()[e-1]=null;
        }
        x.setOccupied(x.getOccupied()+1);
    }
    
    public void SpacedIsert(NODO_B x, Books Data){
        int i = x.getOccupied();
        if(x.isIsLeaf()){
            while(i>=1 && Data.getISBN() < GetISBN(x.getShelf(), i-1)){
                x.getShelf()[i]=x.getShelf()[i-1];
                i--;
            }
            x.getShelf()[i]= Data;
            x.setOccupied(x.getOccupied()+1);
        }else{
            int e =0;
            while(e<x.getOccupied() && Data.getISBN() > GetISBN(x.getShelf(), e)){
                e++;
            }
            if(x.getBranches()[e].getOccupied() == this.order*2-1){
                SplitChild(x, e, x.getBranches()[e]);
                if(Data.getISBN() > GetISBN(x.getShelf(), e)){
                    e++;
                }
            }
            SpacedIsert(x.getBranches()[e], Data);
        }
    }
    
    public int GetISBN(Books[] x, int y){
        if(x[y]==null){
            return 0;
        }
        return x[y].getISBN();
    }
    public void Insertation(Books x){
        if(this.root.getOccupied()==0){
            //root's empty
            this.root = new NODO_B();
            this.root.getShelf()[0]=x;
            this.root.addToOccupied();
        }else{
            this.root.SpacedIncert(x);
            if(this.root.getOccupied()==5){
                NODO_B newRoot = new NODO_B();
                newRoot.setIsLeaf(false);
                newRoot.getBranches()[0]=root;
                newRoot.split(0, this.root);
                this.root=newRoot;
            }//else{
                
         //   }
        }
        size++;
           try {
                GraphTree("SOLIDTREE");
            } catch (IOException ex) {
                Logger.getLogger(BTree.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void GraphTree(String name) throws IOException{
        String head = "digraph G {\n node [shape = record,height=.1];";  
        labels="";
        head += NextNodos(this.root)+labels+"}";
        writeDOC(head, name);
        try {            
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", "Graphics\\"+name+".png","TextFiles\\"+name+".dot");
            pbuilder.redirectErrorStream(true);
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    String labels;
    
    public String NextNodos(NODO_B Central){
        if(Central!=null){
            if(Central.getShelf()[0]!=null){
                labels +=Integer.toString(Central.getShelf()[0].getISBN())+"[label = \"";
            }            
            for(int i =0;i<Central.getOccupied();i++){
                if(Central.getShelf()[i]!=null){
                    labels+="<f"+Integer.toString(i)+">"+Central.getShelf()[i].getTitulo()+"\\n"+Integer.toString(Central.getShelf()[i].getISBN())+"|";
                }
            }
            labels+="\"];\n";
            String content="";
            for(int i =0;i<Central.getOccupied()+1;i++){
                if(Central.getBranches()[i]!=null){
                    content+=Integer.toString(Central.getShelf()[0].getISBN())+"->"+Integer.toString(Central.getBranches()[i].getShelf()[0].getISBN())+";\n";
                    content+=NextNodos(Central.getBranches()[i]);
                }
            }
            return content; 
        }
         return "";
    }
    
    public static void writeDOC(String doc, String name) throws IOException{
        File dir = new File("Graphics");
        dir.mkdirs();
        dir = new File("TextFiles");
        dir.mkdirs();            
        File temporal = new File(dir, name+".dot");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(doc);
        }
        temporal.createNewFile();
    }
    
    public void travel(){
        if(this.root!=null){
            this.root.travel();
        }
    }

    public void Delete(int ISBN){
        if(this.root.getOccupied()==0){//before was compared with null
            return;
        }
        this.root.delete(ISBN);
        if(this.root.getOccupied()==0){
            NODO_B x=this.root;
            if(this.root.isIsLeaf()){
                this.root=null;
            }else{
                this.root=root.getBranches()[0];
            }
        }
        size--;
    }
    
    public void AddUserBooks(ListaSimple<Books> x, NODO_B Central, int carne){
        if(Central!=null){
            for(int i =0;i<Central.getOccupied();i++){
                if(Central.getShelf()[i]!=null){
                    if(Central.getShelf()[i].getUsuario()==carne){
                        x.AddLast(Central.getShelf()[i]);
                    }
                }
            }
            for(int i =0;i<Central.getOccupied()+1;i++){
                if(Central.getBranches()[i]!=null){
                    AddUserBooks(x, Central.getBranches()[i], carne);
                }
            }
        }
    }
    
    public void SearchAlikeTitle(NODO_B Central, String entry, ListaSimple<Books> x){
        if(Central!=null){   
            for(int i =0;i<Central.getOccupied();i++){
                if(Central.getShelf()[i]!=null){
                 //   System.out.println("¿"+Central.getShelf()[i].getTitulo()+" contiene "+entry+"?");
                    if(Central.getShelf()[i].getTitulo().toLowerCase().contains(entry.toLowerCase())){
                        x.AddLast(Central.getShelf()[i]);
                    }
                }
            }
            for(int i =0;i<Central.getOccupied()+1;i++){
                if(Central.getBranches()[i]!=null){
                   SearchAlikeTitle(Central.getBranches()[i], entry, x);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
}
