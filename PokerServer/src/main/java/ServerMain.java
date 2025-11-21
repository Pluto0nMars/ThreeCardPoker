import javax.swing.*;

public class ServerMain {

    static class ServerThread extends Thread {
        private String host;
        private int port;
        PokerServer myServer;

        public ServerThread(String host, int port){
            this.host = host;
            this.port = port;
            myServer = new PokerServer(host, port);
        }

        @Override
        public void run() {
            myServer.run();
        }
    }

    // start server with IP and port
    public void startServer(String host, int port) {
        ServerThread newThread = new ServerThread(host, port);
        newThread.start();
    }
}
