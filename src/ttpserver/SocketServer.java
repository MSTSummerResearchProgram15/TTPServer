package ttpserver;

import databasemanager.DatabaseManager;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bouncycastle.jce.provider.JDKMessageDigest;
import org.jdom.JDOMException;

import java.util.Arrays;

public class SocketServer extends Thread {

    ServerSocket server;
    int keySize;
    Params params;

    SocketServer() {
        this.start();
    }

    public void run() {

        try {
            server = new ServerSocket(ServerPort.port);
            Socket clientSoc = null;
            BufferedReader input;
            PrintStream output;
            String data, usr = null, pw = null;
            boolean a = false;

            clientSoc = server.accept();

            input = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
            output = new PrintStream(clientSoc.getOutputStream());

            while (true) {

                while ((data = input.readLine()) != null) {
                    
                    if (data.startsWith("username:")) {
                        usr = data.substring(9);
                    } else if (data.startsWith("password:")) {
                        pw = data.substring(9);
                    }

                    System.out.println(usr);
                    System.out.println(pw);

                    if (usr != null && !usr.isEmpty() && pw != null && !pw.isEmpty()) {

                        a = verifyUser(usr, pw);
                    }

                    if (a == true) {
                        output.write(0);
                    } else {
                        output.write(1);
                    }
                }
                System.out.println(usr);
                System.out.println(pw);

            }

        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public byte[] hash(byte[] value) {
        byte[] hash;
        MessageDigest md = new JDKMessageDigest.SHA256();
        hash = md.digest(value);
        return hash;
    }

    public boolean verifyUser(String usr, String pw) {
        //hash the password    
        byte[] mypassword = pw.getBytes();
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
            String sql = "SELECT userID, password FROM user_table WHERE userID LIKE % " + usr + " %";
            connection = DatabaseManager.getInstance().getConnection();
            pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                myDbPw = result.getBlob("password");
            }
            
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

    public Params createParams(int keySize) {
        ParamsGen gen = new ParamsGen();

        switch (keySize) {
            case 128:
                params = gen.generate(160, 512);
                break;
            case 256:
                params = gen.generate(360, 1024);
                break;
            case 512:
                params = gen.generate(620, 2048);
                break;
            case 1024:
                params = gen.generate(1040, 4096);
                break;
            case 2048:
                params = gen.generate(2080, 8192);
                break;
            case 4096:
                params = gen.generate(4160, 16384);
                break;

            default:
                params = gen.generate(160, 512);
                break;
            //If you choose anything greater than 512 it probably will never finish...
            }

        byte[] g = params.getgBytes();
        byte[] k = params.getkBytes();
        byte[] gk = params.getg_kBytes();
        byte[] zk = params.getz_kBytes();
        Connection connection = null;
        java.sql.Statement stmt = null;

        try {
            connection = DatabaseManager.getInstance().getConnection();
            stmt = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USER_TABLE(g, k, gk, zk) VALUE(?,?,?,?)");

            pstmt.setBytes(1, g);
            pstmt.setBytes(2, k);
            pstmt.setBytes(3, gk);
            pstmt.setBytes(4, zk);

            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return params;
    }

    /*
     public Params restoreParams(int baseUser){
     byte[] g, k, g_k, z_k;
     PairingParameters curveParams;
        
     Pairing pairing;
     Params p;
        
     // retrieve values from database
        
     // reconstruct values from byte arrays
        
     // set values in Params p
        
     // return p
     return p;
     }
     */
}
