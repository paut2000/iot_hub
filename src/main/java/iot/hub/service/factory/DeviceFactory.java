package iot.hub.service.factory;

import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.actuator.RGBAStrip;
import iot.hub.model.device.actuator.Relay;
import iot.hub.model.device.sensor.DHT;
import iot.hub.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceFactory {

    @Autowired
    private MessagingService messagingService;

    public AbstractDevice createDevice(String type) throws Exception {

        if (type.equals("Relay")) return new Relay(messagingService);
        if (type.equals("RGBAStrip")) return new RGBAStrip(messagingService);
        if (type.equals("DHT")) return new DHT(messagingService);

        throw new Exception("Нет подходящего типа данных в DeviceFactory");
    }

}
