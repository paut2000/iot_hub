package iot.hub.model.device.actuator;

import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.actuator.dataPojo.RelayData;
import iot.hub.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.LinkedHashMap;

public class Relay extends AbstractDevice implements IActuator {

    public Relay(MessagingService messagingService) {
        super(messagingService);
        this.data = new RelayData(this);
    }

    @Override
    public String getStatus() {
        return ((RelayData) this.data).toString();
    }

    @Override
    public void changeStatus(LinkedHashMap<String, Object> payload) {
        this.data.changeData(payload);
    }

    @Override
    public void enable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, "{\"action\":\"enable\"}", 2, false);
            ((RelayData) this.data).setStatus(true);
        } catch (MqttException e) {

        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, "{\"action\":\"disable\"}", 2, false);
            ((RelayData) this.data).setStatus(false);
        } catch (MqttException e) {

        }
    }

}
