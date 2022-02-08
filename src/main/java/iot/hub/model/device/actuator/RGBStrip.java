package iot.hub.model.device.actuator;

import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.LinkedHashMap;

public class RGBStrip extends AbstractDevice implements IActuator {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RGB {
        protected Integer red;
        protected Integer green;
        protected Integer blue;

        @Override
        public String toString() {
            return "{" + "\"red\":" + red + ", \"green\":" + green + ", \"blue\":" + blue + "}";
        }
    }

    public RGBStrip(MessagingService messagingService, String id, String topic) {
        super(messagingService, id, topic);
    }

    @Override
    public LinkedHashMap<String, Object> getStatus() {
        return null;
    }

    @Override
    public void switchStatus() {
        try {
            this.messagingService.publish(this.topic, "{\"action\":\"switch\"}", 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enable() {
        try {
            this.messagingService.publish(this.topic, new RGB(255,255,255).toString(), 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.topic, new RGB(0,0,0).toString(), 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setRGB(RGB rgb) {
        try {
            this.messagingService.publish(this.topic, rgb.toString(), 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
