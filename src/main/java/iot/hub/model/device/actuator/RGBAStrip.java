package iot.hub.model.device.actuator;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttException;

public class RGBAStrip extends RGBStrip {

    @Getter
    @Setter
    public static class RGBA extends RGB {

        private Integer alfa;

        public RGBA(Integer red, Integer green, Integer blue, Integer alfa) {
            super(red, green, blue);
            this.alfa = alfa;
        }

        @Override
        public String toString() {
            return "{" + "\"alfa\":" + alfa + ", \"red\":" + red + ", \"green\":" + green + ", \"blue\":" + blue + "}";
        }
    }

    public void setRGBA(RGBA rgba) {
        try {
            this.messagingService.publish(this.topic, rgba.toString(), 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
