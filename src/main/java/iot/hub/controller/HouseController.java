package iot.hub.controller;

import iot.hub.dao.DeviceDao;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class HouseController {

    @Autowired
    private House house;

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);

    @GetMapping("/house")
    public House getHouse() {
        return house;
    }

    @DeleteMapping("/house/{serialNumber}")
    public ResponseEntity<AbstractDevice> deleteDevice(
            @PathVariable String serialNumber
    ) throws ResourceNotFoundException {
        AbstractDevice device = house.getDevice(serialNumber);
        house.removeDevice(device);
        return ResponseEntity.status(HttpStatus.OK).body(device);
    }

}
