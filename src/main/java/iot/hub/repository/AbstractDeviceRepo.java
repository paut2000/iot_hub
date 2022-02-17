package iot.hub.repository;

import iot.hub.model.device.AbstractDevice;
import org.springframework.data.repository.CrudRepository;

public interface AbstractDeviceRepo extends CrudRepository<AbstractDevice, Integer> {
}
