/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

/**
 *
 * @author Guillermo
 */
public class Books {
    int ISBN;

    public Books(int ISBN) {
        this.ISBN = ISBN;
    }
    
    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
    
}
