//package PokerServer.src.main.java;

import game.Round;
import game.Hand;
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
            this.clientNumber = number+1;
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
//                            if (currRound == null) {
//                                response.setMessage("Error: No active round to fold.");
//                                log("Client #" + clientNumber + " tried to fold without active round");
//                                return;
//                            }

                            currRound = new Round(request.getAnteBet(), request.getPairPlusBet());
                            response.setPlayerHand(convertHand(currRound.getClientHand_arrList()));
                            response.setDealerHand(convertHand(currRound.getServerHand_arrList()));
                            response.setRoundNum(response.getRoundNum()+1);
                            response.setMessage("Cards dealt! Your hand has been created");

                            log("Client #" + clientNumber + " | Round- " + response.getRoundNum() + " | " +
                                    "Dealer: " + currRound.getServerHand().toString() +
                                    "Client: " + currRound.getClientHand().toString());


                            System.out.println("SERVER: About to send player hand with " +
                                    response.getPlayerHand().size() + " cards");
                            for (shared.game.Card c : response.getPlayerHand()) {
                                System.out.println("  SERVER: Card file = " + c.getCardFile());
                            }

                            System.out.println(request.getAction() +  request.getMessage() + request.getPlayerHand() + request.getDealerHand() + request.getPairPlusBet() + request.getAnteBet());
                            break;

                        case PLAY:
                            if (currRound == null) {
                                response.setMessage("Error: No active round to fold.");
                                log("Client #" + clientNumber + " tried to play without active round");
                                break;
                            }

                            int playerScore = ThreeCardLogic.rankHand(currRound.getClientHand().getCards());
                            int serverScore = ThreeCardLogic.rankHand(currRound.getServerHand().getCards());
                            String outcome;
                            int payout;

                            // ADD DIFFERENT PAYOUT LOGIC AS IN INSTRUCTIONS
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
                            response.setMessage("You " + outcome + "! Payout: " + payout + ". Winnings: $" + response.getTotBalance() + ".");
                            response.setTotBalance(totalBalance);

                            log("Client #" + clientNumber + " " + outcome + "\'s.");

                            System.out.println(request.getAction() +  request.getMessage() + request.getPlayerHand() + request.getDealerHand() + request.getPairPlusBet() + request.getAnteBet());
                            break;
                        case FOLD:
                            handleFold(request, response);
                            break;
                        case QUIT:
                            log("Client #" + clientNumber + " has quit.");
                            clients.remove(this);
                            updateClientCount();
                            connection.close();
                            System.out.println(request.getAction() +  request.getMessage() + request.getPlayerHand() + request.getDealerHand() + request.getPairPlusBet() + request.getAnteBet());
                            return;
                    }
                    log("Client #" + clientNumber + " sending reponse: " + response.getMessage());
                    out.writeObject(response);
                    out.flush();
                    log("Client #" + clientNumber + " reponse send successfully.");
                }
            } catch (Exception e) {
                System.out.println("Streams not open for client #" + clientNumber);
                e.printStackTrace();
            }
        }

        private void handleFold(PokerInfo request, PokerInfo response) {
            try {
                if (currRound == null) {
                    response.setMessage("Error: No active round to fold.");
                    log("Client #" + clientNumber + " tried to fold without active round");
                    return;
                }

                int loss = -request.getAnteBet();
                totalBalance += loss;

                response.setMessage("You folded! Lost $" + request.getAnteBet() + ". Total: $" + totalBalance);
                response.setTotBalance(totalBalance);

                log("Client #" + clientNumber + " folded. Lost $" + request.getAnteBet());

                // Round is over
                currRound = null;

            } catch (Exception e) {
                response.setMessage("Error processing fold: " + e.getMessage());
                log("Client #" + clientNumber + " error in FOLD: " + e.getMessage());
                e.printStackTrace();
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