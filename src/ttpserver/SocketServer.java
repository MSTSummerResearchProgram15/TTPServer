
package ttpserver;

import databasemanager.DatabaseManager;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.bouncycastle.jce.provider.JDKMessageDigest;
import java.util.Arrays;

public class SocketServer extends Thread{

    ServerSocket server;
    SocketServer(){
        this.start();
    }
        
    public void run() {
        try{
            server = new ServerSocket(ServerPort.port);
            Socket clientSoc = null;
            DataInputStream input;
            PrintStream output;
            String data, usr = null, pw = null;
            boolean a = false;


            
            while(true){
                clientSoc = server.accept();

                input = new DataInputStream(clientSoc.getInputStream());
                output = new PrintStream(clientSoc.getOutputStream());
                
                while((data = input.readLine()) != null){
                    
                    if(data.startsWith("username:"))
                    {
                        usr = data.substring(9);
                    }
                    else if (data.startsWith("password:"))
                    {
                        pw = data.substring(9);

                    }  
                    
                    if(!"".equals(usr) && !"".equals(pw))
                    {
                      a = verifyUser(usr, pw);  
                    }           
                    
                    if(a == true)
                    {
                        output.write(0);
                    }
                    else 
                    {
                        output.write(1);
                    }
                }
                        System.out.println(usr);
                        System.out.println(pw);


                
                
                           
            }
            
                                      
            
        } catch (Exception ex){
            System.out.println("Error : " + ex.getMessage());
        }       
    }
    
      public byte[] hash(byte[] value){
        byte[] hash;
        MessageDigest md = new JDKMessageDigest.SHA256();
        hash = md.digest(value);
        return hash;
    }
    
    
    public boolean verifyUser(String usr, String pw){
        //hash the password    
        byte[]  mypassword = pw.getBytes();
        mypassword = hash(mypassword);
        boolean a = false;
        
        Blob myDbPw = null;
        //Convert username to int (because we stored integer)
        int user = Integer.parseInt(usr);
        
        //search the username in our database
        
        //Create an connection to the database            
        Connection connection = null;
        PreparedStatement pstmt = null;
        
        try {
            String sql = "SELECT userID, password FROM user_table WHERE userID LIKE % "+usr+" %";
            connection = DatabaseManager.getInstance().getConnection();           
            pstmt = connection.prepareStatement(sql);                    
            ResultSet result = pstmt.executeQuery();
 
            myDbPw = result.getBlob("password");
                   
         //Convert myDbPw from Blob to Byte Array
        int blobLength = (int) myDbPw.length(); 
        byte[] blobPw = myDbPw.getBytes(1, blobLength);
            
        //compare the password
        a = Arrays.equals(mypassword, blobPw);
        
        
        
        } catch (Exception ex) {
            //Logger.getLogger(TTPServer.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
   
        return a;
        }
    
}
