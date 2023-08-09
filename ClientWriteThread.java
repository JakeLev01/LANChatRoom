package assignment5;

import java.io.*;
import java.net.*;


public class ClientWriteThread extends Thread {
    public Socket s;
    ClientMain client;
    //Thread out;

    ClientWriteThread(Socket s, ClientMain client) {
        this.s = s;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (client.isConnected) {
                String message = client.scnr.nextLine();
                client.dout.writeUTF(message);
                client.dout.flush();
                sleep(100);
            }
            client.dout.close();
            currentThread().interrupt();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
