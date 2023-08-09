package assignment5;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread{
    private Socket s;
    ServerMain server;
    public String name;
    DataInputStream din;
    DataOutputStream dout;

    ServerThread(Socket s,ServerMain server,DataOutputStream dout, DataInputStream din){
        this.s = s;
        this.server = server;
        this.dout = dout;
        this.din = din;
    }

    public void setUserName(String name){
        this.name = name;
    }

    @Override
    public void run(){
        try{
            dout.writeUTF("Hello!");
            TimeUnit.MILLISECONDS.sleep(500);
            dout.writeUTF("To get started please enter a name: ");
            String name = din.readUTF();
            this.setUserName(name);
            server.clients.put(name,s);

            TimeUnit.MILLISECONDS.sleep(500);
            dout.writeUTF("Hello, " + name + "!\nTo message other members of the server please identify the recipient name followed by a comma then your message to them");
            TimeUnit.MILLISECONDS.sleep(500);
            dout.writeUTF("Example: {name}, Hello!");
            TimeUnit.MILLISECONDS.sleep(500);
            dout.writeUTF("If the recipient is not online or does not exist we will let you know!");
            TimeUnit.MILLISECONDS.sleep(500);
            dout.writeUTF("To exit the chat simply type EXIT");
            TimeUnit.MILLISECONDS.sleep(500);
            dout.writeUTF("Happy chatting!");

            while(!s.isClosed()){
                String str = (String)din.readUTF();
                if(str.equals("EXIT")){
                    dout.writeUTF("Goodbye!");
                    s.close();
                    ServerMain.clients.remove(this.name);
                    continue;
                }else{
                    int nameIndex = str.indexOf(",");
                    if(nameIndex == -1){
                        dout.writeUTF("Your input is invalid.\nPlease try addressing a recipient");
                        continue;
                    }
                    String recipient = str.substring(0,nameIndex);
                    if(recipient.equals(this.name)){
                        dout.writeUTF("You can not message yourself.\nPlease message another user.");
                        continue;
                    }
                    String message = str.substring(nameIndex+1);
                    if(ServerMain.clients.containsKey(recipient)){
                        Socket tempSocket = ServerMain.clients.get(recipient);
                        DataOutputStream tempDout = new DataOutputStream(tempSocket.getOutputStream());
                        String finalMessage = "[" + this.name + "]:" +  message;
                        tempDout.writeUTF(finalMessage);
                    }else{
                        dout.writeUTF("This recipient does not exist.\nPlease try again.");
                    }
                }
                sleep(100);
            }
            din.close();
            dout.close();
            currentThread().interrupt();

        }catch(SocketTimeoutException e){
            System.out.println("Socket Error: " + e);
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("IO Exception: " + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
