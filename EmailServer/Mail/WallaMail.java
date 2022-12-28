package Mail;

public class WallaMail extends Email {
    private final String serverAddress = "smtp.walla.co.il";
    private final String username = "admin";
    private final String password = "admin";

    public WallaMail() {
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
