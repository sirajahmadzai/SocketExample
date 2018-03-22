import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Trivial client for the date server.
 */
public class Client {
    private static final String SERVER_ADDRESS = "localhost"; //IP OF SERVER
    private static final int SERVER_PORT = 9090; //PORT OF SERVER

    private enum MenuOptions {
        HELLO_WORLD,
        COUNTER,
        EXIT;

        @Override
        public String toString() {
            return String.valueOf(this.ordinal());
        }
    }
//
//    enum CMD{
//        CARD
//    }
//
//    enum Location{
//        ASSET,
//        PLAY,
//        DECKS,
//        HAND
//    }
//    int playerId = 0;
//    String cmd = CMD.CARD + " " + playerId + " " + Location.ASSET + " MOVE " + Location.HAND;



    private static final String MENU = MenuOptions.HELLO_WORLD + ". Hello World\n" + MenuOptions.COUNTER + ". Counter\n" + MenuOptions.EXIT + ". exit";

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    public Client() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
    public void start() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        String userInput = null;

        do {
            System.out.println(MENU);
            userInput = keyboard.nextLine();
            if(!userInput.equals(MenuOptions.EXIT.toString())){
            System.out.println("Write to server");
            out.println(userInput);
            System.out.println("Done writing to server");


                System.out.println("Read from server");
                String response = in.readLine();
                System.out.println("Done reading from server");
                System.out.println(response);
            }
        } while (!userInput.equals(MenuOptions.EXIT.toString()));

        System.exit(0);
    }
}