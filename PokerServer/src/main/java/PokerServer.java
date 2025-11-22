//package PokerServer.src.main.java;

import game.Hand;
import game.Round;
import game.ThreeCardLogic;
import shared.PokerInfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PokerServer {
    private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    int numClients = 0;
    private String host;
    private int port;

    public PokerServer(String host, int port){
        this.host = host;
        this.port = port;
    }


    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server on port: " + port + " is waiting for client(s)!");

            while (true) {
                ClientThread c = new ClientThread(serverSocket.accept(), numClients);
                clients.add(c);
                System.out.println("client has connected to server: " + "client #" + numClients++);
                c.start();

                // need to replace this code with the round logic.
                // not sure how to send the GUI element values from client to server
                // pseudo sudo code
//                anteAmount = anteBox.value
//                 bet = betBox.value

                // Round currRound = new Round(...)
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // create our own Thread class that has built in methods we care about
    // I THINK this will be where we implement Dashboard and LOGIC of each clients game
    class ClientThread extends Thread{
        Socket connection;
        ObjectInputStream in;
        ObjectOutputStream out;
        int clientNumber;
        Round currRound;
        int totalBalance;

        // constructor for a ClientThread
        ClientThread(Socket s, int number){
            this.connection = s;
            this.clientNumber = number;
        }

        private ArrayList<shared.game.Card> convertHand(ArrayList<game.Card> serverHand){
            ArrayList<shared.game.Card> convertedHand = new ArrayList<>();
            for (game.Card card : serverHand){
                convertedHand.add(new shared.game.Card(card.getSuit(), card.getRank()));
            }
            return convertedHand;
        }

        public void run(){
            System.out.println("New client #: " + clientNumber);

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);

                // putting it all together. might need to think about how to implement this
                while (true){
                    Object obj = in.readObject();
                    if (!(obj instanceof PokerInfo)) continue;

                    PokerInfo request = (PokerInfo) obj;
                    PokerInfo response = new PokerInfo();

                    switch(request.getAction()){
                        case PLACE_BET:
                            currRound = new Round(request.getAnteBet(), request.getPairPlusBet());
                            response.setMessage("Cards dealt! Make your move!");
                            break;

                        case PLAY:
                            int playerScore = ThreeCardLogic.rankHand(currRound.getClientHand().getCards());
                            int serverScore = ThreeCardLogic.rankHand(currRound.getServerHand().getCards());
                            String outcome;
                            int payout;

//                            NOTE
                            // add different payout logic maybe
                            if (playerScore > 0 && playerScore > serverScore){
                                outcome = "WIN";
                                payout = currRound.getAnteWager() * 2;
                            }
                            else if (playerScore == serverScore){
                                outcome = "DRAW";
                                payout = 0;
                            }
                            else {
                                outcome = "LOSE";
                                payout = -currRound.getAnteWager();
                            }
                            totalBalance += payout;
                            //currRound = new Round(request.getAnteBet(), request.getPairPlusBet());
                            response.setPlayerHand(convertHand(currRound.getClientHand_arrList()));
                            response.setDealerHand(convertHand(currRound.getServerHand_arrList()));
                            response.setMessage("You " + outcome + "! Payout: " + payout);
                            response.setTotBalance(totalBalance);
                            break;
                        case FOLD:
                            response.setMessage("You folded!");
                            break;
                        case QUIT:
                            this.connection.close();
                            break;
                    }
                    out.writeObject(response);
                    out.flush();
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



