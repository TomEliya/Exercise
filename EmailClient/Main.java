import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        try {
            // Create a socket with the server in order to communicate
            socket = new Socket("localhost", 5000);
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to the server.");

            (new EmailBuilder(socket, input, output)).buildEmails();

        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
