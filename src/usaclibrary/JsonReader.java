/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.Block;
import EDD.NODO_AVL;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
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
                        , temporalObj.get("Carrera").toString(), temporalObj.get("Password").toString()),temporalObj.get("Password").toString(), true);
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
                    USACLibrary.PublicLibrary.setRoot(USACLibrary.PublicLibrary.Add(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString(), carne, true));
                    USACLibrary.PublicLibrary.GraphTree();
                }
                    NODO_AVL x =  USACLibrary.PublicLibrary.Search(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString());
                    x.getColeccion().Insertation(new Books(
                            Integer.parseInt(temporalObj.get("ISBN").toString())
                            , temporalObj.get("Titulo").toString(), temporalObj.get("Autor").toString(), temporalObj.get("Editorial").toString()
                    , Integer.parseInt(temporalObj.get("Año").toString()), Integer.parseInt(temporalObj.get("Edicion").toString()), temporalObj.get("Categoria").toString()
                    , temporalObj.get("Idioma").toString(), carne), true);
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
    
    public static void proofOfWork(String JsonContent) throws NoSuchAlgorithmException, IOException{
        File temporal = new File("\\", "TEMPORAL.json");
        try (FileWriter TemporalFile = new FileWriter(temporal)) {
            TemporalFile.write(JsonContent);
        }
        //temporal.createNewFile();
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(temporal));
            JSONObject jsonObj = (JSONObject) obj;
            String entry = jsonObj.get("INDEX").toString()+jsonObj.get("TIMESTAMP").toString()+jsonObj.get("PREVIOUSHASH").toString()+jsonObj.get("DATA").toString();
            System.out.println("El bloque que se valida es" + entry);
            if(Block.validateBlock(entry, jsonObj.get("NONCE").toString())){
                Block.indexControl=Integer.parseInt(jsonObj.get("INDEX").toString())+1;
                JOptionPane.showMessageDialog(null, "Se ha validado el nuevo bloque");
                Block.SaveRecibedBlock(JsonContent);
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo validar el nuevo Bloque");
            }
        } catch (org.json.simple.parser.ParseException ex) {            
            JOptionPane.showMessageDialog(null, "Ha sucedido un error, inténtalo de nuevo");
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }  
    } 
    
    public static void Addupdates(File jsonFile){
         JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObj = (JSONObject) obj;
            int index = Integer.parseInt(jsonObj.get("INDEX").toString());
            if(index>Block.indexControl-1){
                JSONArray jsonArray = (JSONArray) jsonObj.get("DATA");
                for(int i =0;i<jsonArray.size();i++){
                    JSONObject temporalObj = (JSONObject) jsonArray.get(i);
                    System.out.println(temporalObj.keySet().toString());
                    JSONObject jbj = (JSONObject) temporalObj.get("CREAR_USUARIO");
                    switch(temporalObj.keySet().toString()){
                        case "[CREAR_USUARIO]":                        
                            System.out.println("creando usuario");
                            usaclibrary.USACLibrary.StudentTable.Insert(new Estudiante(Integer.parseInt(jbj.get("Carnet").toString()),
                            jbj.get("Nombre").toString(), jbj.get("Apellido").toString()
                            , jbj.get("Carrera").toString(), jbj.get("Password").toString()),jbj.get("Password").toString(), false);

                            break;
                        case "[EDITAR_USUARIO]":
                            usaclibrary.USACLibrary.StudentTable.UpdateStudent(new Estudiante(
                                            Integer.parseInt(jbj.get("Carnet").toString()),
                            jbj.get("Nombre").toString(), jbj.get("Apellido").toString()
                            , jbj.get("Carrera").toString(), jbj.get("Password").toString()), jbj.get("Password").toString(), false);
                            break;
                        case "[ELIMINAR_USUARIO]":
                            USACLibrary.StudentTable.DeleteStudent(Integer.parseInt(jbj.get("Carnet").toString()), false);
                            break;
                        case "[CREAR_CATEGORIA]":
                            USACLibrary.PublicLibrary.Add(USACLibrary.PublicLibrary.getRoot(), jbj.get("NOMBRE").toString(), Integer.parseInt(jbj.get("USUARIO").toString()), false);
                            break;
                        case "[ELIMINAR_CATEGORIA]":
                             USACLibrary.PublicLibrary.Delete(USACLibrary.PublicLibrary.getRoot(), jbj.get("NOMBRE").toString(), false);
                            break;
                        case "[CREAR_LIBRO]":
                             if(USACLibrary.PublicLibrary.Search(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString())==null){
                                USACLibrary.PublicLibrary.setRoot(USACLibrary.PublicLibrary.Add(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString(), Integer.parseInt( temporalObj.get("Usuario").toString()), true));
                                USACLibrary.PublicLibrary.GraphTree();
                                }
                                NODO_AVL x =  USACLibrary.PublicLibrary.Search(USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString());
                                x.getColeccion().Insertation(new Books(
                                        Integer.parseInt(temporalObj.get("ISBN").toString())
                                        , temporalObj.get("Titulo").toString(), temporalObj.get("Autor").toString(), temporalObj.get("Editorial").toString()
                                , Integer.parseInt(temporalObj.get("Año").toString()), Integer.parseInt(temporalObj.get("Edicion").toString()), temporalObj.get("Categoria").toString()
                                , temporalObj.get("Idioma").toString(), Integer.parseInt( temporalObj.get("Usuario").toString())), false);
                            break;
                        case "[ElIMINAR_LIBRO]":
                            NODO_AVL y = usaclibrary.USACLibrary.PublicLibrary.Search(usaclibrary.USACLibrary.PublicLibrary.getRoot(), temporalObj.get("Categoria").toString());
                            y.getColeccion().Delete(Integer.parseInt(temporalObj.get("ISBN").toString()), false);
                            break;
                    }
                }                     
                File dir = new File("BLOCKS");
                dir.mkdirs();   
                File temporal = new File(dir, "BLOCK_"+Integer.toString(index)+".json");  
                try (FileWriter TemporalFile = new FileWriter(temporal)) {
                    TemporalFile.write(jsonObj.toJSONString());
                }
                temporal.createNewFile();
                JOptionPane.showMessageDialog(null, "¡Se ha cargado correctamente el archivo!");
                Block.indexControl++;
            }
            //si no entró es poprque ya existía ese bloque.
        } catch (org.json.simple.parser.ParseException ex) {            
            JOptionPane.showMessageDialog(null, "Ha sucedido un error, inténtalo de nuevo");
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha sucedido un error, inténtalo de nuevo");
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}