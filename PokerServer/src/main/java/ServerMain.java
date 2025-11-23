//import javax.swing.*;
//import java.util.function.Consumer;
//
//public class ServerMain {
//
//    static class ServerThread extends Thread {
//        private String host;
//        private int port;
//        private Consumer<String> logCallBack;
//        PokerServer myServer;
//
//        public ServerThread(String host, int port, Consumer<String> logCallBack){
//            this.host = host;
//            this.port = port;
//            this.logCallBack = logCallBack;
//            myServer = new PokerServer(host, port, logCallBack);
//        }
//
//        @Override
//        public void run() {
//            myServer.run();
//        }
//    }
//
//    // start server with IP and port
//    public void startServer(String host, int port, Consumer<String> consumer) {
//        ServerThread newThread = new ServerThread(host, port, consumer);
//        newThread.start();
//    }
//}
