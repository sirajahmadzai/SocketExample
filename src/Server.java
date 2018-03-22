import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class Server {
    private static ArrayList<Handler> sockets = new ArrayList<>();
    private static int counter = 0;

    private ServerSocket listener;

    public Server() throws IOException {
        this(9090);
    }

    public Server(int port) throws IOException {
        System.out.println("Creating server");
        listener = new ServerSocket(port);
    }

    /**
     * Runs the server.
     */
    public void start() throws IOException {
        try {
            while (true) {
                System.out.println("Wait for socket...");
//                Socket socket = listener.accept();
                sockets.add(new Handler(listener.accept()));
                sockets.get(sockets.size() - 1).start();

//                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                System.out.println("Waiting to read from Client");
//                String command = in.readLine();
//                System.out.println("Done reading from client");

//                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);




//                System.out.println("Write to client");
//                out.println("Hello World #" + ++counter);
//                System.out.println("Done writing to client");
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */
    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructs a handler thread, squirreling away the socket.
         * All the interesting work is done in the run method.
         */
        public Handler(Socket socket) throws IOException {
            this.socket = socket;

            // Create character streams for the socket.
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        /**
         * Services this thread's client by repeatedly requesting a
         * screen name until a unique one has been submitted, then
         * acknowledges the name and registers the output stream for
         * the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        public void run() {
            try {
                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.
                while (true) {
                    String command = in.readLine();
                    if (command == null) {
                        return;
                    }

//                    for (Handler handler : sockets) {
//                        handler.out.println("MESSAGE " + name + ": " + input);
//                    }

                    switch(command){
                        case "0":
                            //DO HELLO WORLD
                            out.println("Hello World");
                            break;
                        case "1":
                            //DO COUNTER
                            out.println(++counter);
                            break;
                        default:
                            //NOTHING? ERROR?
                            out.println("Error!");
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (out != null) {
                    sockets.remove(this);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

}