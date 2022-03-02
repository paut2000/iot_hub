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
    public void changeData(AbstractData data) {
        this.humidity = ((DHTData) data).getHumidity();
        this.temperature = ((DHTData) data).getTemperature();
    }

}
