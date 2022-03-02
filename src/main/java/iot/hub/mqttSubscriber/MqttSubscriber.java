package iot.hub.mqttSubscriber;

import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.actuator.IActuator;
import iot.hub.model.device.sensor.ISensor;
import iot.hub.service.MessagingService;
import iot.hub.service.factory.DeviceFactory;
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

    @Autowired
    private DeviceFactory deviceFactory;

    @Override
    public void run(String... args) throws Exception {
        try {

            messagingService.subscribe("/device/new", (t, p) -> {

                JSONParser jsonParser = new JSONParser(p.toString());
                LinkedHashMap<String, Object> payload = jsonParser.parseObject();

                AbstractDevice device = deviceFactory.createDevice(payload.get("deviceType").toString());
                device.setSerialNumber(payload.get("deviceId").toString());
                device.setToDeviceTopic((payload.get("toDeviceTopic").toString()));
                device.setFromDeviceTopic((payload.get("fromDeviceTopic").toString()));
                device.setType(payload.get("deviceType").toString());

                // Также этот if помогает при перезапуске сервера: он обновляет состояния актуаторов (они не считываются из БД)
                if (payload.containsKey("status")) { // это для актуатора
                    ((IActuator) device).changeStatus((LinkedHashMap<String, Object>) payload.get("status"));
                }

                // Также этот if помогает при перезапуске сервера: он обновляет данные сенсоров (они не считываются из БД)
                if (payload.containsKey("data")) { // это для сенсора
                    ((ISensor) device).changeInfo((LinkedHashMap<String, Object>) payload.get("data"));
                    messagingService.subscribe((ISensor) device);
                }

                try {
                    house.getRooms().get(payload.get("roomName").toString()).addDevice(device);
                } catch (Exception exception) {
                    System.out.println("Нет такой комнаты: " + payload.get("roomName"));
                }

                System.out.println("new :: topic:" + t + "payload" + p);
            });

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
