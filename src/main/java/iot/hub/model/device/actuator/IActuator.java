package iot.hub.model.device.actuator;

import java.util.LinkedHashMap;

public interface IActuator {

    // Вызывается со стороны МК
    void changeStatus(LinkedHashMap<String, Object> payload);

    // Вызываются со стороны REST API
    void enable();
    void disable();

}
