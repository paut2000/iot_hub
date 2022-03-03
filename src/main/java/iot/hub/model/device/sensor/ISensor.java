package iot.hub.model.device.sensor;

import iot.hub.exception.DiedDeviceException;

public interface ISensor {

    // Вызывается со стороны REST API
    void changeUpdateFrequency(Integer milliSeconds) throws DiedDeviceException;

}
