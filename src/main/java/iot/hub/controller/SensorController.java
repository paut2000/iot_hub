package iot.hub.controller;

import iot.hub.model.House;
import iot.hub.model.device.sensor.ISensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SensorController {

    @Autowired
    private House house;

    @GetMapping("/sensor/{roomName}/{deviceId}")
    public String getSensorInfo(
            @PathVariable String roomName,
            @PathVariable String deviceId
    ) {
        ISensor sensor = (ISensor) house.getRooms().get(roomName).getAbstractDevices().get(deviceId);
        return sensor.getInfo();
    }

}
