/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.AVL;
import EDD.BTree;
import EDD.ListaDoble;
import EDD.ListaSimple;
import EDD.NODO_AVL;
import UI.AddCategory;

/**
 *
 * @author Guillermo
 */
public class USACLibrary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      //  conector c = new conector();
       // c.iniciar();
       /* ListaSimple<String> lista = new ListaSimple<String>();
        lista.AddLast("hola");
        lista.AddLast("buenos");
        lista.AddLast("días");
        ListaDoble<String> lista2 = new ListaDoble<>();
        lista2.AddLast("hello");
        lista2.AddLast("good");
        lista2.AddLast("morning");
        if("hola".compareToIgnoreCase("buenas")>0){
                    System.out.println(">0 significa que es más grande la primera");
        }
        if("buenas".compareToIgnoreCase("holas")<0){
                    System.out.println("<0 significa que es más grande la segunda");
        }
        if("hola".compareToIgnoreCase("hola")==0){
                    System.out.println("==0 significa que son iguales");
        }
        */
        AVL arbol = new AVL();
        arbol.setRoot(arbol.Add(arbol.getRoot(),"amigos"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"beuno"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"como"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"coco"));
        arbol.setRoot(arbol.Add(arbol.getRoot(), "Hola"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"buenas"));
        arbol.setRoot(arbol.Add(arbol.getRoot(), "tardes"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"mis"));
        arbol.setRoot(arbol.Add(arbol.getRoot(), "tios"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"trabajan"));
        arbol.setRoot(arbol.Add(arbol.getRoot(), "en"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"ecatepec"));/*
        arbol.Add(arbol.getRaiz(),"que");
        arbol.Add("tiene");
        arbol.Add("que");
        arbol.Add("hacer");
        arbol.setRoot(arbol.Delete(arbol.getRoot(),"coco"));
        NODO_AVL a = arbol.Search(arbol.getRoot(), "Hola");*/
        /*
        try {
            
        arbol.GraphTree();
        } catch (Exception e) {
        }
        AddCategory s = new AddCategory();
        s.show();*/
        BTree arbol2 = new BTree(3);
        arbol2.Insert(arbol2, 1, new Books(1));
        arbol2.Insert(arbol2,2, new Books(2));
        arbol2.Insert(arbol2,3, new Books(3));
        arbol2.Insert(arbol2,4, new Books(4));
        arbol2.Insert(arbol2,5, new Books(5));
        arbol2.Insert(arbol2,6, new Books(6));
        arbol2.Insert(arbol2,7, new Books(7));
        arbol2.Insert(arbol2,8, new Books(8));
        arbol2.Insert(arbol2,9, new Books(9));
        arbol2.Insert(arbol2,10, new Books(10));
        arbol2.Insert(arbol2,11, new Books(11));
        arbol2.Insert(arbol2,12, new Books(12));
        arbol2.Insert(arbol2,13, new Books(13));
        arbol2.Insert(arbol2,14, new Books(14));
        arbol2.Insert(arbol2,15, new Books(15));
        arbol2.Insert(arbol2,16, new Books(16));
        arbol2.Insert(arbol2,17, new Books(17));
        arbol2.Insert(arbol2,18, new Books(18));
        arbol2.Insert(arbol2,19, new Books(19));
        arbol2.Insert(arbol2,20, new Books(20));/*
        arbol2.Insert(arbol2,21, new Books(21));
        arbol2.Insert(arbol2,22, new Books(22));*/
        try {            
        arbol2.GraphTree();
        } catch (Exception e) {
        }
    }
    
}
