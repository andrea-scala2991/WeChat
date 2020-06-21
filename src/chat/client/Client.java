/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.client;

import chat.modelli.NuovoUtente;
import chat.modelli.SchermataIniziale;
import chat.modelli.ElencoChat;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.net.SocketException;
/**
 *
 * @author Andrea
 */
public class Client{
        

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.net.SocketException
     */
    public static void main(String[] args) throws InterruptedException, SocketException, IOException {
        Socket sock=new Socket(InetAddress.getByName("176.58.86.200"),12541);
        File f=new File(System.getProperty("user.home")+"/info.txt");
        
        SchermataIniziale si=new SchermataIniziale();
        NuovoUtente n=new NuovoUtente(si,true,sock);
        ElencoChat e=new ElencoChat(sock);
        
        si.setVisible(true);
        TimeUnit.SECONDS.sleep(3);
        if(!f.exists())
            n.setVisible(true);
        si.setVisible(false);
        e.setVisible(true);
        
    }
    
}