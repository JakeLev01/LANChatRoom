package assignment5;

import java.io.*;
import java.net.*;

public class ClientReadThread extends Thread{
    Socket s;
    ClientMain client;

    ClientReadThread(Socket s, ClientMain client){
        this.s = s;
        this.client = client;
    }
    @Override
    public void run() {
        try{
            while(!currentThread().isInterrupted()){
                String message = client.din.readUTF();
                if(message.equals("Goodbye!")){
                    currentThread().interrupt();
                }else{
                    System.out.println(message);
                }
            }
            client.isConnected = false;
            client.din.close();
        } catch (IOException e) {
            System.out.println("Input exception: " + e);
        } catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
}
