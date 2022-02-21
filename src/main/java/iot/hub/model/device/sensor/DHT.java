package iot.hub.model.device.sensor;

import iot.hub.dao.deviceData.IDeviceDataDao;
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

    public DHT(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
    }

    @Override
    public void setAllInfo(LinkedHashMap<String, Object> info) {
        this.humidity = Double.parseDouble(info.get("humidity").toString());
        this.temperature = Double.parseDouble(info.get("temperature").toString());
    }

}
