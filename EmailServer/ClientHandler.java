import Mail.Email;
import Mail.EmailFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Email email = null;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        PrintWriter output = null;
        BufferedReader input = null;

        try {
            // Create the streaming with the client using the opened socket
            input = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));

            output = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            String to;
            String from;
            String incorrectStructure = "The structure of the mail is incorrect, Please try again";
            // Listening to input of from client, Parse the input and
            // check is the input is valid
            while ((to = input.readLine()) != null) {
                if (!input.ready()) {
                    output.println(incorrectStructure);
                    continue;
                }
                from = input.readLine();

                if (!input.ready()) {
                    output.println(incorrectStructure);
                    continue;
                }

                StringBuilder body = new StringBuilder();
                while (input.ready()) {
                    body.append('\n').append(input.readLine());
                }
//                Thread.sleep(3000);
                System.out.println("Income e-mail from client: " + from);
                // Using factory method DP to create the correct class by postfix of the client
                email = EmailFactory.vendorByPostfix(from);
                if (email != null && email.sendEmail(to, from, body.toString())) {
                    output.println("Successfully sending the e-mail.");
                } else {
                    if (email == null) {
                        output.println("Invalid email vendor");
                    } else {
                        output.println("Something went wrong while sending the email");
                    }
                }
                email = null;
            }
        }
//        Exception for the thread sleep
//        catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
                clientSocket.close();
                System.out.println("Socket closed with client: " +
                        clientSocket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
