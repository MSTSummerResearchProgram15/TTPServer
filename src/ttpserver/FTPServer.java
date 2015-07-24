package ttpserver;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
	Socket sock;
    public FTPServer() throws IOException{
    	ServerSocket servsock = new ServerSocket(13262);
        while (true) {
            System.out.println("Waiting...");

            sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);
        }
    }

    public void send(String fileName) throws Exception {
        // sendfile
        OutputStream os = sock.getOutputStream();
        File myFile = new File(fileName);
        byte[] mybytearray = new byte[(int) myFile.length() + 1];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        System.out.println("Sending...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        sock.close();

    }

} 
