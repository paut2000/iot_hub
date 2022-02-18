package iot.hub.model;

import iot.hub.dao.DeviceDao;
import iot.hub.model.device.AbstractDevice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Room {

    private DeviceDao deviceDao = null;

    private String name;

    private Map<String, AbstractDevice> abstractDevices = new LinkedHashMap<>();

    public Room(String name, DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
        this.name = name;
    }

    public void addDevice(AbstractDevice abstractDevice) {
        if (abstractDevices.containsKey(abstractDevice.getDeviceId())) {
            System.out.println("Девайс " + abstractDevice.getDeviceId() + " был отключён, но подключился снова");
        } else {
            abstractDevices.put(abstractDevice.getDeviceId(), abstractDevice);
            deviceDao.save(this, abstractDevice);
        }
    }

    public void removeDevice(AbstractDevice abstractDevice) {
        //Не удаляет связанные дейвайсы
        abstractDevices.remove(abstractDevice.getDeviceId());
        deviceDao.delete(abstractDevice.getDeviceId());
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
