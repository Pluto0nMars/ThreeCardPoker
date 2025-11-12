import java.io.*;
import java.net.Socket;

public class PokerClient {
    //connect to server with client request
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connectToServer(String ipAddress, int portNum){
        try{
            socket = new Socket(ipAddress, portNum);
            System.out.println("Connected to server at:" + ipAddress + ":" + portNum);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            System.err.println("Oops, Error connection to server :" + e.getMessage());
        }


    }
    public void closeConnection(){
        try{
            if(socket != null){
                socket.close();
            }
        }catch(IOException e){
            System.err.println("Error closing connection: "+e.getMessage());
        }
    }

    public  ObjectInputStream getInputStream(){
        return in;
    }

    public ObjectOutputStream getOutputStream(){
        return out;
    }
}
