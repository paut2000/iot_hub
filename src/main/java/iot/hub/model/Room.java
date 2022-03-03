package iot.hub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import iot.hub.dao.DeviceDao;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.device.AbstractDevice;
import iot.hub.parser.AbstractDeviceJsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
public class Room {

    private DeviceDao deviceDao = null;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonProperty("devices")
    private Map<String, AbstractDevice> abstractDevices = new LinkedHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(Room.class);

    public Room(String name, DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
        this.name = name;
    }

    public void addDevice(AbstractDevice abstractDevice) {
        if (abstractDevices.containsKey(abstractDevice.getSerialNumber())) {
            abstractDevices.remove(abstractDevice.getSerialNumber());
            logger.info("Девайс " + abstractDevice.getSerialNumber() + " в комнате " + this.name
                    + " был отключён, но подключился снова");
        } else {
            deviceDao.save(this, abstractDevice);
        }
        abstractDevices.put(abstractDevice.getSerialNumber(), abstractDevice);
    }

    public void removeDevice(AbstractDevice abstractDevice) {
        //Не удаляет связанные дейвайсы
        abstractDevices.remove(abstractDevice.getSerialNumber());
        deviceDao.delete(abstractDevice.getSerialNumber());
    }

    @JsonIgnore
    public AbstractDevice getDevice(String serialNumber) throws ResourceNotFoundException {
        AbstractDevice device = this.abstractDevices.get(serialNumber);
        if (device == null) throw new ResourceNotFoundException("Девайс " + serialNumber);
        return device;
    }
}
