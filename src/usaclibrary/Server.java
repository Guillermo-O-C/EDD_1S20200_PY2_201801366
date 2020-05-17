/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.AVL;
import EDD.Block;
import static EDD.Block.indexControl;
import EDD.HashTable;
import EDD.Nodo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

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
                String[] arg = mensaje.split(";");
                if(arg[0].compareToIgnoreCase("RETURN_IPS")==0){
                JOptionPane.showMessageDialog(null, "Nuevo Nodo Detectado");
                    Nodo<String> b = USACLibrary.Nodos.getHead();
                    String output ="";
                    while(b!=null){
                        output+=b.getValue();
                        output+=";";
                        b=b.getRight();
                    }
                    System.out.println(output+USACLibrary.BLOCK_String);
                    USACLibrary.Nodos.AddLast(arg[1]);
                    out.writeUTF(output+USACLibrary.BLOCK_String);
                }else if(arg[0].compareToIgnoreCase("DELETE_IP")==0){
                    String ipToDelete=arg[1];
                    System.out.println(USACLibrary.MY_IP+" will delete "+ipToDelete);
                    Nodo<String> b = USACLibrary.Nodos.getHead();
                    int w =0;
                    while(b!=null){
                        if(b.getValue().compareToIgnoreCase(ipToDelete)==0){
                            USACLibrary.Nodos.DeleteElementAt(w);
                            break;
                        }
                        w++;
                        b=b.getRight();
                    }
                    out.writeUTF("Se ha eliminado tu conexión con "+USACLibrary.MY_IP);
                }else if(arg[0].compareToIgnoreCase("BLOCK_LIST")==0){
                    out.writeUTF(USACLibrary.BLOCK_String);
                    System.out.println("BlockChain sent: "+USACLibrary.BLOCK_String);
                }else{                    
                    JOptionPane.showMessageDialog(null, "Se ha recibido un nuevo bloque, se efectuará la prueba de trabajo.");
                    JsonReader.proofOfWork(mensaje);
                    out.writeUTF("Bloque aceptado");
                }                      
                sc.close();
                System.out.println("Cliente desconectado");
            }            
        } catch (Exception e) {
        }
    }
}
