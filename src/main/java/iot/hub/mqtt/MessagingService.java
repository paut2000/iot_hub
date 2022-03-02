package iot.hub.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import iot.hub.parser.DeviceDataJsonParser;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    @Autowired
    private IMqttClient mqttClient;

    public void publish(final String topic, final String payload, int qos, boolean retained)
            throws MqttPersistenceException, MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        mqttClient.publish(topic, mqttMessage);
    }

    public void subscribe(final String topic, IMqttMessageListener iMqttMessageListener) throws MqttException, InterruptedException {
        mqttClient.subscribeWithResponse(topic, iMqttMessageListener);
    }

    public void subscribe(AbstractDevice device) throws MqttException {

        mqttClient.subscribeWithResponse(device.getFromDeviceTopic(), (t, p) -> {
            AbstractData data = new ObjectMapper().registerModule(
                    new SimpleModule().addDeserializer(AbstractData.class, new DeviceDataJsonParser(device.getType()))
            ).readValue(p.toString(), AbstractData.class);

            device.changeData(data);
        });
    }

    public void unsubscribe(AbstractDevice device) throws MqttException {
        mqttClient.unsubscribe(device.getFromDeviceTopic());
    }

}
