import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // Open a server socket
            server = new ServerSocket(5000);
            // Listening for clients, and start a new thread
            while (true) {
                Socket client = server.accept();

                new ClientHandler(client).start();

                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}