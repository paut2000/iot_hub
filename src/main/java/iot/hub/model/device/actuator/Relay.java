package iot.hub.model.device.actuator;

import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.LinkedHashMap;

public class Relay extends AbstractDevice implements IActuator {

    public Relay(MessagingService messagingService) {
        super(messagingService);
    }

    public Relay(MessagingService messagingService, String id, String topic, String type) {
        super(messagingService, id, topic, type);
    }

    @Override
    public void switchStatus() {
        try {
            this.messagingService.publish(this.topic, "{\"action\":\"switch\"}", 2, false);
        } catch (MqttException e) {

        }
    }

    @Override
    public LinkedHashMap<String, Object> getStatus() {
        return null;
    }

    @Override
    public void enable() {
        try {
            this.messagingService.publish(this.topic, "{\"action\":\"enable\"}", 2, false);
        } catch (MqttException e) {

        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.topic, "{\"action\":\"disable\"}", 2, false);
        } catch (MqttException e) {

        }
    }

}
