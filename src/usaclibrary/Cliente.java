/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.Nodo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guillermo
 */
public class Cliente extends Thread{
    private int puerto;
    private String mensaje;
    public Cliente(int puerto, String mensaje){
        this.puerto =puerto;
        this.mensaje=mensaje;
    }
    
    @Override 
    public void run(){    
        final String HOST ="127.0.0.1";
        DataInputStream in;
        DataOutputStream out;
        Nodo<String> t = USACLibrary.Nodos.getHead().getRight();
        while(t!=null){ 
            try {
                System.out.println("Conectando con "+t.getValue()+" enviando "+mensaje);
                try (Socket sc = new Socket(HOST, Integer.parseInt(t.getValue()))) {
                    in = new DataInputStream(sc.getInputStream());
                    out = new DataOutputStream(sc.getOutputStream());
                    out.writeUTF(mensaje);
                    String[] arg = mensaje.split(";");
                    if(arg[0].compareToIgnoreCase("RETURN_IPS")==0){
                        String entry = in.readUTF();
                        System.out.println(entry);
                        String[] x = entry.split(";");
                        for(int i =1;i<x.length-2;i++){
                            USACLibrary.Nodos.AddLast(x[i]);
                        }
                        System.out.println("Blocks Obteined"+ x[x.length-1]);
                        String[] y = x[x.length-1].split("#@");
                        for(int i =0;i<y.length;i++){
                            File temporal = new File("", "tmp.json");
                            try (FileWriter TemporalFile = new FileWriter(temporal)) {
                                TemporalFile.write(y[i]);
                                System.out.println("Entry File is: "+y[i]);
                            }
                            JsonReader.Addupdates(temporal);
                        }
                        JOptionPane.showMessageDialog(null, "Te has unido exitosamente, estás actualizado.");
                        break;
                    }else if(arg[0].compareToIgnoreCase("BLOCK_LIST")==0){
                        String entry = in.readUTF();
                        System.out.println("Blocks Obteined"+ entry);
                        String[] y = entry.split("#@");
                        for(int i =0;i<y.length;i++){
                            File temporal = new File("", "tmp.json");
                            try (FileWriter TemporalFile = new FileWriter(temporal)) {
                                TemporalFile.write(y[i]);
                                System.out.println("Entry File is: "+y[i]);
                            }
                            JsonReader.Addupdates(temporal);
                        }
                        JOptionPane.showMessageDialog(null, "Te has unido exitosamente, estás actualizado.");
                        break;
                    }else if(puerto==1234){
                        System.out.println("Relizando Validación");
                        String entry = in.readUTF();
                        System.out.println(entry);
                    }
                }            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            t=t.getRight();
        }
        
    }
        
       
}
