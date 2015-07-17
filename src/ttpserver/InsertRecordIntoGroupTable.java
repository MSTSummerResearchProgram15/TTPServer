
package ttpserver;

import java.io.*;
import java.net.*;
import databasemanager.*;
import java.sql.*;
import org.jdom.JDOMException;

public class InsertRecordIntoGroupTable{
    

 
    public void run() throws SQLException, JDOMException{
        Connection connection = null;
        Statement stmt = null;
 
        String insertTableSQL = "INSERT INTO GROUP_TABLE"
				+ "(groupID, userID) " + "VALUES"
				+ "(1, 999))";
    
        try{
	connection = DatabaseManager.getInstance().getConnection();
        stmt = connection.createStatement();
 
	System.out.println(insertTableSQL);
 
	// execute insert SQL statement
	stmt.executeUpdate(insertTableSQL);
 
	System.out.println("Record is inserted into group table!");
 
    } catch(Exception ex){
          System.err.println("Error : "+ex.getMessage());
    } finally 
        {
            if (stmt != null) 
                {
                    stmt.close();
                }
            if (connection != null) 
                {
                    connection.close();
                }
        }
    }
    
    
}
