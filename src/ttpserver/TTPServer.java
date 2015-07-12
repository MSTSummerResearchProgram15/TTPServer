/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttpserver;

import java.io.*;
import java.net.*;
import databasemanager.*;
import java.sql.*;

public class TTPServer{
    
    public void main(String[] args){
        
        //start the socket server and wait for incomming data
        SocketServer server = new SocketServer();
        
        //Begin to connect to the MySQL database
        //Create a connection
        Connection connection;
        Statement stmt = null;
        
        //Establish the connection
        try{
            connection = DatabaseManager.getInstance().getConnection();
            stmt = connection.createStatement();
            
            //Insert data into the table using executeUpdate
            
            //Use data in the table using executeQuery
            
            //Terminate connection
            connection.close();
            connection = null;
            stmt.close();
            stmt = null;
        } catch(Exception ex){
            System.err.println("Error : "+ex.getMessage());
        }
        
        
    }
    
}
