/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;
import java.net.*;
import java.io.*;
/**
 *
 * @author Guillermo
 */
public class conector {
    
    public conector() {
    String ip;
    }
    
    public void send(String ipDelivery, String msg){
        try {
            Socket mySocket = new Socket(ipDelivery,9999);
            try (DataOutputStream out = new DataOutputStream(mySocket.getOutputStream())) {
                out.writeUTF(msg);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }        
    }
    
    /**
     * Recibe un flujo de datos en un socket
     * @return String Recibido
     */
    
    public String receive(){
         try {
            ServerSocket rc = new ServerSocket(9999);
            String rcv;
             try (Socket msc = rc.accept()) {
                 DataInputStream in=new DataInputStream(msc.getInputStream());
                 rcv = in.readUTF();
             }
            return rcv;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "No hay respuesta...";
    }

}
