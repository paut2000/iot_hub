package iot.hub.model.device.sensor;

import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class DHT extends AbstractDevice implements ISensor {

    private Double humidity;
    private Double temperature;

    public DHT(MessagingService messagingService) {
        super(messagingService);
    }

    @Override
    public void setAllInfo(LinkedHashMap<String, Object> info) {
        this.humidity = (Double) info.get("humidity");
        this.temperature = (Double) info.get("temperature");
    }

}
