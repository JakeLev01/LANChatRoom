package assignment5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerMain {
    ServerSocket ss;
    public static Map<String,Socket> clients = new HashMap<>();
    int portNum;

    ServerMain(int portNum) throws IOException {
        this.portNum = 6666;
        ss = new ServerSocket(portNum);
    }

    public static void main(String[] args) throws IOException {

        ServerMain server = new ServerMain(6666);
        try{
            while(true){
                Socket s = server.ss.accept();

                DataInputStream din = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                Thread serverThread = new Thread(new ServerThread(s,server,dout,din));
                serverThread.start();

            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
