/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.modelli;

import java.awt.Dimension;
import java.sql.Timestamp;
/**
 *
 * @author Andrea
 */
public class Messaggio extends javax.swing.JPanel {

    /**
     * Creates new form Messaggio
     */
    public Messaggio() {
        initComponents();
    }

    Messaggio(int codutente, String username, String contenuto, Timestamp data_ora) {
        initComponents();
        this.contenuto.setText(contenuto);
        this.info.setText(data_ora.toString());
        if(contenuto.length()< 50)
            this.setPreferredSize(new Dimension(300,60));
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
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        info = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        contenuto = new javax.swing.JTextArea();

        jScrollPane1.setViewportView(jTextPane1);

        setBackground(new java.awt.Color(163, 199, 255));
        setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        setMaximumSize(new java.awt.Dimension(740, 1000));
        setPreferredSize(new java.awt.Dimension(740, 90));

        jPanel1.setBackground(new java.awt.Color(163, 199, 255));

        info.setBackground(new java.awt.Color(90, 141, 221));
        info.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info.setLabelFor(contenuto);
        info.setMaximumSize(new java.awt.Dimension(534, 20));
        info.setPreferredSize(new java.awt.Dimension(534, 20));

        contenuto.setEditable(false);
        contenuto.setBackground(new java.awt.Color(163, 199, 255));
        contenuto.setColumns(20);
        contenuto.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        contenuto.setLineWrap(true);
        contenuto.setRows(5);
        contenuto.setWrapStyleWord(true);
        contenuto.setMaximumSize(new java.awt.Dimension(534, 1000));
        jScrollPane3.setViewportView(contenuto);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea contenuto;
    private javax.swing.JLabel info;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
