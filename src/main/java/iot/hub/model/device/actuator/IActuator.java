package iot.hub.model.device.actuator;

import java.util.LinkedHashMap;

public interface IActuator {

    LinkedHashMap<String, Object> getStatus();
    void switchStatus();
    void enable();
    void disable();

}
