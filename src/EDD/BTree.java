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
import usaclibrary.Books;

/**
 *
 * @author Guillermo
 */
public class BTree {
    static int order;
    NODO_B root;

    public BTree(int order) {
        this.order=order;
        this.root = new NODO_B(null);
    }

    public NODO_B getRoot() {
        return root;
    }

    public void setRoot(NODO_B root) {
        this.root = root;
    }
    
    public NODO_B Search(NODO_B x, int ISBN){
        int i =0;
        while(i<this.root.getOccupied() && ISBN > this.root.getShelf()[i].getISBN()){
            i++;
        }
        if(i<=this.root.getOccupied() && ISBN == this.root.getShelf()[i].getISBN()){
            return this.root;
        }
        if(this.root.isIsLeaf()==false){
            return Search(x.getBranch(i), ISBN);
        }else{
            return null;
        }
    }
    public void SplitChild(NODO_B x, int i, NODO_B y){
        NODO_B z = new NODO_B(null);
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
    public void SpacedIsert(NODO_B x, int ISBN, Books Data){
        int i = x.getOccupied();
        if(x.isIsLeaf()){
            while(i>=1 && ISBN < GetISBN(x.getShelf(), i-1)){
                x.getShelf()[i]=x.getShelf()[i-1];
                i--;
            }
            x.getShelf()[i]= Data;
            x.setOccupied(x.getOccupied()+1);
        }else{
            int e =0;
            while(e<x.getOccupied() && ISBN > GetISBN(x.getShelf(), e)){
                e++;
            }
            if(x.getBranches()[e].getOccupied() == this.order*2-1){
                SplitChild(x, e, x.getBranches()[e]);
                if(ISBN > GetISBN(x.getShelf(), e)){
                    e++;
                }
            }
            SpacedIsert(x.getBranches()[e], ISBN, Data);
        }
    }
    public int GetISBN(Books[] x, int y){
        if(x[y]==null){
            return 0;
        }
        return x[y].getISBN();
    }
    public void Insert(BTree x, int ISBN, Books Data){
        NODO_B rootNode = x.getRoot();
        if(rootNode.getOccupied()==2*this.order -1){/*si lo camibio a 4 luego no puede llegar en el split al 5to*/
            NODO_B newNode = new NODO_B(null);
            x.setRoot(newNode);
            newNode.setIsLeaf(false);
            newNode.setOccupied(0);
            newNode.getBranches()[0]=rootNode;
            SplitChild(newNode, 0, rootNode);
            SpacedIsert(newNode, ISBN, Data);
        }else{
            SpacedIsert(rootNode, ISBN, Data);
        }
        try {
            GraphTree();
        } catch (IOException ex) {
            Logger.getLogger(BTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void GraphTree() throws IOException{
        String head = "digraph G {\n node [shape = record,height=.1];";  
        labels="";
        head += NextNodos(this.root)+labels+"}";
        writeDOC(head);
        try {            
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", "Graphics\\BTree.png","TextFiles\\BTree.dot");
            pbuilder.redirectErrorStream(true);
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String labels;
    public String NextNodos(NODO_B Central){
        labels +=Integer.toString(Central.getShelf()[0].getISBN())+"[label = \"";
        for(int i =0;i<5;i++){
            if(Central.getShelf()[i]!=null){
                labels+="<f"+Integer.toString(i)+">"+Integer.toString(Central.getShelf()[i].getISBN())+"|";
            }
        }
        labels+="\"];\n";
        String content="";
        for(int i =0;i <5;i++){
            if(Central.getBranches()[i]!=null){
                content+=Integer.toString(Central.getShelf()[0].getISBN())+"->"+Integer.toString(Central.getBranches()[i].getShelf()[0].getISBN())+";\n";
                content+=NextNodos(Central.getBranches()[i]);
            }
        }
        return content;   
    }
    
    public static void writeDOC(String doc) throws IOException{
        File dir = new File("Graphics");
        dir.mkdirs();
        dir = new File("TextFiles");
        dir.mkdirs();            
        File temporal = new File(dir, "BTree.dot");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(doc);
        }
        temporal.createNewFile();
    }
}
