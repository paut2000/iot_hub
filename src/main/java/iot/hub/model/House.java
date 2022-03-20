package iot.hub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iot.hub.dao.DeviceDao;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.device.AbstractDevice;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class House {

    @Getter
    private Map<String, AbstractDevice> devices;

    @Autowired
    private DeviceDao deviceDao;

    private static final Logger logger = LoggerFactory.getLogger(House.class);

    @PostConstruct
    private void postConstructor() {
        devices = deviceDao.findAll();
    }

    public void addDevice(AbstractDevice abstractDevice) {
        if (devices.containsKey(abstractDevice.getSerialNumber())) {
            devices.remove(abstractDevice.getSerialNumber());
            logger.info("Девайс " + abstractDevice.getSerialNumber() + " был отключён, но подключился снова");
        } else {
            deviceDao.save(abstractDevice);
        }
        devices.put(abstractDevice.getSerialNumber(), abstractDevice);
    }

    public void removeDevice(AbstractDevice abstractDevice) {
        //Не удаляет связанные дейвайсы
        devices.remove(abstractDevice.getSerialNumber());
        deviceDao.delete(abstractDevice.getSerialNumber());
    }

    @JsonIgnore
    public AbstractDevice getDevice(String serialNumber) throws ResourceNotFoundException {
        AbstractDevice device = this.devices.get(serialNumber);
        if (device == null) throw new ResourceNotFoundException("Девайс " + serialNumber);
        return device;
    }

}
