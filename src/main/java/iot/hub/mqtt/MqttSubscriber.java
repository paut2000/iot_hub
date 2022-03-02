package iot.hub.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import iot.hub.service.MessagingService;
import iot.hub.service.factory.DeviceFactory;
import org.apache.tomcat.util.json.JSONParser;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
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
    public void run(String... args) {
        try {

            messagingService.subscribe("/device/new", (t, p) -> {

                NewDeviceInfo newDeviceInfo = new ObjectMapper().readerFor(NewDeviceInfo.class).readValue(p.toString());
                String roomName = newDeviceInfo.getRoomName();
                AbstractDevice device = deviceFactory.injectDependencies(newDeviceInfo.getDevice());

                // Заполняет поле datetime и сохраняет в БД
                device.changeData(device.getData());

                messagingService.subscribe(device);

                try {
                    house.getRoom(roomName).addDevice(device);
                } catch (ResourceNotFoundException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("new :: topic:" + t + "payload" + p);
            });

        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
