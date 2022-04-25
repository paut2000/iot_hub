package iot.hub.model.device.sensor;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.exception.DiedDeviceException;
import iot.hub.mqtt.MessagingService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DHT extends AbstractSensor {

    public DHT(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
    }

    @Override
    public void changeUpdateFrequency(Integer milliSeconds) throws DiedDeviceException {

    }

}
