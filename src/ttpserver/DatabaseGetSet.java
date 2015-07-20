package ttpserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jdom.JDOMException;

import databasemanager.DatabaseManager;

public class DatabaseGetSet {
	Connection connection = null;
    PreparedStatement pstmt = null;
    
	public DatabaseGetSet() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, JDOMException{
        connection = DatabaseManager.getInstance().getConnection();           
	}
	
	public void setUserID(int userID) throws SQLException{
		pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(userID) VALUE(?)");
		pstmt.setInt(1, userID);
	}
	
	public void setPassword(byte[] password) throws SQLException{
		pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(password) VALUE(?)");
		pstmt.setBytes(1, password);
	}
	
	public void setCurveParams(byte[] curveParameters) throws SQLException{
		pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(pairing) VALUE(?)");
		pstmt.setBytes(1, curveParameters);
	}
	
	public void publicKey(byte[] publicKey) throws SQLException{
		pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(publicKey) VALUE(?)");
		pstmt.setBytes(1, publicKey);
	}
	
	public void privateKey(byte[] privateKey) throws SQLException{
		pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(secretkey) VALUE(?)");
		pstmt.setBytes(1, privateKey);
	}
	
	public void setRole(int role) throws SQLException{
		pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(role) VALUE(?)");
		pstmt.setInt(1, role);
	}
	
	
	
	
}
