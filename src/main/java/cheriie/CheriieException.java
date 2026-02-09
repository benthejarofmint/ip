package cheriie;

public class CheriieException extends Exception {
    public CheriieException(String message) {
        super("oh no!!! " + message);
    }
}
