/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.AVL;
import EDD.HashTable;
import EDD.Nodo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Guillermo
 */
public class Server extends Thread{
    private int puerto;
    public Server(int puerto){
        this.puerto=puerto;
    }
    @Override 
    public void run(){
        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;        
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor Iniciado");            
            while(true){
                sc = servidor.accept();//se queda a la espera en esta línea
                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                
                String mensaje = in.readUTF();//se queda a la espera de que el cliente mande algo
                System.out.println(mensaje);
                if(mensaje.compareToIgnoreCase("RETURN_IPS")==0){
                    Nodo<String> b = USACLibrary.Nodos.getHead();
                    String output ="";
                    while(b!=null){
                        output+=b.getValue()+",";
                        b=b.getRight();
                    }
                    out.writeUTF(output);
                }else{
                    JsonReader.proofOfWork(mensaje);
                }                
                sc.close();
                System.out.println("Cliente desconectado");
            }
            
        } catch (Exception e) {
        }
    }
   /* public Server(){
    //Se crea un hilo para escuchar en cierto puerto
    Thread hilo = new Thread(this);
    hilo.start();
    }

    
    //Metodo que realiza el listen
    @Override
    public void run() {  
        try {
            ServerSocket rc = new ServerSocket(5000);
            try (Socket msc = rc.accept()) {
                DataInputStream flujo_entrada = new DataInputStream(msc.getInputStream());
                String respuesta = flujo_entrada.readUTF();
                //ejecutar acciones sobre cadena
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
}
