package assignment5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMain{
    Socket s;
    public Scanner scnr;
    boolean isConnected;
    DataInputStream din;
    DataOutputStream dout;
    //ClientReadThread in;
    //ClientWriteThread out;
    public void closeSocket() throws Exception{this.s.close();}

    ClientMain() throws IOException {
        try{
            this.s =  new Socket("localhost",6666);
            isConnected = true;
            System.out.println("Trying to connect to " + s.getRemoteSocketAddress() + " with port number " + s.getPort() + "....." );
            System.out.print("Connected!\n");
            this.scnr = new Scanner(System.in);
            //this.name = scnr.next();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
        }
        catch(SocketException e){
            System.out.println("The connection was refused!");
            System.exit(0);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        ClientMain client = new ClientMain();
        try {
            Thread rt = new Thread(new ClientReadThread(client.s, client));
            Thread wt = new Thread(new ClientWriteThread(client.s, client));
            rt.start();
            wt.start();
            while (!rt.isInterrupted() && !wt.isInterrupted());

            System.out.println("Goodbye!!\nWe wish to see you again soon!");
            client.closeSocket();

        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } finally {
            client.closeSocket();
        }
    }

}
