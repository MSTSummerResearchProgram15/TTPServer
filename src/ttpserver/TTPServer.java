/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttpserver;

import org.apache.log4j.BasicConfigurator;


public class TTPServer{
   
public static void main(String[] args){
        //Fix logger warning
        BasicConfigurator.configure();
        
        //start the socket server and wait for incomming data
        SocketServer server = new SocketServer();
        
        UserCreation newUser = new UserCreation();       
        //newUser.delete(1);
        //ParamsGen PG = new ParamsGen();
        //Params p = new Params();
        //p = PG.generate(160, 512);
        //newUser.Register(4, "abc", 1, 128, p );
        
    
    }  
}
