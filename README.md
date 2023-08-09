# LANChatRoom
LAN Chat Room that allows multiple users to enter the room and send message to other active users, programmed in Java using Networking and Threading protocols.

Created .java files for Client and Server that utilze threads to establish connections.

### Server Side
Establishes Server Socket and continuously accepts new clients to which a new socket is created for each client connection.
These sockets then read all incoming messages from the client and identify who the recipient of each message is and if their are an active user of the chat room. The message is then passed to the correct user with information of who sent the message and when. All code is in ServerMain.java and ServerClient.java.

### Client Side
ClientMain.java establishes a connection to the Server using DNS protocols and creates 2 threads, one for writing to the server and one for reading incoming messages from the server. This code is in ClientWriteThread.java and ClientReadThread.java respectfully. The read and write threads use BufferedReader and BufferedWriter to maintain the inputs of each message. The connection to the server is severed when the user inputs an exit message.

**Skills learned/used: Java, Networking, Threading, Java TextInput, DNS protocols**
