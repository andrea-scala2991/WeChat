/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.modelli;

import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrea
 */
public class ChatCorrente extends javax.swing.JPanel {
    int codchat,codutentemittente,codutentedest;
    Socket sock;
    BufferedReader input;
    PrintWriter output;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document d;
    TransformerFactory transformerFactory;
    Transformer transformer;
    DOMSource source;
    StreamResult result;
    NodeList nl;
    String username,contenuto;
    Timestamp data_ora;
    Element root,tipo,cod_chat;
    JPanel pc;

    ChatCorrente(int codchat,int codutente,Socket sock,JPanel pannellochat) throws UnknownHostException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException {
        initComponents();
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane3.getVerticalScrollBar().setSize(2,this.getHeight());
        //inizializzazione variabili
        lista_mess.setLayout(new BoxLayout(lista_mess, BoxLayout.Y_AXIS));
        this.codchat=codchat;
        this.codutentemittente=codutente;
        this.sock=sock;
        this.pc=pannellochat;
        input = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        output= new PrintWriter(this.sock.getOutputStream(),true);
        dbf = DocumentBuilderFactory.newInstance();
        db=dbf.newDocumentBuilder();
        transformerFactory= TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        result =  new StreamResult(output);
        visualizzaMessaggi();
        //new ControllaMessaggi(this.lista_mess,this);
    }
    public void visualizzaMessaggi(){
        try {
            //creazione file xml
            d=db.newDocument();
            root=d.createElement("richiesta");
            d.appendChild(root);
            
            tipo=d.createElement("tipo");
            tipo.appendChild(d.createTextNode("elenca_messaggi"));
            root.appendChild(tipo);
            
            cod_chat=d.createElement("codchat");
            cod_chat.appendChild(d.createTextNode(Integer.toString(this.codchat)));
            root.appendChild(cod_chat);
            
            source = new DOMSource(d);
            result =  new StreamResult(output);
            transformer.transform(source,result);
            output.println();
            output.flush();
            
            d=db.parse(new InputSource(new StringReader(input.readLine())));
            nl=d.getElementsByTagName("messaggio");
            
            for(int x=0;x<nl.getLength();x++){
                this.codutentedest=Integer.parseInt(nl.item(x).getChildNodes().item(0).getTextContent());
                this.username=nl.item(x).getChildNodes().item(1).getTextContent();
                contenuto=nl.item(x).getChildNodes().item(2).getTextContent();
                data_ora=Timestamp.valueOf(nl.item(x).getChildNodes().item(3).getTextContent());
                Messaggio m = (Messaggio) lista_mess.add(new Messaggio(this.codutentemittente,this.username,contenuto,data_ora));
                if(codutentemittente != this.codutentedest)
                    m.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
            pc.repaint();
            pc.updateUI();
        } catch (TransformerException ex) {
            Logger.getLogger(ChatCorrente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatCorrente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ChatCorrente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ins_mess = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lista_mess = new javax.swing.JPanel();

        setBackground(java.awt.Color.lightGray);
        setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        setPreferredSize(new java.awt.Dimension(740, 580));

        jScrollPane1.setBackground(java.awt.Color.orange);

        ins_mess.setColumns(20);
        ins_mess.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        ins_mess.setLineWrap(true);
        ins_mess.setRows(5);
        ins_mess.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ins_messKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(ins_mess);

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jButton1.setText("Invia");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jScrollPane3.setHorizontalScrollBar(null);

        javax.swing.GroupLayout lista_messLayout = new javax.swing.GroupLayout(lista_mess);
        lista_mess.setLayout(lista_messLayout);
        lista_messLayout.setHorizontalGroup(
            lista_messLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 737, Short.MAX_VALUE)
        );
        lista_messLayout.setVerticalGroup(
            lista_messLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 531, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(lista_mess);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       mandaMessaggio();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ins_messKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ins_messKeyPressed
        if(evt.getKeyChar()== KeyEvent.VK_ENTER){
            mandaMessaggio();
        }
    }//GEN-LAST:event_ins_messKeyPressed

    private void mandaMessaggio(){
        if (!ins_mess.getText().trim().isEmpty()){
           try {
               Element root,tipo,data_ora,contenuto,cod_utente,cod_chat;
               //creazione file xml
               d=db.newDocument();
               root=d.createElement("richiesta");
               d.appendChild(root);
               
               tipo=d.createElement("tipo");
               tipo.appendChild(d.createTextNode("manda_messaggio"));
               root.appendChild(tipo);
               
               data_ora=d.createElement("data_ora");
               data_ora.appendChild(d.createTextNode(LocalDateTime.now().toString()));
               root.appendChild(data_ora);
               
               contenuto=d.createElement("contenuto");
               contenuto.appendChild(d.createTextNode(ins_mess.getText().trim()));
               root.appendChild(contenuto);
               
               cod_utente=d.createElement("codutente");
               cod_utente.appendChild(d.createTextNode(Integer.toString(codutentemittente)));
               root.appendChild(cod_utente);
               
               cod_chat=d.createElement("codchat");
               cod_chat.appendChild(d.createTextNode(Integer.toString(codchat)));
               root.appendChild(cod_chat);
               //manda documento al server
               source = new DOMSource(d);
               result =  new StreamResult(output);
               //result =  new StreamResult(System.out);
               transformer.transform(source,result);
               output.println();
               output.flush();
               ins_mess.setText("");
               this.lista_mess.removeAll();
               visualizzaMessaggi();
           } catch (TransformerConfigurationException ex) {
               Logger.getLogger(ChatCorrente.class.getName()).log(Level.SEVERE, null, ex);
           } catch (TransformerException ex) {
               Logger.getLogger(ChatCorrente.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ins_mess;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel lista_mess;
    // End of variables declaration//GEN-END:variables
 
}

class ControllaMessaggi extends Thread{
    JPanel lista_chat;
    ChatCorrente cc;
    
    ControllaMessaggi(JPanel lischat,ChatCorrente cc){
        this.lista_chat=lischat;
        this.cc=cc;
        start();
    }
    
    @Override
    public void run(){
            try {
            while(true){
                Thread.sleep(1000);
                lista_chat.removeAll();
                cc.visualizzaMessaggi();
            }
            } catch (InterruptedException ex) {
                this.interrupt();
            }
    }
}