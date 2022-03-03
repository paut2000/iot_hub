package iot.hub.model.device.actuator;

import iot.hub.exception.DiedDeviceException;

public interface IActuator {

    // Вызываются со стороны REST API
    void enable() throws DiedDeviceException;
    void disable() throws DiedDeviceException;

}
