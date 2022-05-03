package iot.hub.exception;

import java.io.IOException;

public class DataAlreadyLikeThatException  extends IOException {

    public DataAlreadyLikeThatException(String message) {
        super("У устройства уже задано задаваемое состояние: " + message);
    }
}
