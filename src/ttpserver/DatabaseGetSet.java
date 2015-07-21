package ttpserver;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jdom.JDOMException;

import databasemanager.DatabaseManager;

public class DatabaseGetSet {

    Connection connection = null;
    PreparedStatement pstmt = null;
    String sql;
    ResultSet result;

    public DatabaseGetSet() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, JDOMException {
        connection = DatabaseManager.getInstance().getConnection();
    }

    public void setUserID(int userID) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(userID) VALUE(?)");
        pstmt.setInt(1, userID);
    }

    public void setPassword(byte[] password) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(password) VALUE(?)");
        pstmt.setBytes(1, password);
    }

    public void setCurveParams(byte[] curveParameters) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(pairing) VALUE(?)");
        pstmt.setBytes(1, curveParameters);
    }

    public void publicKey(byte[] publicKey) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(publicKey) VALUE(?)");
        pstmt.setBytes(1, publicKey);
    }

    public void privateKey(byte[] privateKey) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(secretkey) VALUE(?)");
        pstmt.setBytes(1, privateKey);
    }

    public void setRole(int role) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(role) VALUE(?)");
        pstmt.setInt(1, role);
    }

	//Start get methods
    public byte[] getPassword(int usr) throws SQLException {
        sql = "SELECT password FROM user_table WHERE userID='" + usr + "'";
        pstmt = connection.prepareStatement(sql);
        result = pstmt.executeQuery();
        Blob myDbPw = null;
        while (result.next()) {
            myDbPw = result.getBlob("password");
        }
        int blobLength = (int) myDbPw.length();
        byte[] blobPw = myDbPw.getBytes(1, blobLength);

        return blobPw;
    }

    public byte[] getCurveParams(int usr) throws SQLException {
        sql = "SELECT pairing FROM user_table WHERE userID='" + usr + "'";
        pstmt = connection.prepareStatement(sql);
        result = pstmt.executeQuery();

        Blob myDbCurveParams = null;
        while (result.next()) {
            myDbCurveParams = result.getBlob("pairing");
        }
        int blobLength = (int) myDbCurveParams.length();
        byte[] blobCurveParams = myDbCurveParams.getBytes(1, blobLength);

        return blobCurveParams;
    }

    public byte[] getPublicKey(int usr) throws SQLException {
        sql = "SELECT publicKey FROM user_table WHERE userID='" + usr + "'";
        pstmt = connection.prepareStatement(sql);
        result = pstmt.executeQuery();

        Blob myDbPublicKey = null;

        while (result.next()) {
            myDbPublicKey = result.getBlob("publicKey");
        }
        int blobLength = (int) myDbPublicKey.length();
        byte[] blobPublicKey = myDbPublicKey.getBytes(1, blobLength);

        return blobPublicKey;
    }

    public byte[] getPrivateKey(int usr) throws SQLException {
        sql = "SELECT secretKey FROM user_table WHERE userID='" + usr + "'";
        pstmt = connection.prepareStatement(sql);
        result = pstmt.executeQuery();

        Blob myDbPrivateKey = null;
        while (result.next()) {
            myDbPrivateKey = result.getBlob("secretKey");
        }
        int blobLength = (int) myDbPrivateKey.length();
        byte[] blobPrivateKey = myDbPrivateKey.getBytes(1, blobLength);

        return blobPrivateKey;
    }

    public int getRole(int usr) throws SQLException {
        sql = "SELECT role FROM user_table WHERE userID=" + usr + "'";
        pstmt = connection.prepareStatement(sql);
        result = pstmt.executeQuery();
        int role = 0;

        while (result.next()) {
            role = result.getInt("role");
        }

        return role;
    }

}
