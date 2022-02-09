package iot.hub.model.device.actuator;

import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.LinkedHashMap;

public class RGBAStrip extends AbstractDevice implements IActuator {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class RGBA {

        private Integer red = 0;
        private Integer green = 0;
        private Integer blue = 0;
        private Integer alfa = 0;

        public RGBA(Integer red, Integer green, Integer blue, Integer alfa) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alfa = alfa;
        }

        @Override
        public String toString() {
            return "{" + "\"alfa\":" + alfa + ", \"red\":" + red + ", \"green\":" + green + ", \"blue\":" + blue + "}";
        }
    }

    private RGBA status = new RGBA();

    public RGBAStrip(MessagingService messagingService, String id, String topic, String type) {
        super(messagingService, id, topic, type);
    }

    public RGBAStrip(MessagingService messagingService) {
        super(messagingService);
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    @Override
    public void setStatus(LinkedHashMap<String, Object> payload) {
        this.status.setRed((Integer) payload.get("red"));
        this.status.setGreen((Integer) payload.get("green"));
        this.status.setBlue((Integer) payload.get("blue"));
        this.status.setAlfa((Integer) payload.get("alfa"));
    }

    @Override
    public void enable() {
        try {
            this.messagingService.publish(this.topic, new RGBA(255,255,255, 255).toString(), 2, false);
            this.status.setRed(255);
            this.status.setGreen(255);
            this.status.setBlue(255);
            this.status.setAlfa(255);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.topic, new RGBA(0,0,0, 0).toString(), 2, false);
            this.status.setRed(0);
            this.status.setGreen(0);
            this.status.setBlue(0);
            this.status.setAlfa(0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setRGBA(RGBA rgba) {
        try {
            this.messagingService.publish(this.topic, rgba.toString(), 2, false);
            this.status.setRed(rgba.getRed());
            this.status.setGreen(rgba.getGreen());
            this.status.setBlue(rgba.getBlue());
            this.status.setAlfa(rgba.getAlfa());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
