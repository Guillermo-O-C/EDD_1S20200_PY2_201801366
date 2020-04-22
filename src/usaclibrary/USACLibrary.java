/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.AVL;
import EDD.ListaDoble;
import EDD.ListaSimple;

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
        arbol.Add("hacer");*/
        try {
            
        arbol.GraphTree();
        } catch (Exception e) {
        }
    }
    
}
