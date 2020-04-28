/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usaclibrary;

import EDD.BTree;
import EDD.ListaSimple;
import EDD.NODO_AVL;
import EDD.NODO_B;
import UI.BookDisplay;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Guillermo
 */
public class BookList extends javax.swing.JFrame {
    DefaultListModel list;
    String categoria;
    int currentUser;
    ListaSimple<Books> booksInShelf;
    /**
     * Creates new form BookList
     */
    public BookList() {
    }
    public BookList(int carne, String categoria) {
        this.list = new DefaultListModel();
        this.categoria=categoria;
        this.booksInShelf = new ListaSimple<>();
        this.currentUser=carne;
        initComponents();
        list.clear();
        ListBooks(carne, categoria);
        jList1.setModel(list);
    }
    void ListBooks(int carne, String categoria){        
            NODO_AVL x =  usaclibrary.USACLibrary.PublicLibrary.Search(usaclibrary.USACLibrary.PublicLibrary.getRoot(), categoria);
            NODO_B y = x.getColeccion().getRoot();
            PrintTree(y);
            if(x.getAllowedToDelete()==carne){
                jButton1.setVisible(true);
            }else{
                jButton1.setVisible(false);
            }
            /*ListaSimple<NODO_B> branchList = new ListaSimple<>();
            branchList.AddLast(y);
            while(branchList)
            ListaSimple<Books> AllBooks = new ListaSimple<>();
            for(int i =0;i<5;i++){
                if(y.getShelf()[i]!=null){
                    AllBooks.AddLast(y.getShelf()[i]);
                }
            }*/
            
    }
    void PrintTree(NODO_B x){
        for(int i =0;i<5;i++){
                if(x.getShelf()[i]!=null){
                    booksInShelf.AddLast(x.getShelf()[i]);
                    list.addElement(x.getShelf()[i].getTitulo());
                }
            }
        for(int i =0;i<5;i++){
            if(x.getBranches()[i]!=null){
                PrintTree(x.getBranches()[i]);
            }            
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Eliminar Categoría");

        jLabel1.setText("Actualizar Lista");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(312, 312, 312))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        NODO_AVL x = USACLibrary.PublicLibrary.Search(USACLibrary.PublicLibrary.getRoot(), categoria);
        String Chosen = jList1.getSelectedValue().toString();
        for(int i =0; i< booksInShelf.getSize();i++){
            String title = booksInShelf.elementAt(i).getTitulo();
            if(title.compareTo(Chosen)==0){
                BookDisplay showBook = new BookDisplay(this.currentUser, booksInShelf.elementAt(i));
                showBook.show();
                break;
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:        
        list.clear();
        ListBooks(this.currentUser, categoria);
        jList1.setModel(list);
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
