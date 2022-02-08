package iot.hub.model.device.sensor;

import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;

public class AM2302 extends AbstractDevice {

    public AM2302(MessagingService messagingService) {
        super(messagingService);
    }
}
