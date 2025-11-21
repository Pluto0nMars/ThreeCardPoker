package PokerServer.src.main.java;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PokerServer {
    private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    int count = 1;
    private static final int PORT = 5555;

    //    private Consumer<Serializable> callback;
//    PokerServer(Consumer<Serializable> call){
//        callback = call;
//        server = new Server.TheServer();
//        server.start();
//    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server on port: " + PORT + " is waiting for client(s)!");

            while (true) {
                ClientThread c = new ClientThread(serverSocket.accept(), count);
                clients.add(c);
                System.out.println("client has connected to server: " + "client #" + count++);
                c.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // create our own Thread class that has built in methods we care about
    // I THINK this will be where we implement Dashboard and LOGIC of each clients game
    class ClientThread extends Thread{
        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        // constructor for a ClientThread
        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        public void run(){
            // try to build IO stream to client
            System.out.println("New client #: " + count);
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);

                while (true){
                    Object message = in.readObject(); // read object from client
                    System.out.println("Client " + count + " sent: " + message);

                    // Echo it back or respond with something
                    out.writeObject("Server received: " + message);
                }
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }
        }


    }

//    public static void main(String[] args){
//        new PokerServer().run();
//    }
}



