/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.NODO_AVL;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Guillermo
 */
public class JsonReader {
    public static void LoadStudents(File jsonFile){
      JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObj.get("Usuarios");
            for(int i =0;i<jsonArray.size();i++){
                JSONObject temporalObj = (JSONObject) jsonArray.get(i);
                usaclibrary.USACLibrary.StudentTable.Insert(new Estudiante(Integer.parseInt(temporalObj.get("Carnet").toString()),
                        temporalObj.get("Nombre").toString(), temporalObj.get("Apellido").toString()
                        , temporalObj.get("Carrera").toString(), temporalObj.get("Password").toString()),temporalObj.get("Password").toString());
            }            
            JOptionPane.showMessageDialog(null, "¡Se ha cargado correctamente el archivo!");
        } catch (org.json.simple.parser.ParseException ex) {            
            JOptionPane.showMessageDialog(null, "Ha sucedido un error, inténtalo de nuevo");
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha sucedido un error, inténtalo de nuevo");
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }   
    public static void LoadBooks(File jsonFile, int carne){
      JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObj.get("libros");
            for(int i =0;i<jsonArray.size();i++){
                JSONObject temporalObj = (JSONObject) jsonArray.get(i);
                if(USACLibrary.PublicLibrary.Search(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString())==null){
                    USACLibrary.PublicLibrary.setRoot(USACLibrary.PublicLibrary.Add(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString(), carne));
                    USACLibrary.PublicLibrary.GraphTree();
                }
                    NODO_AVL x =  USACLibrary.PublicLibrary.Search(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString());
                    x.getColeccion().Insertation(new Books(
                            Integer.parseInt(temporalObj.get("ISBN").toString())
                            , temporalObj.get("Titulo").toString(), temporalObj.get("Autor").toString(), temporalObj.get("Editorial").toString()
                    , Integer.parseInt(temporalObj.get("Año").toString()), Integer.parseInt(temporalObj.get("Edicion").toString()), temporalObj.get("Categoria").toString()
                    , temporalObj.get("Idioma").toString(), carne));
                }
        } catch (org.json.simple.parser.ParseException ex) {
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            USACLibrary.PublicLibrary.PrintAllBTrees(USACLibrary.PublicLibrary.getRoot());
        } catch (IOException e) {
        }
    } 
}