/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttpserver;

import java.io.*;
import java.net.*;

import databasemanager.*;
import java.security.MessageDigest;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;
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
    
    public void Register(int userid, String password, int role, int keySize, Params params){
        
        
        //Initialize some useful variables
        User owner;
        KeyGen key;
        boolean isOwner;
        owner = new User();
        
        //Turn password into byte array and create a hash
        byte[] passwordBytes = password.getBytes();
        passwordBytes = hash(passwordBytes);
        
        //generate the keys for the data owner
        key = new KeyGen(params);
        owner = key.generate(); 
            
        byte[] publicKey = owner.getPKBytes();
        byte[] privateKey = owner.getSKBytes();
          
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
            stmt = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(userID, password, pairing, publicKey, secretKey, role) VALUE(?,?,?,?,?,?)");           
            
            //set values            
            pstmt.setInt(1, userid);
            pstmt.setBytes(2, passwordBytes);
            pstmt.setBytes(3, curveParamsBytes);           
            pstmt.setBytes(4, publicKey);
            pstmt.setBytes(5, privateKey);
            pstmt.setInt(6, role);
           
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
        
    public void RegisterUser(int userid, String password){
        byte[] userCurveParams = null;
        byte[] userG = null;
        byte[] userK = null;
        byte[] userGK = null;
        byte[] userZK = null;
        byte[] pw = password.getBytes();
        KeyGen key;
                      
        try {
            // Quan do this
            //yeah right
            
            //Pull common values (g, k, gk, zk and curveparams) from database
            DatabaseGetSet DB = new DatabaseGetSet();
            userCurveParams = DB.getCurveParams(1);
            userG = DB.getG(1);
            userK = DB.getK(1);
            userGK = DB.getGK(1);
            userZK = DB.getZK(1);
            
            //Now create new user lalala
            DB.setUserID(userid);
            DB.setPassword(pw);
            DB.setCurveParams(userCurveParams);
            DB.setRole(1);
            DB.setG(userG);
            DB.setK(userK);
            DB.setGK(userGK);
            DB.setZK(userZK);
            

            
            
            
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(UserCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
          
}
