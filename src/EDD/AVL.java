/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Guillermo
 */

public class AVL {
    private NODO_AVL root;
    private int size;

    public AVL() {
        this.root =null;
        this.size = 0;
    }

    public NODO_AVL getRoot() {
        return root;
    }

    public void setRoot(NODO_AVL root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public void GraphTree() throws IOException{
        String head = "digraph G {\n nodesep=0.3;\n ranksep=0.2;\n    margin=0.1;\n   node [shape=circle];\n  edge [arrowsize=0.8];";       
        head += NextNodos(this.root)+"}";
        writeDOC(head);
        try {            
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", "Graphics\\AVL.png","TextFiles\\AVL.dot");
            pbuilder.redirectErrorStream(true);
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String NextNodos(NODO_AVL Central){
        String content="";
        content +="\""+ Central.getValue()+"\"[label=\"" +Integer.toString(Central.getBalance())+" "+Central.getValue()+"\"]";
        if(Central.getLeft()!=null){
            content +="\""+Central.getValue()+"\" -> \""+Central.getLeft().getValue()+"\";\n";
            content += NextNodos(Central.getLeft());
        }
        if(Central.getRight()!=null){
            content +="\""+Central.getValue()+"\" -> \""+Central.getRight().getValue()+"\";\n";
            content += NextNodos(Central.getRight());            
        }
        
        return content;   
    }
    
    public static void writeDOC(String doc) throws IOException{
        File dir = new File("Graphics");
        dir.mkdirs();
        dir = new File("TextFiles");
        dir.mkdirs();            
        File temporal = new File(dir, "AVL.dot");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(doc);
        }
        temporal.createNewFile();
    }
    
    int Height(NODO_AVL x){
        if(x==null){
            return 0;
        }else{
            return x.getBalance();
        }
    }
    int BiggerBetween(int a, int b){
        if(a>b){
            return a;
        }else{
            return b;
        }
    }
    void UpdateHeight(NODO_AVL x){
        x.setBalance(BiggerBetween(Height(x.getLeft()), Height(x.getRight()))+1);
    }
    NODO_AVL RightRotacion(NODO_AVL x){
        NODO_AVL y = x.getLeft();
        NODO_AVL z = y.getRight();
        y.setRight(x);
        x.setLeft(z);
        UpdateHeight(x);
        UpdateHeight(y);
        return y;
    }
    NODO_AVL LeftRotacion(NODO_AVL x){
        NODO_AVL y = x.getRight();
        NODO_AVL z = y.getLeft();
        y.setLeft(x);
        x.setRight(z);
        UpdateHeight(x);
        UpdateHeight(y);
        return y;
    }
    int Swing(NODO_AVL x){
        if(x==null){
            return 0;
        }else{
            return Height(x.getLeft())-Height(x.getRight());
        }
    }
    public NODO_AVL Add(NODO_AVL x, String name){
        if(x==null){
            return (new NODO_AVL(name));
        }
        if(name.compareToIgnoreCase(x.getValue())<0){
            x.setLeft(Add(x.getLeft(), name));
        }else if(name.compareToIgnoreCase(x.getValue())>0){
            x.setRight(Add(x.getRight(), name));
        }else{
            //son iguales
            return x;
        }
        UpdateHeight(x);
        int NodeBalance = Swing(x);
        if(NodeBalance>1 && name.compareToIgnoreCase(x.getLeft().getValue())<0){
            return RightRotacion(x);
        }
        if(NodeBalance<-1 && name.compareToIgnoreCase(x.getRight().getValue())>0){
            return LeftRotacion(x);
        }        
        if(NodeBalance>1 && name.compareToIgnoreCase(x.getLeft().getValue())>0){
            x.setLeft(LeftRotacion(x.getLeft()));
            return RightRotacion(x);
        }
        if(NodeBalance<-1 && name.compareToIgnoreCase(x.getRight().getValue())<0){
            x.setRight(RightRotacion(x.getRight()));
            return LeftRotacion(x);
        }
        return x;
    }
}
