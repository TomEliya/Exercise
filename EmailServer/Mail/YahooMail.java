package Mail;

public class YahooMail extends Email {
    private final String serverAddress = "smtp.yahoo.com";
    private final String username = "admin";
    private final String password = "admin";

    public YahooMail() {
    }

    @Override
    protected String getServerAddress() {
        return serverAddress;
    }

    @Override
    protected String getUsername() {
        return username;
    }

    @Override
    protected String getPassword() {
        return password;
    }
}
