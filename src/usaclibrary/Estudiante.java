/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;
import java.security.MessageDigest;
/**
 *
 * @author Guillermo
 */
public class Estudiante {
    private int carne;
    private String nombre;
    private String apellido;
    private String carrera;
    private byte[] password;

    public Estudiante(int carne, String nombre, String apellido, String carrera, String password) {
        this.carne = carne;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.password = encrypting(password);
    }

    public byte[] encrypting(String password){
        byte[] passwordBytes=null;
        try {
            passwordBytes = password.getBytes("UTF-8");
        } catch (Exception e) {
        }
        MessageDigest HashGenerator=null;
        try {
            HashGenerator =  MessageDigest.getInstance("MD5");
        } catch (Exception e) {
        }
        return HashGenerator.digest(passwordBytes);        
    }
    public int getCarne() {
        return carne;
    }

    public void setCarne(int carne) {
        this.carne = carne;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }   
}
