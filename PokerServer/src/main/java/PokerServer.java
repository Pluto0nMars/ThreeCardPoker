//package PokerServer.src.main.java;

import game.Round;
import game.ThreeCardLogic;
import shared.PokerInfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PokerServer {
    private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    int numClients = 0;
    private String host;
    private int port;
    private Consumer<String> logCallback;
    private Consumer<Integer> clientCountCallback;


    public PokerServer(String host, int port, Consumer<String> logCallback, Consumer<Integer> clientCountCallback) {
        this.host = host;
        this.port = port;
        this.logCallback = logCallback;
        this.clientCountCallback = clientCountCallback;
    }


    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[LOG] Server is waiting for client(s)...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread c = new ClientThread(socket, numClients);
                clients.add(c);

                numClients++;
                log("Client " + (numClients) + " connected to server: ");
                updateClientCount();

                c.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // create our own Thread class that has built in methods we care about
    // I THINK this will be where we implement Dashboard and LOGIC of each clients game
    class ClientThread extends Thread {
        Socket connection;
        ObjectInputStream in;
        ObjectOutputStream out;
        int clientNumber;
        Round currRound;
        int totalBalance;

        // constructor for a ClientThread
        ClientThread(Socket s, int number) {
            this.connection = s;
            this.clientNumber = number;
        }

        private ArrayList<shared.game.Card> convertHand(ArrayList<game.Card> serverHand) {
            ArrayList<shared.game.Card> convertedHand = new ArrayList<>();
            for (game.Card card : serverHand) {
                convertedHand.add(new shared.game.Card(card.getSuit(), card.getRank()));
            }
            return convertedHand;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                connection.setTcpNoDelay(true);

                // putting it all together. might need to think about how to implement this
                while (true) {
                    Object obj = in.readObject();
                    if (!(obj instanceof PokerInfo)) continue;

                    PokerInfo request = (PokerInfo) obj;
                    PokerInfo response = new PokerInfo();

                    switch (request.getAction()) {
                        case PLACE_BET:
                            currRound = new Round(request.getAnteBet(), request.getPairPlusBet());
                            response.setMessage("Cards dealt! Make your move!");
                            log("Cards dealt for client #" + clientNumber + "! Make your move!\"");
                            break;

                        case PLAY:
                            int playerScore = ThreeCardLogic.rankHand(currRound.getClientHand().getCards());
                            int serverScore = ThreeCardLogic.rankHand(currRound.getServerHand().getCards());
                            String outcome;
                            int payout;

//                            NOTE
                            // add different payout logic maybe
                            if (playerScore > 0 && playerScore > serverScore) {
                                outcome = "WIN";
                                payout = currRound.getAnteWager() * 2;
                            } else if (playerScore == serverScore) {
                                outcome = "DRAW";
                                payout = 0;
                            } else {
                                outcome = "LOSE";
                                payout = -currRound.getAnteWager();
                            }
                            totalBalance += payout;
                            //currRound = new Round(request.getAnteBet(), request.getPairPlusBet());
                            response.setPlayerHand(convertHand(currRound.getClientHand_arrList()));
                            response.setDealerHand(convertHand(currRound.getServerHand_arrList()));
                            response.setMessage("You " + outcome + "! Payout: " + payout);
                            log("Client #" + clientNumber + out + "\'s.");
                            response.setTotBalance(totalBalance);
                            break;
                        case FOLD:
                            response.setMessage("You folded!");
                            break;
                        case QUIT:
                            log("Client #" + clientNumber + " has quit.");
                            clients.remove(this);
                            updateClientCount();
                            connection.close();
                            return;
                    }
                    out.writeObject(response);
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("Streams not open for client #" + clientNumber);
            }
        }
    }

    private void log(String message) {
//        System.out.println(message); // always print for sanity
        if (logCallback != null) {
            logCallback.accept(message); // send to GUI
        }
    }

    private void updateClientCount() {
        if (clientCountCallback != null) {
            clientCountCallback.accept(numClients); // send to GUI
        }
    }

    private void setLogCallback(Consumer<String> callback){
        this.logCallback = callback;
    }

    private void setClientCountCallback(Consumer<Integer> callback){
        this.clientCountCallback = callback;
    }

    private void clientConnected(){
        numClients++;
        log("Client " + (numClients) + " connected to server: ");
        updateClientCount();
    }

    private void clientDisconneced(){
        numClients--;
        log("Client has quit.");
        updateClientCount();
    }
}