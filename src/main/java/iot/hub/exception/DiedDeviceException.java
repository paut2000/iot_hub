package iot.hub.exception;

import java.io.IOException;

public class DiedDeviceException extends IOException {

    public DiedDeviceException(String message) {
        super("Девайсс был поделючен, но уже не подключен: " + message);
    }
}
