package iot.hub.model;

import iot.hub.model.device.AbstractDevice;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

public class Room {

    public Room(String name) {
        this.name = name;
    }

    @Getter
    @Setter
    private String name;

    @Getter
    private final LinkedHashMap<String, AbstractDevice> abstractDevices = new LinkedHashMap<>();

    public void addDevice(AbstractDevice abstractDevice) {
        abstractDevices.put(abstractDevice.getId(), abstractDevice);
    }

    public void removeDevice(AbstractDevice abstractDevice) {
        abstractDevices.remove(abstractDevice.getId());
    }

    @Override
    public String toString() {

        StringBuilder ads = new StringBuilder();

        for (AbstractDevice abstractDevice : abstractDevices.values()) {
            ads.append(abstractDevice.toString()).append(",");
        }

        if (ads.length() != 0) ads.deleteCharAt(ads.length() - 1);

        return "\"" + name + "\": {" + ads + "}";
    }
}
