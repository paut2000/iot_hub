package iot.hub.model.device.actuator;

public interface IActuator {

    // Вызываются со стороны REST API
    void enable();
    void disable();

}
