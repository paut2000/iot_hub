package iot.hub.model.device.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractData {

    private Timestamp datetime;

    public abstract void changeData(LinkedHashMap<String, Object> payload);

    @Override
    public String toString() {
        return "\"datetime\":" + "\"" + datetime + "\"";
    }

}
