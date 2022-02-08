package iot.hub.service;

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

}
