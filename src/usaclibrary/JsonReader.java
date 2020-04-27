/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                        , temporalObj.get("Carrera").toString(), temporalObj.get("Password").toString()));
            }
        } catch (org.json.simple.parser.ParseException ex) {
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
         //   Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }   
}


