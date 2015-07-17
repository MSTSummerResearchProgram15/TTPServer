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
import java.util.*;
import static java.util.Objects.hash;
import org.bouncycastle.jce.provider.JDKMessageDigest;
import org.jdom.JDOMException;

/**
 *
 * @author macbook
 */
public class UserCreation {
    
    public byte[] hash(byte[] value){
        byte[] hash;
        MessageDigest md = new JDKMessageDigest.SHA256();
        hash = md.digest(value);
        return hash;
    }
    
    public void delete(int userid){
       
        //Create an connection to the database            
        Connection connection = null;
        PreparedStatement pstmt = null;
        
        //Delete the row which is assosiated to the userid
        try {
            String SQL = "DELETE FROM USER_TABLE WHERE userID = '"+userid+"' ";
            connection = DatabaseManager.getInstance().getConnection();           
            pstmt = connection.prepareStatement(SQL);                    
            pstmt.executeUpdate();
         
        } catch (Exception ex) {
            //Logger.getLogger(TTPServer.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
         
        finally{
            try{
                connection.close();
            }catch(Exception e1){
                System.out.println("Exception while closing connection::"+e1.getMessage());
            }
            try{
                pstmt.close();
            }catch(Exception e2){
                System.out.println("Exception while closing statement::"+e2.getMessage());
            }
        }
            
        
    }
    
    public void Register(int userid, String password, int role, int keySize){
        
        //Initialize some useful variables
        Params params;
        User owner;
        KeyGen key;
        boolean isOwner;
        ParamsGen gen = new ParamsGen();
        owner = new User();
        
        //Turn password into byte array and create a hash
        byte[] passwordBytes = password.getBytes();
        passwordBytes = hash(passwordBytes);
        
        

        switch(keySize){
            case 128: params = gen.generate(160, 512);
            	break;
            case 256: params = gen.generate(360, 1024);
            	break;
            case 512: params = gen.generate(620, 2048);
            	break;
            case 1024: params = gen.generate(1040, 4096);
            	break;
            case 2048: params = gen.generate(2080, 8192);
            	break;
            case 4096: params = gen.generate(4160, 16384);
            	break;
                    
            default: params = gen.generate(160, 512);
                break;           			
            	//If you choose anything greater than 512 it probably will never finish...
            }
        
        //generate the keys for the data owner
        key = new KeyGen(params);
        owner = key.generate(); 
            
        byte[] publicKey = owner.getPKBytes();
        byte[] privateKey = owner.getSKBytes();
        byte[] g = params.getgBytes();
        byte[] k = params.getkBytes();
        byte[] gk = params.getg_kBytes();
        byte[] zk = params.getz_kBytes();
          
          
        //Old code which can be used to Serialize/Deserialize  
        //Serializer series = new Serializer();         
        //byte[] pairingBytes = series.serialize(params.getPairing());
          
        byte[] curveParamsBytes = SerializationUtils.serialize(params.getCurveParameters());

          
        //Store all generated keys to MySQL database  
        
        //Set up the connection
        Connection connection = null;
        Statement stmt = null;
        
     
        try {
            connection = DatabaseManager.getInstance().getConnection();
            
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(userID, password, pairing, g, k, gk, zk, publicKey, secretKey, role) VALUE(?,?,?,?,?,?,?,?,?,?)");           
            
            //set values            
            pstmt.setInt(1, userid);
            pstmt.setBytes(2, passwordBytes);
            pstmt.setBytes(3, curveParamsBytes);           
            pstmt.setBytes(4, g);
            pstmt.setBytes(5, k);
            pstmt.setBytes(6, gk);
            pstmt.setBytes(7, zk);
            pstmt.setBytes(8, publicKey);
            pstmt.setBytes(9, privateKey);
            pstmt.setInt(10, role);
           
            pstmt.executeUpdate();
          
        } catch (Exception ex) {
            //Logger.getLogger(TTPServer.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
         
        finally{
            try{
                connection.close();
            }catch(Exception e1){
                System.out.println("Exception while closing connection::"+e1.getMessage());
            }
            try{
                stmt.close();
            }catch(Exception e2){
                System.out.println("Exception while closing statement::"+e2.getMessage());
            }
        }
    }
          
}
