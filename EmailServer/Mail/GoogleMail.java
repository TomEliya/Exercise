package Mail;

public class GoogleMail extends Email {
    private final String serverAddress = "smtp.google.com";
    private final String username = "admin";
    private final String password = "admin";

    public GoogleMail() {
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
