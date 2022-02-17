package iot.hub.model.device.actuator.dataPojo;

import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.LinkedHashMap;

@NoArgsConstructor
public abstract class AbstractData {

    private Integer id;

    private Calendar datetime;

    public abstract void changeData(LinkedHashMap<String, Object> payload);

}
