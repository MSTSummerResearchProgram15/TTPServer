
package ttpserver;

import java.io.*;
import java.net.*;

public class SocketServer extends Thread{

    ServerSocket server;
    SocketServer(){
        this.start();
    }
    
    public void run() {
        try{
            server = new ServerSocket(ServerPort.port);
            Socket clientSoc = null;
            
            while(true){
                clientSoc = server.accept();
                InputStream is = clientSoc.getInputStream();
                OutputStream os = clientSoc.getOutputStream();
                
                //Specify which data to send here
            }
                
            
            
        } catch (Exception ex){
            System.out.println("Error : " + ex.getMessage());
        }
        
        
        
    }
    
}
