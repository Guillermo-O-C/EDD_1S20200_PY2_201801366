/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.Nodo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        DataInputStream in;
        DataOutputStream out;
        Nodo<String> t = USACLibrary.Nodos.getHead().getRight();
        while(t!=null){
            try {
                try (Socket sc = new Socket(t.getValue(), puerto)) {
                    in = new DataInputStream(sc.getInputStream());
                    out = new DataOutputStream(sc.getOutputStream());
                    out.writeUTF(mensaje);
                    String entry = in.readUTF();
                    System.out.println(entry);
                    if(mensaje.compareToIgnoreCase("RETURN_IPS")==0){
                        String[] x = entry.split(",");
                        for(int i =1;i<x.length;i++){
                            USACLibrary.Nodos.AddLast(x[i]);
                        }
                        break;
                    }else{
                        System.out.println("Relizando Validación");
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            t=t.getRight();
        }
        
    }
        
       
}
