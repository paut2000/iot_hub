package iot.hub.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import iot.hub.factory.DeviceFactory;
import iot.hub.mqtt.message.DiedDeviceMessage;
import iot.hub.mqtt.message.NewDeviceMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber implements CommandLineRunner {

    @Override
    public void run(String... args) {
        subscribeToNewDevice();
        subscribeToLastWillAndTestament();
    }

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private House house;

    @Autowired
    private DeviceFactory deviceFactory;

    private void subscribeToNewDevice() {
        try {

            messagingService.subscribe("/device/new", (t, p) -> {
                NewDeviceMessage newDeviceMessage = new ObjectMapper()
                        .readerFor(NewDeviceMessage.class)
                        .readValue(p.toString());
                String roomName = newDeviceMessage.getRoomName();
                AbstractDevice device = deviceFactory.injectDependencies(newDeviceMessage.getDevice());

                // Заполняет поле datetime и сохраняет в БД
                device.changeData(device.getData());

                messagingService.subscribe(device);

                device.setAlive(true);

                try {
                    house.getRoom(roomName).addDevice(device);
                    System.out.println("new :: topic:" + t + "payload" + p);
                } catch (ResourceNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            });

        } catch (MqttException | InterruptedException e) {
            System.out.println("Не удалось получить инофрмацию о новом девайсе: " + e.getMessage());
        }
    }

    private void subscribeToLastWillAndTestament() {
        try {
            messagingService.subscribe("/device/died", (t, p) -> {
                DiedDeviceMessage diedDeviceMessage = new ObjectMapper()
                        .readerFor(DiedDeviceMessage.class)
                        .readValue(p.toString());

                String roomName = diedDeviceMessage.getRoomName();
                String serialNumber = diedDeviceMessage.getSerialNumber();

                try {
                    AbstractDevice device = house.getRoom(roomName).getDevice(serialNumber);
                    device.setAlive(false);
                    messagingService.unsubscribe(device);
                } catch (ResourceNotFoundException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("died :: topic:" + t + "payload" + p);
            });
        } catch (MqttException | InterruptedException e) {
            System.out.println("Не удалось получить инофрмацию о помершем девайсе: " + e.getMessage());
        }
    }

}
