package iot.hub.model.device.sensor;

import java.util.LinkedHashMap;

public interface ISensor {

    String getInfo();

    // Вызывается со стороны МК
    void changeInfo(LinkedHashMap<String, Object> info);

    // Вызывается со стороны REST API
    void changeUpdateFrequency(Integer milliSeconds);

}
