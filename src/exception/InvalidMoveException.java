package exception;

// Custom exception class to handle the invalid move scenario
public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}