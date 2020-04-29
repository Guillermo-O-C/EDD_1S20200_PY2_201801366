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
  /*   public NODO_B Search(NODO_B x, int ISBN){
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
    
    public void Insert(BTree x, Books Data){
        if(Search(Data.getISBN())!=null){
            //libro repetido en esta categoria
        }else{
            NODO_B rootNode = x.getRoot();
            if(rootNode.getOccupied()==2*this.order -1){/*si lo camibio a 4 luego no puede llegar en el split al 5to*/
                NODO_B newNode = new NODO_B(null);
                x.setRoot(newNode);
                newNode.setIsLeaf(false);
                newNode.setOccupied(0);
                newNode.getBranches()[0]=rootNode;
                SplitChild(newNode, 0, rootNode);
                SpacedIsert(newNode, Data);
            }else{
                SpacedIsert(rootNode, Data);
            }
           /* try {
                GraphTree("SOLIDTREE");
            } catch (IOException ex) {
                Logger.getLogger(BTree.class.getName()).log(Level.SEVERE, null, ex);
            }*/
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
                    labels+="<f"+Integer.toString(i)+">"+Integer.toString(Central.getShelf()[i].getISBN())+"|";
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
        if(root==null){
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
    }
}
