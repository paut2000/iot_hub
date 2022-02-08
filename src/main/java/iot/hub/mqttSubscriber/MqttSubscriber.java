package iot.hub.mqttSubscriber;

import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.actuator.Relay;
import iot.hub.service.MessagingService;
import org.apache.tomcat.util.json.JSONParser;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class MqttSubscriber implements CommandLineRunner {

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private House house;

    @Override
    public void run(String... args) throws Exception {
        try {
            messagingService.subscribe("/device/new", (t, p) -> {
                JSONParser jsonParser = new JSONParser(p.toString());
                LinkedHashMap<String, Object> payload = jsonParser.parseObject();

                if (payload.get("deviceType").toString().equals("Relay")) {
                    house.getRooms().get(payload.get("roomName").toString()).addDevice(
                            new Relay(
                                    messagingService,
                                    payload.get("deviceId").toString(),
                                    payload.get("topic").toString()
                            )
                    );
                }

                System.out.println("ok :: topic:" + t + "payload" + p);
            });

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
