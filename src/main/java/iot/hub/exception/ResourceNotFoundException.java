package iot.hub.exception;

import java.io.IOException;

public class ResourceNotFoundException extends IOException {
    public ResourceNotFoundException(String message) {
        super("Нет такого ресурса: " + message);
    }
}
