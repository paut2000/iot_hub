package iot.hub.model;

import iot.hub.model.device.AbstractDevice;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Room {

    public Room(String name) {
        this.name = name;
    }

    @Getter
    @Setter
    private String name;

    @Getter
    private LinkedHashMap<String, AbstractDevice> abstractDevices = new LinkedHashMap<>();

    public void addDevice(AbstractDevice abstractDevice) {
        abstractDevices.put(abstractDevice.getId(), abstractDevice);
    }

    public void removeDevice(AbstractDevice abstractDevice) {
        abstractDevices.remove(abstractDevice.getId());
    }

}
