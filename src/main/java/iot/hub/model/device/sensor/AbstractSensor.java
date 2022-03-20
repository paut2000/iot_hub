package iot.hub.model.device.sensor;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.exception.DiedDeviceException;
import iot.hub.model.device.AbstractDevice;
import iot.hub.mqtt.MessagingService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class AbstractSensor extends AbstractDevice {

    @Getter
    @Setter
    private Integer updateFrequency = 0;

    public AbstractSensor(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
    }

    // Вызывается со стороны REST API
    public abstract void changeUpdateFrequency(Integer milliSeconds) throws DiedDeviceException;

}
