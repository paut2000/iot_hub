package iot.hub.repository;

import iot.hub.model.device.actuator.dataPojo.RelayData;
import org.springframework.data.repository.CrudRepository;

public interface RelayDataRepo extends CrudRepository<RelayData, Integer> {
}
