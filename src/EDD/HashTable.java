/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import usaclibrary.Estudiante;

/**
 *
 * @author Guillermo
 */
public class HashTable {
    private Slot[] bucket;

    public HashTable() {
        this.bucket = new Slot[45];
    }    
    public boolean Insert(Estudiante x){
        int key = HashFunction(x.getCarne());
        if(key>=0 && key <45){
            if(this.bucket[key]==null){
                this.bucket[key]=new Slot();
            }
            if(!SearchStudent(x, this.bucket[key])){                
                this.bucket[key].getListado().AddLast(x);
                return true;
            }else{                
                JOptionPane.showMessageDialog(null, "El carné que se ha intentado ingresar ya existe.");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error en el registro, inténtalo de nuevo.");
        }
        return false;
    }
    private int HashFunction(int key){
        return key % 45;
    }
    private boolean SearchStudent(Estudiante x, Slot y){
        Nodo<Estudiante> z = y.getListado().getHead();
        while(z!=null){
            if(z.getValue().getCarne()==x.getCarne()){
                return true;
            }
            z=z.getRight();
        }
        return false;
    }
    public Estudiante SearchStudent(int carne){         
        int key = HashFunction(carne);
        if(key>=0 && key <45){
            if(this.bucket[key]!=null){
                Nodo<Estudiante> z = this.bucket[key].getListado().getHead();
                while(z!=null){
                    if(z.getValue().getCarne()==carne){
                        return z.getValue();
                    }
                    z=z.getRight();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error en el registro, inténtalo de nuevo.");
        }
        return null;
    }
    public void UpdateStudent(Estudiante x){
        int key = HashFunction(x.getCarne());
        if(key>=0 && key <45){
            if(this.bucket[key]!=null){
                Nodo<Estudiante> z = this.bucket[key].getListado().getHead();
                while(z!=null){
                    if(z.getValue().getCarne()==x.getCarne()){
                       z.setValue(x);
                    }
                    z=z.getRight();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error en el registro, inténtalo de nuevo.");
        }
    }

    public void DeleteStudent(int carne){
        int key = HashFunction(carne);
         if(key>=0 && key <45){
            if(this.bucket[key]!=null){
                ListaSimple<Estudiante> list = this.bucket[key].getListado();
                Nodo<Estudiante> z = list.getHead();
                Nodo<Estudiante> z_Prev = null;
                while(z!=null){
                if(z.getValue().getCarne()==carne){
                    if(z_Prev==null){
                        //this was the head
                        if(list.getHead()==list.getTail()){
                            //there was only one node
                            list.setHead(null);
                            list.setTail(null);
                        }else{
                            list.setHead(z.getRight());
                        } 
                    }else{
                        if(list.getTail()==z){
                            list.setTail(z_Prev);
                        }else{
                            z_Prev.setRight(z.getRight());
                        }
                    }
                    break;
                }
                z_Prev=z;
                z=z.getRight();
                }
            }
        }
    }
    public boolean LogIn(int carne, String password){
        int key = HashFunction(carne);
         if(key>=0 && key <45){
            if(this.bucket[key]==null){
                return false;
            }            
            Nodo<Estudiante> z = this.bucket[key].getListado().getHead();
            while(z!=null){
            if(z.getValue().getCarne()==carne){
                return Arrays.equals(z.getValue().getPassword(),  Estudiante.encrypting(password));
            }
            z=z.getRight();
            }
        }
        return false;
    }
    
    public String GenerateTable(){
        String graph = "digraph G { \n node[shape=diamond]\n";
        String arrayLists ="";
        String rankedList="";
        String arrayLabels="";
        boolean first=true;
        for(int i =0;i<45;i++){
            if(this.bucket[i]!=null){
                rankedList+="{rank=same "+"Slot_"+Integer.toString(i);
                arrayLabels+="Slot_"+Integer.toString(i)+"[label=\""+Integer.toString(i)+"\" shape=box];";
                if(first){
                    graph+="Slot_"+Integer.toString(i);
                    first=false;
                }else{                    
                    graph+="->Slot_"+Integer.toString(i);
                }
                Nodo<Estudiante> aux = this.bucket[i].getListado().getHead();
                while(true){
                    rankedList+=" "+Integer.toString(aux.getValue().getCarne());
                    if(aux==this.bucket[i].getListado().getHead()){
                         arrayLists+="Slot_"+Integer.toString(i)+"->"+Integer.toString(aux.getValue().getCarne())+"[constraint=true];\n";
                    }
                    if(aux.getRight()!=null){
                        arrayLists+=Integer.toString(aux.getValue().getCarne())+"->"+Integer.toString(aux.getRight().getValue().getCarne())+"[constraint=true];\n";
                        aux=aux.getRight();
                    }else{
                        rankedList+="}\n";
                        break;
                    }                    
                }
            }
        }
        return graph+";"+arrayLists+rankedList+arrayLabels+"\n}";
    }
      public void GraphTable() throws IOException{
        //String head = "digraph G {\n nodesep=0.3;\n ranksep=0.2;\n    margin=0.1;\n   node [shape=circle];\n  edge [arrowsize=0.8];";       
        String head = GenerateTable()+"}";
        writeDOC(head);
        try {            
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", "Graphics\\HashTable.png","TextFiles\\HashTable.dot");
            pbuilder.redirectErrorStream(true);
            pbuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void writeDOC(String doc) throws IOException{
        File dir = new File("Graphics");
        dir.mkdirs();
        dir = new File("TextFiles");
        dir.mkdirs();            
        File temporal = new File(dir, "HashTable.dot");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(doc);
        }
        temporal.createNewFile();
    }
}
