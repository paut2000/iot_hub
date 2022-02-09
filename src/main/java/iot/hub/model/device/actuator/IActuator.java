package iot.hub.model.device.actuator;

import java.util.LinkedHashMap;

public interface IActuator {

    String getStatus();
    void setStatus(LinkedHashMap<String, Object> payload);
    void enable();
    void disable();

}
