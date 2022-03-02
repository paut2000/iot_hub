package iot.hub.factory;

import iot.hub.dao.deviceData.DHTDataDao;
import iot.hub.dao.deviceData.RGBADataDao;
import iot.hub.dao.deviceData.RelayDataDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.actuator.RGBAStrip;
import iot.hub.model.device.actuator.Relay;
import iot.hub.model.device.sensor.DHT;
import iot.hub.mqtt.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceFactory {

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private RelayDataDao relayDataDao;

    @Autowired
    private RGBADataDao rgbaDataDao;

    @Autowired
    private DHTDataDao dhtDataDao;

    public AbstractDevice createDevice(String type) throws Exception {

        if (type.equals("Relay")) return new Relay(messagingService, relayDataDao);
        if (type.equals("RGBAStrip")) return new RGBAStrip(messagingService, rgbaDataDao);
        if (type.equals("DHT")) return new DHT(messagingService, dhtDataDao);

        throw new Exception("Нет подходящего типа данных в DeviceFactory");
    }

    public AbstractDevice injectDependencies(AbstractDevice device) {
        device.setMessagingService(messagingService);
        if (device.getType().equals("Relay")) device.setDataDao(relayDataDao);
        if (device.getType().equals("RGBAStrip")) device.setDataDao(rgbaDataDao);
        if (device.getType().equals("DHT")) device.setDataDao(dhtDataDao);
        return device;
    }

}
