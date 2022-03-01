package iot.hub.exception;

import java.io.IOException;

public class ResourceAlreadyExistException extends IOException {
    public ResourceAlreadyExistException(String message) {
        super("Ресурс уже существует: " + message);
    }
}
