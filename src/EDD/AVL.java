/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import usaclibrary.Books;

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
        //String head = "digraph G {\n nodesep=0.3;\n ranksep=0.2;\n    margin=0.1;\n   node [shape=circle];\n  edge [arrowsize=0.8];";       
        String head = "digraph G {\n nodesep=0.3;\n ranksep=0.2;\n    margin=0.1;\n node[shape=circle width=\"1.5\" height=\"1.5\" fixed=\"true\"];  edge [arrowsize=0.8];";       
        head += "\""+this.root.getValue()+"\""+NextNodos(this.root)+"}";
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
        if(Central.getLeft()!=null){
            content +="\""+Central.getValue()+"\" -> \""+Central.getLeft().getValue()+"\"[color=\"#ff0000\"];\n";//rojo
            content += NextNodos(Central.getLeft());
        }
        if(Central.getRight()!=null){
            content +="\""+Central.getValue()+"\" -> \""+Central.getRight().getValue()+"\"[color=\"#40e0d0\"];\n";//turquesa
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
    public NODO_AVL Add(NODO_AVL x, String name, int AllowedToDelete){
        if(x==null){
            return (new NODO_AVL(name, AllowedToDelete));
        }
        if(name.compareToIgnoreCase(x.getValue())<0){
            x.setLeft(Add(x.getLeft(), name, AllowedToDelete));
        }else if(name.compareToIgnoreCase(x.getValue())>0){
            x.setRight(Add(x.getRight(), name, AllowedToDelete));
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
    public NODO_AVL Search(NODO_AVL x, String name){
        if(x==null){
            return null;
        }else if(name.compareToIgnoreCase(x.getValue())<0){
            return Search(x.getLeft(), name);
        }else if(name.compareToIgnoreCase(x.getValue())>0){
            return Search(x.getRight(), name);
        }else{
            return x;
        }
    }
    NODO_AVL LeftLEaf(NODO_AVL x){
        if(x.getLeft()!=null){
            return LeftLEaf(x.getLeft());
        }
        return x;        
    }
    public NODO_AVL Delete(NODO_AVL x, String name){
        if(x == null){
            return x;
        }else if(name.compareToIgnoreCase(x.getValue())<0){
            x.setLeft(Delete(x.getLeft(), name));
        }else if(name.compareToIgnoreCase(x.getValue())>0){
            x.setRight(Delete(x.getRight(), name));
        }else{
            if(x.getLeft()==null || x.getRight()==null){
                NODO_AVL y = null;
                if(x.getLeft()==y){
                    y=x.getRight();
                }else{
                    y=x.getLeft();
                }
                if(y==null){
                    y=x;
                    x=null;
                }else{
                    x=y;
                }
            }else{
                NODO_AVL y = LeftLEaf(x.getRight());
                x.setValue(y.getValue());
                x.setRight(Delete(x.getRight(), y.getValue()));
            }
        }
        if(x==null){
            return x;
        }
        UpdateHeight(x);
        int NodeBalance = Swing(x);
        if(NodeBalance>1 && Swing(x.getLeft())>=0){
            return RightRotacion(x);
        }
        if(NodeBalance<-1 && Swing(x.getRight())<=0){
            return LeftRotacion(x);
        }        
        if(NodeBalance>1 && Swing(x.getLeft())<0){
            x.setLeft(LeftRotacion(x.getLeft()));
            return RightRotacion(x);
        }
        if(NodeBalance<-1 && Swing(x.getRight())>0){
            x.setRight(RightRotacion(x.getRight()));
            return LeftRotacion(x);
        }
        return x;
    }
    NODO_AVL LeftLeaf(NODO_AVL x){
        while(true){
            if(x.getLeft()!=null){
                x=x.getLeft();
            }else{
                break;
            }
        }
        return x;
    }
    public void PrintAllBTrees(NODO_AVL x) throws IOException{
        x.getColeccion().GraphTree(x.getValue());
        if(x.getLeft()!=null){
            PrintAllBTrees(x.getLeft());
        }
        if(x.getRight()!=null){
            PrintAllBTrees(x.getRight());
        }
    }
    public ListaSimple<Books> UserBooks(int carne){
        ListaSimple<Books> userbooks = new ListaSimple<>();
        VisitTreeNodes(userbooks, this.root, carne);
        return userbooks;
    } 
    void VisitTreeNodes(ListaSimple<Books> y, NODO_AVL x, int carne){
        x.getColeccion().AddUserBooks(y, x.getColeccion().getRoot(), carne);
        if(x.getLeft()!=null){
            VisitTreeNodes(y, x.getLeft(), carne);
        }
        if(x.getRight()!=null){
            VisitTreeNodes(y, x.getRight(), carne);
        }
    }
}
