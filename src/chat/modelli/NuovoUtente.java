/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.modelli;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
/**
 *
 * @author Andrea
 */
public class NuovoUtente extends javax.swing.JDialog {
        Socket sock;
        BufferedReader input;
	PrintWriter output;
        String s;
        int risultato;
        Element root,tipo,username,password;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document d;
        TransformerFactory transformerFactory;
        Transformer transformer;
        DOMSource source;
        StreamResult result;
        File info;
        FileWriter outputinfo;
    /**
     * Creates new form nuovo_utente
     * @param parent
     * @param modal
     * @param sock
     * @throws java.net.UnknownHostException
     */
    public NuovoUtente(java.awt.Frame parent, boolean modal,Socket sock) throws UnknownHostException, IOException {
        super(parent, modal);
        initComponents();
        this.sock=sock;     
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        input_nome = new javax.swing.JTextField();
        invia = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        input_psw = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        immagine = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        mess_errore = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("nuovo utente");
        setBackground(new java.awt.Color(0, 204, 255));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nome utente");

        input_nome.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        input_nome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        input_nome.setBorder(null);

        invia.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        invia.setText("registrati");
        invia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("password");
        jLabel2.setFocusable(false);
        jLabel2.setMaximumSize(new java.awt.Dimension(124, 32));
        jLabel2.setMinimumSize(new java.awt.Dimension(124, 32));
        jLabel2.setPreferredSize(new java.awt.Dimension(124, 32));

        input_psw.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        input_psw.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        input_psw.setBorder(null);
        input_psw.setMaximumSize(new java.awt.Dimension(124, 32));
        input_psw.setMinimumSize(new java.awt.Dimension(124, 32));
        input_psw.setPreferredSize(new java.awt.Dimension(124, 32));

        jLabel3.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("immagine di profilo");
        jLabel3.setFocusable(false);
        jLabel3.setMaximumSize(new java.awt.Dimension(124, 32));
        jLabel3.setMinimumSize(new java.awt.Dimension(124, 32));
        jLabel3.setPreferredSize(new java.awt.Dimension(124, 32));

        immagine.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        immagine.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        immagine.setToolTipText("opzionale, sarà assegnata un'mmagine di default se si lascia vuoto");
        immagine.setBorder(null);

        jScrollPane1.setBackground(new java.awt.Color(213, 217, 223));

        mess_errore.setEditable(false);
        mess_errore.setColumns(20);
        mess_errore.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        mess_errore.setLineWrap(true);
        mess_errore.setRows(5);
        mess_errore.setWrapStyleWord(true);
        jScrollPane1.setViewportView(mess_errore);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
            .addComponent(input_nome, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(input_psw, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(172, Short.MAX_VALUE)
                .addComponent(invia)
                .addContainerGap())
            .addComponent(immagine)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(input_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_psw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(immagine, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(invia)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviaActionPerformed
        //se uno dei campi è vuoto avverte
        if(input_nome.getText().isEmpty() || input_psw.getText().isEmpty()){
            mess_errore.setText("almeno uno dei due campi username o password è vuoto");
        }else{
            //manda un messaggio al server per verificare che i valori inseriti non esistano già nel database
            //le informazioni mandate sono il nome utente e la password inseriti
            try {
            //istanza socket(indirizzo IP del router di casa e porta,
            //tramite port forwarding le informazioni saranno mandate poi al server vero e proprio, con indirizzo 192.168.1.7:23405)
            input=new BufferedReader(new InputStreamReader(sock.getInputStream()));
            output=new PrintWriter(sock.getOutputStream(),true);
            try {
                db=dbf.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(NuovoUtente.class.getName()).log(Level.SEVERE, null, ex);
            }
            //creazione file xml
            d=db.newDocument();
            root = d.createElement("richiesta");
            d.appendChild(root);
            
            tipo=d.createElement("tipo");
            tipo.appendChild(d.createTextNode("login"));
            root.appendChild(tipo);
            
            username=d.createElement("username");
            username.appendChild(d.createTextNode(input_nome.getText()));
            root.appendChild(username);
            
            password=d.createElement("password");
            password.appendChild(d.createTextNode(input_psw.getText()));
            root.appendChild(password);

            //manda il file xml al server
            transformerFactory =  TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(d);
            result =  new StreamResult(output);
            transformer.transform(source,result);
            output.println();
            output.flush();
            risultato=input.read();
            if(risultato == 1){
                mess_errore.setText("nome utente o password già esistenti, riprovare con valori diversi");
            }else if(risultato == 2){
                info=new File(System.getProperty("user.home")+"/info.txt");
                outputinfo=new FileWriter(info);
                outputinfo.write(input_nome.getText()+"\n");
                outputinfo.write(input_psw.getText()+"\n");
                outputinfo.flush();
                info.setWritable(false);
                info.setReadable(true);
                info.createNewFile();
                outputinfo.close();
                this.dispose();
            }
        } catch (IOException | TransformerException ex) {
                Logger.getLogger(NuovoUtente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_inviaActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField immagine;
    private javax.swing.JTextField input_nome;
    private javax.swing.JPasswordField input_psw;
    private javax.swing.JButton invia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea mess_errore;
    // End of variables declaration//GEN-END:variables
}
