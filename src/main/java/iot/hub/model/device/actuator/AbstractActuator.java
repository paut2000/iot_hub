package iot.hub.model.device.actuator;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.exception.DataAlreadyLikeThatException;
import iot.hub.exception.DiedDeviceException;
import iot.hub.model.device.AbstractDevice;
import iot.hub.mqtt.MessagingService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractActuator extends AbstractDevice {

    public AbstractActuator(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
    }

    // Вызываются со стороны REST API
    abstract public void enable() throws DiedDeviceException, DataAlreadyLikeThatException;
    abstract public void disable() throws DiedDeviceException, DataAlreadyLikeThatException;

}
