package iot.hub.model.device.sensor;

public interface ISensor {

    // Вызывается со стороны REST API
    void changeUpdateFrequency(Integer milliSeconds);

}
