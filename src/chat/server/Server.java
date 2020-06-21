/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 *
 * @author Andrea
 */
public class Server {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     * @throws javax.xml.transform.TransformerConfigurationException
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) 
    throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException, SQLException, InterruptedException, TransformerConfigurationException {
        ServerSocket conn=new ServerSocket(23407);
        Socket client;
        HashMap<Integer,Socket> listaclient=new HashMap<>();
        Connection connection = null;
        PreparedStatement controlla_credenziali = null,manda_messaggio = null,elenca_chat = null,
                elenca_messaggi = null,inserisci_utente = null,recupera_codice = null,trova_utente = null;
        CallableStatement crea_chat = null;
        
        try {
            //connessione al database e preparazione transazioni
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/WeChat?characterEncoding=latin1&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC","root","");
            
            controlla_credenziali=connection.prepareStatement("SELECT * FROM UTENTE WHERE username=? OR password=?;");
            
            trova_utente=connection.prepareStatement("SELECT codutente FROM utente WHERE username=?");
            
            crea_chat=connection.prepareCall("CALL nuovachat(?,?)");
            
            manda_messaggio=connection.prepareStatement("INSERT INTO messaggio(data_ora,contenuto,u_codutente,u_codchat) VALUES(?,?,?,?);");
            
            recupera_codice=connection.prepareStatement("SELECT codutente FROM utente WHERE username=?");
            
            elenca_chat=connection.prepareStatement
            ("SELECT u.codutente,u.username,u.immagine,u.bio,c.nome,c.immagine,c.tipo,c.codchat"
                + " FROM utente u JOIN fapartedi f ON (u.codutente=f.codutente) JOIN chat c ON (f.codchat=c.codchat) "+
                "where c.codchat in"+
                "(Select f.codchat as cc from utente u join fapartedi f on (u.codutente=f.codutente) where u.username=?) and u.username!= ?;");
            
            elenca_messaggi=connection.prepareStatement("SELECT u.codutente,u.username,m.contenuto,m.data_ora " +
                "FROM utente u JOIN messaggio m ON(u.codutente=m.u_codutente) JOIN chat c ON (m.u_codchat=c.codchat) WHERE c.codchat=?;");
            
            inserisci_utente=connection.prepareStatement("INSERT INTO utente(username,password) VALUES(?,?);");
            connection.setAutoCommit(false);
        }catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        while(true){
            client=conn.accept();
            new EseguiRichiesta(client,connection,controlla_credenziali,manda_messaggio,
                    elenca_chat,elenca_messaggi,inserisci_utente,recupera_codice,trova_utente,crea_chat);
        }
    }
}

class EseguiRichiesta extends Thread {
    Socket client;
    BufferedReader input;
    PrintWriter output;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document d,d1;     
    Connection connection;
    PreparedStatement controlla_credenziali = null,manda_messaggio = null,elenca_chat = null,
            elenca_messaggi = null,inserisci_utente = null,recupera_codice = null,
            trova_utente = null;
    CallableStatement crea_chat = null;
    String tipo;
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer;
    DOMSource source;
    StreamResult result;
    
    EseguiRichiesta(Socket client,Connection conn,PreparedStatement controlla_credenziali,PreparedStatement manda_messaggio,
        PreparedStatement elenca_chat,PreparedStatement elenca_messaggi,PreparedStatement inserisci_utente,PreparedStatement recupera_codice,
        PreparedStatement trova_utente,CallableStatement crea_chat)
        throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException{ 
        setClient(client);
        setInput(new BufferedReader(new InputStreamReader(client.getInputStream())));
        setOutput(new PrintWriter(client.getOutputStream(),true));
        setDbf(DocumentBuilderFactory.newInstance());
        setDb(getDbf().newDocumentBuilder());
        setConnection(conn);
        this.controlla_credenziali=controlla_credenziali;
        this.manda_messaggio=manda_messaggio;
        this.elenca_chat=elenca_chat;
        this.elenca_messaggi=elenca_messaggi;
        this.inserisci_utente=inserisci_utente;
        this.recupera_codice=recupera_codice;
        this.trova_utente=trova_utente;
        this.crea_chat=crea_chat;
        transformer = transformerFactory.newTransformer();
        start();
    }
    
    @Override
    public void run(){
        String username,password = null;
        int codiceUtente;
        ResultSet rs;
        try {
            while(true) {
            setD(getDb().parse(new InputSource(new StringReader(getInput().readLine()))));
            tipo=d.getElementsByTagName("tipo").item(0).getTextContent();
            System.out.println(client.getInetAddress());
            switch(tipo){
                case "login":
                    username=d.getElementsByTagName("username").item(0).getTextContent();
                    if(d.getElementsByTagName("password").getLength()>0)
                        password=d.getElementsByTagName("password").item(0).getTextContent();
                    controlla_credenziali.setString(1, username);
                    controlla_credenziali.setString(2, password);
                    //controlla se ci sono già ennuple con username e/o password uguali
                    rs=controlla_credenziali.executeQuery();
                    if(rs.next()){
                        output.flush();
                        output.write(1);}
                    else{
                        try{
                        inserisci_utente.setString(1, username);
                        inserisci_utente.setString(2, password);
                        inserisci_utente.executeUpdate();
                        output.flush();
                        output.write(2);}
                        catch(SQLException e){
                            connection.rollback();
                            output.write(3);
                        }
                    }
                    output.flush();
                    break;
                case "recupera_codice":
                    username=(d.getElementsByTagName("username").item(0).getTextContent());
                    recupera_codice.setString(1, username);
                    rs=recupera_codice.executeQuery();
                    rs.next();
                    codiceUtente=rs.getInt(1);
                    output.write(codiceUtente);
                    output.flush();
                break;
                case "elencochat":
                    username=d.getElementsByTagName("username").item(0).getTextContent();
                    elenca_chat.setString(1, username);
                    elenca_chat.setString(2, username);
                    rs=elenca_chat.executeQuery();
                    //creazione file xml con i dati delle varie chat dell'utente
                    Element root,chat,codutente,utusername,utimmagine,utbio,chatnome,chatimmagine,chattipo,codchat;
                    d1=getDb().newDocument();
                    //elemento root
                    root=d1.createElement("Dati");
                    d1.appendChild(root);
                    while(rs.next()){
                        //inserimento di un elemento "chat" con i suoi elementi figli(username,immagine,bio,nome chat,immagine chat,tipo chat[di gruppo o privata])
                        chat=d1.createElement("chat");
                        root.appendChild(chat);
                        
                        codutente=d1.createElement("codutente");
                        codutente.appendChild(d1.createTextNode(Integer.toString(rs.getInt(1))));
                        chat.appendChild(codutente);
                        
                        utusername=d1.createElement("username");
                        utusername.appendChild(d1.createTextNode(rs.getString(2)));
                        chat.appendChild(utusername);
                        
                        utimmagine=d1.createElement("immagine");
                        if(rs.getBlob(3)!=null)
                        utimmagine.appendChild(d1.createTextNode(rs.getBlob(3).toString()));
                        else
                            utimmagine.appendChild(d1.createTextNode(""));
                        chat.appendChild(utimmagine);
                        
                        utbio=d1.createElement("bio");
                        if(rs.getString(4)!=null)
                            utbio.appendChild(d1.createTextNode(rs.getString(4)));
                        else
                            utbio.appendChild(d1.createTextNode(""));
                        chat.appendChild(utbio);
                        
                        chatnome=d1.createElement("nomechat");
                        if(rs.getString(5)!=null)
                            chatnome.appendChild(d1.createTextNode(rs.getString(5)));
                        else
                            chatnome.appendChild(d1.createTextNode(""));
                        chat.appendChild(chatnome);
                        
                        chatimmagine=d1.createElement("immaginechat");
                        if(rs.getBlob(6)!=null)
                            chatimmagine.appendChild(d1.createTextNode(rs.getBlob(6).toString()));
                        else
                            chatimmagine.appendChild(d1.createTextNode(""));
                        chat.appendChild(chatimmagine);
                        
                        chattipo=d1.createElement("tipochat");
                        chattipo.appendChild(d1.createTextNode(Integer.toString(rs.getInt(7))));
                        chat.appendChild(chattipo);
                        
                        codchat=d1.createElement("codchat");
                        codchat.appendChild(d1.createTextNode(Integer.toString(rs.getInt(8))));
                        chat.appendChild(codchat);
                    }
                    source = new DOMSource(d1);
                    result = new StreamResult(output);
                    //StreamResult result = new StreamResult(System.out);
                    transformer.transform(source,result);
                    output.println();
                    output.flush();
                break;
                case "manda_messaggio":
                    manda_messaggio.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    manda_messaggio.setString(2, d.getElementsByTagName("contenuto").item(0).getTextContent());
                    manda_messaggio.setInt(3, Integer.parseInt(d.getElementsByTagName("codutente").item(0).getTextContent()));
                    manda_messaggio.setInt(4, Integer.parseInt(d.getElementsByTagName("codchat").item(0).getTextContent()));
                    manda_messaggio.executeUpdate();
                break;
                case "elenca_messaggi":
                    elenca_messaggi.setInt(1, Integer.parseInt(d.getElementsByTagName("codchat").item(0).getTextContent()));
                    rs = elenca_messaggi.executeQuery();
                    Element root1,messaggio,codutente1,username1,contenuto,data_ora;
                        
                    //creazione documento xml
                    d1=getDb().newDocument();
                    //elemento root
                    root1=d1.createElement("Dati");
                    d1.appendChild(root1);
                    while(rs.next()){
                        messaggio=d1.createElement("messaggio");
                        root1.appendChild(messaggio);
                        
                        codutente1=d1.createElement("codutente");
                        codutente1.appendChild(d1.createTextNode(Integer.toString(rs.getInt("codutente"))));
                        messaggio.appendChild(codutente1);
                        
                        username1=d1.createElement("username");
                        username1.appendChild(d1.createTextNode(rs.getString("username")));
                        messaggio.appendChild(username1);
                        
                        contenuto=d1.createElement("contenuto");
                        contenuto.appendChild(d1.createTextNode(rs.getString("contenuto")));
                        messaggio.appendChild(contenuto);
                        
                        data_ora=d1.createElement("data_ora");
                        data_ora.appendChild(d1.createTextNode(rs.getTimestamp("data_ora").toString()));
                        messaggio.appendChild(data_ora);
                    }
                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer();
                    source = new DOMSource(d1);
                    result = new StreamResult(output);
                    transformer.transform(source,result);
                    output.println();
                    output.flush();
                break;
                case "nuova_chat":
                    int codiceUtente2;
                    username=d.getElementsByTagName("nome_utente").item(0).getTextContent();
                    //utente richiedente una nuova chat
                    codiceUtente=Integer.parseInt(d.getElementsByTagName("codu1").item(0).getTextContent());
                    trova_utente.setString(1, username);
                    rs=trova_utente.executeQuery();
                    if(!rs.next()){
                        //in caso nessun utente sia stato trovato con quel nome, rimanda un messaggio di errore al client
                        output.println("1");
                        output.flush();
                    }
                    else{
                        codiceUtente2=rs.getInt("codutente");
                        crea_chat.setInt(1, codiceUtente);
                        crea_chat.setInt(2, codiceUtente2);
                        crea_chat.execute();
                        output.println("2");
                        output.flush();
                    }
                break;
                default:
                    
            }
            connection.commit();
            }
        } catch ( TransformerException ex) {
            Logger.getLogger(EseguiRichiesta.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException e){
            try {
                System.out.println("errore in una query.");
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(EseguiRichiesta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            this.interrupt();
            
        } catch (SAXException ex) {
            Logger.getLogger(EseguiRichiesta.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    private Socket getClient() {
        return client;
    }

    private void setClient(Socket client) {
        this.client = client;
    }

    private BufferedReader getInput() {
        return input;
    }

    private void setInput(BufferedReader input) {
        this.input = input;
    }

    public PrintWriter getOutput() {
        return output;
    }

    private void setOutput(PrintWriter output) {
        this.output = output;
    }

    private DocumentBuilderFactory getDbf() {
        return dbf;
    }

    private void setDbf(DocumentBuilderFactory dbf) {
        this.dbf = dbf;
    }

    private DocumentBuilder getDb() {
        return db;
    }

    private void setDb(DocumentBuilder db) {
        this.db = db;
    }

    public Document getD() {
        return d;
    }

    private void setD(Document d) {
        this.d = d;
    }

    private Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getControlla_credenziali() {
        return controlla_credenziali;
    }

    public void setControlla_credenziali(PreparedStatement controlla_credenziali) {
        this.controlla_credenziali = controlla_credenziali;
    }
}