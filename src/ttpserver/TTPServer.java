/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttpserver;


import org.apache.commons.lang3.*;
import java.io.*;
import java.net.*;
import databasemanager.*;
import java.security.MessageDigest;
import java.sql.*;
import org.bouncycastle.jce.provider.JDKMessageDigest;
import org.jdom.JDOMException;

public class TTPServer{
    
    public byte[] hash(byte[] value){
        byte[] hash;
        MessageDigest md = new JDKMessageDigest.SHA256();
        hash = md.digest(value);
        return hash;
    }
    
    public boolean verifyUser(int userID, String password){
           byte[]  mypassword = password.getBytes();
           mypassword = hash(mypassword);
           
           
        return false;      
    }

    
    public static void main(String[] args){
        
        //start the socket server and wait for incomming data
        SocketServer server = new SocketServer();
                
        UserCreation newUser = new UserCreation();       
        //newUser.delete(1);
        //newUser.Register();
        
    
    }  
}
