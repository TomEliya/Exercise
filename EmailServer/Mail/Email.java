package Mail;

import java.util.regex.Pattern;

abstract public class Email {
    /*
        Using abstract class Email, sendEmail method relevant
        for all subclasses, This class is abstract for not repeating
        the sandEmail method.
     */
    public Email() {
    }

    protected abstract String getServerAddress();

    protected abstract String getUsername();

    protected abstract String getPassword();

    public boolean sendEmail(String to, String from, String body) {
        // validate that the destination mail address is valid.
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!Pattern.compile(regexPattern).matcher(to).matches()) {
            return false;
        }
        // Simulate connect and send email.
        System.out.println("Connect to the server: " + getServerAddress() + "...\n" +
                "Username: " + getUsername() + "\nPassword: " + getPassword() +
                "\nEmail send to: " + to + "\nFrom: " + from + "\nBody:" + body);
        return true;
    }
}
