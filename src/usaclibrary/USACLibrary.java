/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.AVL;
import EDD.BTree;
import EDD.HashTable;
import UI.AddCategory;
import UI.PrincipalMenu;
import UI.ReportManage;

/**
 *
 * @author Guillermo
 */
public class USACLibrary {
public static HashTable StudentTable;
public static AVL PublicLibrary;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StudentTable= new HashTable();
        PublicLibrary = new AVL();
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
        *//*
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
        arbol.setRoot(arbol.Add(arbol.getRoot(),"ecatepec"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"pinches"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"chilangos"));
        arbol.setRoot(arbol.Add(arbol.getRoot(),"mamones"));
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
        arbol2.Insertation(new Books(1));
        arbol2.Insertation(new Books(2));
        arbol2.Insertation(new Books(3));
        arbol2.Insertation(new Books(4));
        arbol2.Insertation(new Books(5));
        arbol2.Insertation(new Books(6));
        arbol2.Insertation(new Books(7));
        arbol2.Insertation(new Books(8));
        arbol2.Insertation(new Books(9));
        arbol2.Insertation(new Books(10));
        arbol2.Insertation(new Books(11));
        arbol2.Insertation(new Books(12));
        arbol2.Insertation(new Books(13));
        arbol2.Insertation(new Books(14));
        arbol2.Insertation(new Books(15));
        arbol2.Insertation(new Books(16));
        arbol2.Insertation(new Books(17));
        arbol2.Delete(1);
        arbol2.Delete(4);
        arbol2.Delete(10);
       arbol2.Delete(11);
        arbol2.Delete(14);
        arbol2.Insertation(new Books(18));
        arbol2.Insertation(new Books(19));
        arbol2.Insertation(new Books(20));
        arbol2.Insertation(new Books(21));
        arbol2.Insertation(new Books(22));
        arbol2.Insertation(new Books(23));
        arbol2.Insertation(new Books(24));
        arbol2.Insertation(new Books(25));
        arbol2.Insertation(new Books(26));
        arbol2.Insertation(new Books(27));
        arbol2.Insertation(new Books(28));
        arbol2.Insertation(new Books(29));
        arbol2.Insertation(new Books(30));
        arbol2.Insertation(new Books(31));
        arbol2.Insertation(new Books(32));
        arbol2.Insertation(new Books(33));
        arbol2.Insertation(new Books(34));
        arbol2.Insertation(new Books(35));
        try {            
        arbol2.GraphTree("NUMEROS");
        } catch (Exception e) {
        }
        
        PrincipalMenu begin = new PrincipalMenu();
        StudentTable.Insert(new Estudiante(201801366, "josue", "orellana", "sistemass", "1201"));
        PublicLibrary.setRoot(PublicLibrary.Add(PublicLibrary.getRoot(), "Anime", 201801366));
      //  ListaSimple<Estudiante> oj = new ListaSimple<>();
      //  oj.AddLast(new Estudiante(201801366, "josue", "orellana", "sistemass", "1201"));
        begin.show();
    }
}
