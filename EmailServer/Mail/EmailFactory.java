package Mail;

public class EmailFactory {
    // Factory Method DP, Using static method In order not to create an instance of the class.
    public static Email vendorByPostfix(String from) {
        // Clean the string.
        if (from == null || from.isEmpty()) {
            return null;
        }

        int emailSymbolIndex = from.indexOf('@');
        if (emailSymbolIndex < 0) {
            return null;
        }

        String postfix = from.substring(emailSymbolIndex);
        postfix = postfix.trim();
        postfix = postfix.toLowerCase();
        return switch (postfix) {
            case "@gmail.com" -> new GoogleMail();
            case "@walla.co.il" -> new WallaMail();
            case "@yahoo.com" -> new YahooMail();
            default -> null;
        };
    }
}
