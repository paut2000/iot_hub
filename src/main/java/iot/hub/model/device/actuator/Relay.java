package iot.hub.model.device.actuator;

import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.LinkedHashMap;

public class Relay extends AbstractDevice implements IActuator {

    private Boolean status = false;

    public Relay(MessagingService messagingService) {
        super(messagingService);
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    @Override
    public void setStatus(LinkedHashMap<String, Object> payload) {
        this.status = Boolean.parseBoolean(payload.get("isOn").toString());
    }

    @Override
    public void enable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, "{\"action\":\"enable\"}", 2, false);
            this.status = true;
        } catch (MqttException e) {

        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, "{\"action\":\"disable\"}", 2, false);
            this.status = false;
        } catch (MqttException e) {

        }
    }

}
