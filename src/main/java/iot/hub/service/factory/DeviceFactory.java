package iot.hub.service.factory;

import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.actuator.RGBAStrip;
import iot.hub.model.device.actuator.Relay;
import iot.hub.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceFactory {

    @Autowired
    private MessagingService messagingService;

    public AbstractDevice createDevice(String type) {
        AbstractDevice abstractDevice = null;

        if (type.equals("Relay")) abstractDevice = new Relay(messagingService);
        if (type.equals("RGBAStrip")) abstractDevice = new RGBAStrip(messagingService);

        return abstractDevice;
    }

}
