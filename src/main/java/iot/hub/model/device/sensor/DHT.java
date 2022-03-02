package iot.hub.model.device.sensor;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.DHTData;
import iot.hub.service.MessagingService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DHT extends AbstractDevice implements ISensor {

    public DHT(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
        this.data = new DHTData();
    }

    @Override
    public void changeUpdateFrequency(Integer milliSeconds) {

    }

}
