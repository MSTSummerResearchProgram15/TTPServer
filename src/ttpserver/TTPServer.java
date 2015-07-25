/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttpserver;

import org.apache.log4j.BasicConfigurator;


public class TTPServer{
   
public static void main(String[] args){
    System.out.println("the very top");
        //Fix logger warning
        BasicConfigurator.configure();
        
        //start the socket server and wait for incomming data
        SocketServer server = new SocketServer();
        System.out.println("Hello");
        //UserCreation newUser = new UserCreation();       
        //newUser.delete(1);
        //ParamsGen PG = new ParamsGen();
        //Params p = new Params();
        System.out.println("Get here");
        //p = PG.generate(160, 512);
        System.out.println("Params is ok");
        //newUser.Register(2, "abc", 0, 128, p );
        //newUser.RegisterUser(2, "def");
        System.out.println("tada");
        
        
        //FTP server socket
        //FTPSocketServer soc=new FTPSocketServer();
        //System.out.println("FTP Server Started on Port Number 5217");
        //while(true)
        //{
        //    System.out.println("Waiting for Connection ...");
            //transferfile t=new transferfile(soc.accept());
            
        //}
    }  
}
