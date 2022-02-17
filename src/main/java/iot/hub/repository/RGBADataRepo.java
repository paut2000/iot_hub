package iot.hub.repository;

import iot.hub.model.device.actuator.dataPojo.RGBAData;
import org.springframework.data.repository.CrudRepository;

public interface RGBADataRepo extends CrudRepository<RGBAData, Integer> {
}
