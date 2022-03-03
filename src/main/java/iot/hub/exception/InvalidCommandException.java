package iot.hub.exception;

public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super("Неизвестная команда: " + message);
    }
}
