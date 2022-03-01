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
public class DHTData extends AbstractData {

    private Double humidity;
    private Double temperature;

    public DHTData(Timestamp datetime, Double humidity, Double temperature) {
        super(datetime);
        this.humidity = humidity;
        this.temperature = temperature;
    }

    @Override
    public void changeData(LinkedHashMap<String, Object> payload) {
        this.humidity = Double.parseDouble(payload.get("humidity").toString());
        this.temperature = Double.parseDouble(payload.get("temperature").toString());
    }

}
