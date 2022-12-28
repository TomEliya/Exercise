import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmailBuilder {
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    Scanner scanner;

    public EmailBuilder(Socket acceptedSocket, BufferedReader in, PrintWriter out) {
        socket = acceptedSocket;
        input = in;
        output = out;
        scanner = new Scanner(System.in);
    }

    private String userEmail() {
        String from;
        do {
            System.out.println("""
                    Enter your e-mail or enter q for exit:
                    The email must be one of the three vendors
                    @google.com, @walla.co.il, @yahoo.com""");
            from = scanner.nextLine();
            if (!validEmail(from) && !from.equals("q")) {
                System.out.println("Your email is not valid, please try again");
            } else if (!validPostfix(from) && !from.equals("q")) {
                System.out.println("Your email vendor is incorrect, please try again");
            }
        } while ((!validEmail(from) || !validPostfix(from)) && !from.equals("q"));
        return from;
    }

    private boolean validPostfix(String from) {
        int emailSymbolIndex = from.indexOf('@');
        if (emailSymbolIndex < 0) {
            return false;
        }

        String postfix = from.substring(emailSymbolIndex);
        postfix = postfix.trim();
        postfix = postfix.toLowerCase();
        return postfix.equals("@gmail.com") ||
                postfix.equals("@walla.co.il") ||
                postfix.equals("@yahoo.com");
    }

    private String destinationEmail() {
        String to;
        System.out.println("Please enter a destination email: ");
        to = scanner.nextLine();
        if (!validEmail(to)) {
            System.out.println("The e-mail is not valid.");
            return "again";
        }
        return to;
    }

    private String buildBody() {
        System.out.println("Please enter the email body\n" +
                "For stopping build your body press Enter.");
        StringBuilder body = new StringBuilder();
        String bodyLine;
        while (true) {
            bodyLine = scanner.nextLine();
            if (bodyLine.equals("")) {
                return body.toString();
            }
            body.append(bodyLine).append("\n");
        }
    }

    public void buildEmails() {
        String from;
        String option;
        String to;
        String body;
        String acceptSend;
        String response;
        String tryAgain = null;

        try {
            while (true) {
                System.out.println("[1] Send Email.\n[2] exit.");
                option = scanner.nextLine();
                if (option.equals("1")) {
                    to = destinationEmail();
                    if (to.equals("again")) {
                        continue;
                    }

                    from = userEmail();
                    if (from.equals("q")) {
                        scanner.close();
                        return;
                    }

                    body = buildBody();
                    System.out.println("Your Email:\nTo: " + to +
                            "\nFrom: " + from +
                            "\nBody: " + body +
                            "\nAre you sure you want to send the email? Y/N");
                    acceptSend = scanner.nextLine();
                    if (acceptSend.equals("y") || acceptSend.equals("Y")) {
                        // Sending the email to the server.
                        output.println(to + "\n" + from + "\n" + body);
                        // time out so the user doesn't wait forever
                        socket.setSoTimeout(5000);
                        // Waiting for acknowledgement from the server
                        response = input.readLine();
                        System.out.println("Response from Email Server: " + response);
                    }

                } else if (option.equals("2")) {
                    System.out.println("Bye Bye.");
                    break;
                } else {
                    System.out.println("Wrong choice, the options are 1 or 2");
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Socket timed out, Would you like to try again? Y/N");
            tryAgain = scanner.nextLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (tryAgain != null && (tryAgain.equals("y") || tryAgain.equals("Y"))) {
                buildEmails();
            } else {
                scanner.close();
            }
        }
    }

    private boolean validEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
}
